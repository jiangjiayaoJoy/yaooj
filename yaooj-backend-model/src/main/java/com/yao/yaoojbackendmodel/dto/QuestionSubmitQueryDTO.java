package com.yao.yaoojbackendmodel.dto;

import com.yao.yaoojbackendcommon.common.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ClassName: QuestionSubmitQueryDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/8/2 21:23
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryDTO extends PageDTO implements Serializable {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;


    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
