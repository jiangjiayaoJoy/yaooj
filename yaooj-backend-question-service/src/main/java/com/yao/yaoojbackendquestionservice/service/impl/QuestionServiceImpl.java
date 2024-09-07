package com.yao.yaoojbackendquestionservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.yaoojbackendcommon.common.ErrorCode;
import com.yao.yaoojbackendcommon.constant.CommonConstant;
import com.yao.yaoojbackendcommon.exception.BusinessException;
import com.yao.yaoojbackendcommon.utils.SqlUtils;
import com.yao.yaoojbackendmodel.dto.*;
import com.yao.yaoojbackendmodel.entity.Question;
import com.yao.yaoojbackendmodel.entity.User;
import com.yao.yaoojbackendmodel.vo.QuestionVO;
import com.yao.yaoojbackendquestionservice.mapper.QuestionMapper;
import com.yao.yaoojbackendquestionservice.service.QuestionService;
import com.yao.yaoojbackendserviceclient.service.UserFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 瑶瑶儿
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-07-21 16:31:34
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {
    @Resource
    private UserFeignClient userFeignClient;

    /**
     * 创建题目
     * @param questionAddDTO
     * @return
     */
    @Override
    public Long addQuestion(QuestionAddDTO questionAddDTO, HttpServletRequest request) {
        Question question = new Question();
        BeanUtil.copyProperties(questionAddDTO,question);

        List<String> tags = questionAddDTO.getTags();
        if(tags!=null){
            question.setTags(JSON.toJSONString(tags));
        }

        List<JudgeCase> judgeCase = questionAddDTO.getJudgeCase();
        if(judgeCase!=null){
            question.setJudgeCase(JSON.toJSONString(judgeCase));
        }

        JudgeConfig judgeConfig = questionAddDTO.getJudgeConfig();
        if(judgeConfig!=null){
            question.setJudgeConfig(JSON.toJSONString(judgeConfig));
        }

        //检查参数是否合法
        validQuestion(question,true);

        //获得现在登录的用户id
        User loginUser = userFeignClient.getLoginUser(request);
//        User loginUser = UserHolder.getUser();

        question.setUserId(loginUser.getId());
        question.setThumbNum(0);
        question.setFavourNum(0);
        question.setSubmitNum(0);
        question.setAcceptedNum(0);

        boolean result = save(question);
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return question.getId();
    }

    /**
     * 检查参数是否合法
     * @param question
     * @param add
     */
    @Override
    public void validQuestion(Question question, boolean add) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        String content = question.getContent();
        String tags = question.getTags();
        String answer = question.getAnswer();
        String judgeCase = question.getJudgeCase();
        String judgeConfig = question.getJudgeConfig();
        // 创建时，参数不能为空
        if (add) {
            if(StringUtils.isAnyBlank(title, content, tags)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "答案过长");
        }
        if (StringUtils.isNotBlank(judgeCase) && judgeCase.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "判题用例过长");
        }
        if (StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "判题配置过长");
        }
    }

    /**
     * 更新题目
     * @param questionUpdateDTO
     * @param request
     * @return
     */
    @Override
    public Boolean updateQuestion(QuestionUpdateDTO questionUpdateDTO, HttpServletRequest request) {
        Question question = new Question();
//        BeanUtil.copyProperties(questionEditDTO, question);
        if(questionUpdateDTO.getTitle()!=null&&questionUpdateDTO.getTitle().length()!=0){
            question.setTitle(questionUpdateDTO.getTitle());
        }
        if(questionUpdateDTO.getContent()!=null&&questionUpdateDTO.getContent().length()!=0){
            question.setContent(questionUpdateDTO.getContent());
        }
        List<String> tags = questionUpdateDTO.getTags();
        if (tags != null&&tags.size()!=0) {
            question.setTags(JSON.toJSONString(tags));
        }
        if(questionUpdateDTO.getAnswer()!=null&&questionUpdateDTO.getAnswer().length()!=0){
            question.setAnswer(questionUpdateDTO.getAnswer());
        }
        List<JudgeCase> judgeCase = questionUpdateDTO.getJudgeCase();
        if (judgeCase != null&&judgeCase.size()!=0) {
            question.setJudgeCase(JSON.toJSONString(judgeCase));
        }
        JudgeConfig judgeConfig = questionUpdateDTO.getJudgeConfig();
        if (judgeConfig.getTimeLimit()!=null||judgeConfig.getMemoryLimit()!=null||judgeConfig.getStackLimit()!=null) {
            question.setJudgeConfig(JSON.toJSONString(judgeConfig));
        }
        validQuestion(question,false);
        question.setId(questionUpdateDTO.getId());
        //判断题目是否存在
        Long id = question.getId();
        Question oldQuestion = getById(id);
        if(oldQuestion==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //更新题目
        boolean result = updateById(question);
        return result;
    }

    /**
     * 编辑题目（用户）
     * @param questionEditDTO
     * @param request
     * @return
     */
    @Override
    public Boolean editQuestion(QuestionEditDTO questionEditDTO, HttpServletRequest request) {
        Question question = new Question();
//        BeanUtil.copyProperties(questionEditDTO, question);
        if(questionEditDTO.getTitle()!=null&&questionEditDTO.getTitle().length()!=0){
            question.setTitle(questionEditDTO.getTitle());
        }
        if(questionEditDTO.getContent()!=null&&questionEditDTO.getContent().length()!=0){
            question.setContent(questionEditDTO.getContent());
        }
        List<String> tags = questionEditDTO.getTags();
        if (tags != null&&tags.size()!=0) {
            question.setTags(JSON.toJSONString(tags));
        }
        if(questionEditDTO.getAnswer()!=null&&questionEditDTO.getAnswer().length()!=0){
            question.setAnswer(questionEditDTO.getAnswer());
        }
        List<JudgeCase> judgeCase = questionEditDTO.getJudgeCase();
        if (judgeCase != null&&judgeCase.size()!=0) {
            question.setJudgeCase(JSON.toJSONString(judgeCase));
        }
        JudgeConfig judgeConfig = questionEditDTO.getJudgeConfig();
        if (judgeConfig.getTimeLimit()!=null||judgeConfig.getMemoryLimit()!=null||judgeConfig.getStackLimit()!=null) {
            question.setJudgeConfig(JSON.toJSONString(judgeConfig));
        }
        validQuestion(question,false);
        question.setId(questionEditDTO.getId());

        //判断题目是否存在
        Long id = question.getId();
        Question oldQuestion = getById(id);
        if(oldQuestion==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //更新题目（仅本人和管理员可以更新）
        User loginUser = userFeignClient.getLoginUser(request);
//        User loginUser = UserHolder.getUser();
        if(!oldQuestion.getUserId().equals(loginUser.getId())&&!userFeignClient.isAdmin(loginUser)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = updateById(question);
        return result;
    }

    /**
     * 获取查询条件
     * @param questionQueryDTO
     * @return
     */
    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryDTO questionQueryDTO) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if(questionQueryDTO==null){
            return queryWrapper;
        }
        Long id = questionQueryDTO.getId();
        String title = questionQueryDTO.getTitle();
        String content = questionQueryDTO.getContent();
        List<String> tags = questionQueryDTO.getTags();
        String answer = questionQueryDTO.getAnswer();
        Long userId = questionQueryDTO.getUserId();
        String sortField = questionQueryDTO.getSortField();
        String sortOrder = questionQueryDTO.getSortOrder();

        //拼接查询条件
        queryWrapper.eq(ObjectUtils.isNotEmpty(id),"id",id);
        queryWrapper.like(StringUtils.isNotBlank(title),"title",title);
        queryWrapper.like(StringUtils.isNotBlank(content),"content",content);
        queryWrapper.like(StringUtils.isNotBlank(answer),"answer",answer);
        if(!CollectionUtils.isEmpty(tags)){
            for(String tag:tags){
                queryWrapper.like("tags","\""+tag+"\"");
            }
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId),"userId",userId);
        queryWrapper.eq("isDelete",false);
        //添加排序条件
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), CommonConstant.SORT_ORDER_ASC.equals(sortOrder),sortField);
        return queryWrapper;
    }

    /**
     * 分页获取题目封装
     * @param questionPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request) {
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionVO> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getCurrent(), questionPage.getTotal());
        if(CollectionUtils.isEmpty(questionList)){
            return questionVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionList.stream().map(Question::getUserId).collect(Collectors.toSet());
        List<User> userList = userFeignClient.listByIds(userIdSet);
        Map<Long, List<User>> userIdUserListMap = userList.stream().collect(Collectors.groupingBy(User::getId));
        //填充信息
        List<QuestionVO> questionVOList = questionList.stream().map(question -> {
            QuestionVO questionVO = QuestionVO.objToVo(question);
            Long userId = question.getId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionVO.setUserVO(userFeignClient.getUserVO(user));
            return questionVO;
        }).collect(Collectors.toList());
        questionVOPage.setRecords(questionVOList);
        return questionVOPage;
    }
}




