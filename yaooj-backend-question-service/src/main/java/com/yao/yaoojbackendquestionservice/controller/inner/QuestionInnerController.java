package com.yao.yaoojbackendquestionservice.controller.inner;

import com.yao.yaoojbackendmodel.entity.Question;
import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import com.yao.yaoojbackendquestionservice.service.QuestionService;
import com.yao.yaoojbackendquestionservice.service.QuestionSubmitService;
import com.yao.yaoojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ClassName: QuestionInnerController
 * Package: com.yao.yaoojbackendquestionservice.controller.inner
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/8/12 11:19
 * @Version 1.0
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
