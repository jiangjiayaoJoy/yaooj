package com.yao.yaoojbackendjudgeservice.judge;

import com.yao.yaoojbackendmodel.entity.QuestionSubmit;

/**
 * ClassName: JudgeService
 * Package: com.yao.yaoojbackendmy.judge.strategy
 * Description:
 * 判题服务
 *
 * @Author Joy_瑶
 * @Create 2024/7/29 9:58
 * @Version 1.0
 */
public interface JudgeService {
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
