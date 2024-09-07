package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: QuestionUpdateDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 题目更新请求
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 16:59
 * @Version 1.0
 */
@Data
public class QuestionUpdateDTO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 判题用例
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置
     */
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}
