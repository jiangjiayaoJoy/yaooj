package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: QuestionSubmitDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 题目提交请求
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 20:01
 * @Version 1.0
 */
@Data
public class QuestionSubmitDTO implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}
