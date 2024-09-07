package com.yao.yaoojbackendjudgeservice.controller.inner;

import com.yao.yaoojbackendjudgeservice.judge.JudgeService;
import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import com.yao.yaoojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ClassName: JudgeInnerController
 * Package: com.yao.yaoojbackendjudgeservice.controller.inner
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/8/12 11:27
 * @Version 1.0
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    private JudgeService judgeService;

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/do")
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
