package com.yao.yaoojbackendmodel.enums;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: QuestionSubmitLanguageEnum
 * Package: com.yao.yaoojbackendmy.enums
 * Description:
 * 题目提交编程语言枚举
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 20:06
 * @Version 1.0
 */
public enum QuestionSubmitLanguageEnum {
    JAVA("java","java"),
    CPLUSPLUS("c++","c++"),
    GOLANG("golang","golang");
    private String text;
    private String value;

    QuestionSubmitLanguageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    /**
     * 获取值列表
     * @return
     */
    public static List<String> getValues(){
        return Arrays.stream(values()).map(item->item.value).collect(Collectors.toList());
    }
    public static QuestionSubmitLanguageEnum getEnumByValue(String value){
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        for(QuestionSubmitLanguageEnum anEnum:values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }
}
