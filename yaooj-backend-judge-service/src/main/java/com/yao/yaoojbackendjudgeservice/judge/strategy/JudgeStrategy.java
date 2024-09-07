package com.yao.yaoojbackendjudgeservice.judge.strategy;

import com.yao.yaoojbackendmodel.dto.JudgeInfo;

/**
 * ClassName: JudgeStrategy
 * Package: com.yao.yaoojbackendmy.judge.strategy
 * Description:
 * 判题策略
 *
 * @Author Joy_瑶
 * @Create 2024/7/29 10:58
 * @Version 1.0
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
