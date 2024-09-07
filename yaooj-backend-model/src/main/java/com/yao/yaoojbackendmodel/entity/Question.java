package com.yao.yaoojbackendmodel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标题
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private String title;

    /**
     * 内容
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private String content;

    /**
     * 标签列表（json数组）
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private String tags;

    /**
     * 题目标准答案
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private String answer;

    /**
     * 题目提交数
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private Integer submitNum;

    /**
     * 题目通过数
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private Integer acceptedNum;

    /**
     * 判题用例（json数组）
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private String judgeCase;

    /**
     * 判题配置（json对象）
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private String judgeConfig;

    /**
     * 点赞数
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private Integer thumbNum;

    /**
     * 收藏数
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private Integer favourNum;

    /**
     * 创建用户的id
     */
    @TableField(whereStrategy = FieldStrategy.NOT_EMPTY)
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}