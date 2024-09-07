package com.yao.yaoojbackendmodel.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: QuestionSubmitStatusEnum
 * Package: com.yao.yaoojbackendmy.enums
 * Description:
 * 题目提交状态枚举
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 20:36
 * @Version 1.0
 */
public enum QuestionSubmitStatusEnum {
    WAITING("等待中",0),
    RUNNING("判题中",1),
    SUCCEED("成功",2),
    FAILED("失败",3);
    private final String text;
    private final Integer value;

    QuestionSubmitStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }
    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static QuestionSubmitStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitStatusEnum anEnum : QuestionSubmitStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
