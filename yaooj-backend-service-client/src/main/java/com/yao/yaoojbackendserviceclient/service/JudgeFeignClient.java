package com.yao.yaoojbackendserviceclient.service;

import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName: JudgeService
 * Package: com.yao.yaoojbackendserviceclient.service
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/8/9 17:19
 * @Version 1.0
 */
@FeignClient(name = "yaooj-backend-judge-service",path = "/api/judge/inner")
public interface JudgeFeignClient {
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
