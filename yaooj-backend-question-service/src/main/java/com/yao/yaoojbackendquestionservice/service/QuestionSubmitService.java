package com.yao.yaoojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.yaoojbackendmodel.dto.QuestionSubmitDTO;
import com.yao.yaoojbackendmodel.dto.QuestionSubmitQueryDTO;
import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import com.yao.yaoojbackendmodel.entity.User;
import com.yao.yaoojbackendmodel.vo.QuestionSubmitVO;


import javax.servlet.http.HttpServletRequest;

/**
* @author 瑶瑶儿
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-07-21 16:33:06
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交题目
     * @param questionSubmitDTO
     * @param request
     * @return
     */
    long doQuestionSubmit(QuestionSubmitDTO questionSubmitDTO, HttpServletRequest request);

    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryDTO questionSubmitQueryDTO);

    /**
     * 分页获取题目封装
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);
}
