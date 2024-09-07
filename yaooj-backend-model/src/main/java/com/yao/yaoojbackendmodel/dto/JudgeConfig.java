package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

/**
 * ClassName: JudgeConfig
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 题目配置
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 15:14
 * @Version 1.0
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制（ms）
     */
    private Long timeLimit;
    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;
    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}
