package com.yao.yaoojbackendquestionservice.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.yaoojbackendcommon.common.ErrorCode;
import com.yao.yaoojbackendcommon.constant.CommonConstant;
import com.yao.yaoojbackendcommon.exception.BusinessException;
import com.yao.yaoojbackendcommon.utils.SqlUtils;
import com.yao.yaoojbackendmodel.dto.QuestionSubmitDTO;
import com.yao.yaoojbackendmodel.dto.QuestionSubmitQueryDTO;
import com.yao.yaoojbackendmodel.entity.Question;
import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import com.yao.yaoojbackendmodel.entity.User;
import com.yao.yaoojbackendmodel.enums.QuestionSubmitLanguageEnum;
import com.yao.yaoojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.yao.yaoojbackendmodel.vo.QuestionSubmitVO;
import com.yao.yaoojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.yao.yaoojbackendquestionservice.rabbitmq.MyMessageProducer;
import com.yao.yaoojbackendquestionservice.service.QuestionService;
import com.yao.yaoojbackendquestionservice.service.QuestionSubmitService;
import com.yao.yaoojbackendserviceclient.service.JudgeFeignClient;
import com.yao.yaoojbackendserviceclient.service.UserFeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author 瑶瑶儿
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-07-21 16:33:06
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private QuestionService questionService;
    @Resource
    @Lazy//使用懒加载，解决循环依赖
    private JudgeFeignClient judgeFeignClient;
    @Resource
    private MyMessageProducer myMessageProducer;
    /**
     * 提交题目
     * @param questionSubmitDTO
     * @param request
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitDTO questionSubmitDTO, HttpServletRequest request) {
        //校验编程语言是否合法
        String language = questionSubmitDTO.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"编程语言错误");
        }
        //检查题目是否存在
        Long questionId = questionSubmitDTO.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //todo 优化：如何使得同一时间同一用户只能提交一次
        //synchronized(String.of(userId).intern())
        User loginUser = userFeignClient.getLoginUser(request);
//        User loginUser = UserHolder.getUser();
        Long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitDTO.getCode());
        questionSubmit.setLanguage(language);
        //设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        //保存到数据库
        boolean save = save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"数据插入失败");
        }
        Long submitId = questionSubmit.getId();
        //执行判题服务
        myMessageProducer.sendMessage("code_exchange","my_routingKey",String.valueOf(submitId));
//        CompletableFuture.runAsync(()->{
//            judgeFeignClient.doJudge(submitId);
//        });
        return submitId;
    }

    /**
     * 获取题目提交的查询条件
     * @param questionSubmitQueryDTO
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryDTO questionSubmitQueryDTO) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if(questionSubmitQueryDTO==null){
            return queryWrapper;
        }
        String language = questionSubmitQueryDTO.getLanguage();
        Integer status = questionSubmitQueryDTO.getStatus();
        Long questionId = questionSubmitQueryDTO.getQuestionId();
        Long userId = questionSubmitQueryDTO.getUserId();
        String sortField = questionSubmitQueryDTO.getSortField();
        String sortOrder = questionSubmitQueryDTO.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream()
                .map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }
    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 脱敏：仅本人和管理员能看见自己（提交 userId 和登录用户 id 不同）提交的代码
        long userId = loginUser.getId();
        // 处理脱敏
        if (userId != questionSubmit.getUserId() && !userFeignClient.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;
    }
}




