package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

/**
 * ClassName: JudgeCase
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 判题用例
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 15:12
 * @Version 1.0
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String inputCase;
    /**
     * 输出用例
     */
    private String outputCase;
}
