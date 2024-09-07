package com.yao.yaoojbackendmodel.dto;




import com.yao.yaoojbackendcommon.common.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: QuestionQueryDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 分页查询题目封装类请求
 *
 * @Author Joy_瑶
 * @Create 2024/7/24 21:30
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryDTO extends PageDTO implements Serializable {
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
     * 标签列表（json数组）
     */
    private List<String> tags;

    /**
     * 题目标准答案
     */
    private String answer;

    /**
     * 创建用户的id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
