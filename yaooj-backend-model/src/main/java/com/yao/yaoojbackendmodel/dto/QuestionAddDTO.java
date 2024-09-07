package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: QuestionAddDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 题目创建请求
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 15:09
 * @Version 1.0
 */
@Data
public class QuestionAddDTO implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json数组）
     */
    private List<String> tags;

    /**
     * 题目标准答案
     */
    private String answer;

    /**
     * 判题用例（json数组）
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置（json对象）
     */
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}
