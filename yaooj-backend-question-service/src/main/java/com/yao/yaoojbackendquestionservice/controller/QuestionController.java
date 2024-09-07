package com.yao.yaoojbackendquestionservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.yao.yaoojbackendcommon.annotation.AuthCheck;
import com.yao.yaoojbackendcommon.common.ErrorCode;
import com.yao.yaoojbackendcommon.common.Result;
import com.yao.yaoojbackendcommon.constant.UserConstant;
import com.yao.yaoojbackendcommon.exception.BusinessException;
import com.yao.yaoojbackendmodel.dto.*;
import com.yao.yaoojbackendmodel.entity.Question;
import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import com.yao.yaoojbackendmodel.entity.User;
import com.yao.yaoojbackendmodel.vo.QuestionSubmitVO;
import com.yao.yaoojbackendmodel.vo.QuestionVO;
import com.yao.yaoojbackendquestionservice.service.QuestionService;
import com.yao.yaoojbackendquestionservice.service.QuestionSubmitService;
import com.yao.yaoojbackendserviceclient.service.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: UserController
 * Package: com.yao.yaoojbackendmy.controller
 * Description:
 * 题目接口
 *
 * @Author Joy_瑶
 * @Create 2024/7/17 19:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/")
@Slf4j
public class QuestionController {
    @Resource
    private QuestionService questionService;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private QuestionSubmitService questionSubmitService;
    // region增删改查

    /**
     * 创建题目
     * @param questionAddDTO
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Result<Long> addQuestion(@RequestBody QuestionAddDTO questionAddDTO, HttpServletRequest request){
        if(questionAddDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return Result.success(questionService.addQuestion(questionAddDTO,request));
    }

    /**
      * 删除题目
      * @param id
      * @param request
      * @return
      */
    @PostMapping("/delete")
    public Result<Boolean> deleteQuestion(long id,HttpServletRequest request){
        //different 鱼皮用的参数是DeleteRequest，我直接用的id来接收
        //先判断要删除的题目是否存在；然后再删除，并且只有管理员和本人才能够删除
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //判断要删除的题目是否存在
        Question oldQuestion = questionService.getById(id);
        if(oldQuestion==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //仅管理员或本人删除
        User loginUser = userFeignClient.getLoginUser(request);
//        User loginUser = UserHolder.getUser();
        if(!userFeignClient.isAdmin(loginUser)&&!oldQuestion.getUserId().equals(loginUser.getId())){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        boolean result = questionService.removeById(id);
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 更新题目（仅管理员）
     * @param questionUpdateDTO
     * @param request
     * @return
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/update")
    public Result<Boolean> updateQuestion(@RequestBody QuestionUpdateDTO questionUpdateDTO, HttpServletRequest request){
        if (questionUpdateDTO == null || questionUpdateDTO.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result=questionService.updateQuestion(questionUpdateDTO,request);
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 编辑题目（仅用户）
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public Result<Boolean> editQuestion(@RequestBody QuestionEditDTO questionEditDTO, HttpServletRequest request){
        if (questionEditDTO == null || questionEditDTO.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result=questionService.editQuestion(questionEditDTO,request);
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 根据id获取题目
     * @param id
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/get/vo")
    public Result<QuestionVO> getQuestionVOById(long id, HttpServletRequest httpServletRequest){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if(question==null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return Result.success(QuestionVO.objToVo(question));

    }


    @PostMapping("/list/page/vo")
    public Result<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryDTO questionQueryDTO, HttpServletRequest request){
        if(questionQueryDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数不能为空！");
        }
        long current = questionQueryDTO.getCurrent();
        long size = questionQueryDTO.getPageSize();
        //限制爬虫
        if(size>20){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求题目数量过多！");
        }
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryDTO));
        return Result.success(questionService.getQuestionVOPage(questionPage,request));
    }



    @PostMapping("/my/list/page/vo")
    public Result<Page<QuestionVO>> listMyQuesionVOByPage(@RequestBody QuestionQueryDTO questionQueryDTO,HttpServletRequest request){
        if(questionQueryDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数不能为空！");
        }
//        User loginUser = UserHolder.getUser();
        User loginUser = userFeignClient.getLoginUser(request);
        questionQueryDTO.setUserId(loginUser.getId());
        long current = questionQueryDTO.getCurrent();
        long size = questionQueryDTO.getPageSize();
        //限制爬虫
        if(size>20){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求题目数量过多！");
        }
        Page<Question> questionPage = questionService.page(new Page<Question>(current, size),
                questionService.getQueryWrapper(questionQueryDTO));
        return Result.success(questionService.getQuestionVOPage(questionPage,request));
    }


    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryDTO questionQueryDTO,HttpServletRequest request){
        if(questionQueryDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数不能为空！");
        }
        long current = questionQueryDTO.getCurrent();
        long size = questionQueryDTO.getPageSize();
        Page<Question> questionPage = questionService.page(new Page<Question>(current, size),
                questionService.getQueryWrapper(questionQueryDTO));
        return Result.success(questionPage);
    }
    //endregion


    // region 题目提交
    /**
     * 提交题目
     * @param questionSubmitDTO
     * @param request
     * @return
     */
    @PostMapping("/question_submit/do")
    public Result<Long> doQuestionSubmit(@RequestBody QuestionSubmitDTO questionSubmitDTO, HttpServletRequest request){
        if(questionSubmitDTO==null||questionSubmitDTO.getQuestionId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result=questionSubmitService.doQuestionSubmit(questionSubmitDTO,request);
        return Result.success(result);
    }

    //todo 分页获取题目列表
    @PostMapping("/question_submit/list/page")
    public Result<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryDTO questionSubmitQueryDTO, HttpServletRequest request){
        if(questionSubmitQueryDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空！");
        }
        long current = questionSubmitQueryDTO.getCurrent();
        long pageSize = questionSubmitQueryDTO.getPageSize();
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, pageSize),
                questionSubmitService.getQueryWrapper(questionSubmitQueryDTO));
        final User loginUser = userFeignClient.getLoginUser(request);
        // 返回脱敏信息
        Page<QuestionSubmitVO> questionSubmitVOPage=questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage,loginUser);
        return Result.success(questionSubmitVOPage);
    }

    //endregion
}
