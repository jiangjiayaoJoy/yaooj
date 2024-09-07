package com.yao.yaoojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.yaoojbackendmodel.dto.QuestionAddDTO;
import com.yao.yaoojbackendmodel.dto.QuestionEditDTO;
import com.yao.yaoojbackendmodel.dto.QuestionQueryDTO;
import com.yao.yaoojbackendmodel.dto.QuestionUpdateDTO;
import com.yao.yaoojbackendmodel.entity.Question;
import com.yao.yaoojbackendmodel.vo.QuestionVO;


import javax.servlet.http.HttpServletRequest;

/**
* @author 瑶瑶儿
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-07-21 16:31:34
*/
public interface QuestionService extends IService<Question> {

    /**
     * 创建题目
     * @param questionAddDTO
     * @return
     */
    Long addQuestion(QuestionAddDTO questionAddDTO, HttpServletRequest request);

    /**
     * 检查参数是否合法
     * @param question
     * @param add
     */
    void validQuestion(Question question,boolean add);

    /**
     * 更新题目（管理员）
     * @param questionUpdateDTO
     * @param request
     * @return
     */
    Boolean updateQuestion(QuestionUpdateDTO questionUpdateDTO, HttpServletRequest request);

    /**
     * 编辑题目（用户）
     * @param questionEditDTO
     * @param request
     * @return
     */
    Boolean editQuestion(QuestionEditDTO questionEditDTO, HttpServletRequest request);

    /**
     * 获取查询条件
     * @param questionQueryDTO
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryDTO questionQueryDTO);

    /**
     * 分页获取题目封装
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);
}
