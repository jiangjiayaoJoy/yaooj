package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

/**
 * ClassName: JudgeInfo
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 判题信息
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 17:02
 * @Version 1.0
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 时间消耗（ms）
     */
    private Long time;
    /**
     * 内存消耗（KB）
     */
    private Long memory;
}
