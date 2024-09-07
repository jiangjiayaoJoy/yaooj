package com.yao.yaoojbackendmodel.enums;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: JudgeInfoMessageEnum
 * Package: com.yao.yaoojbackendmy.enums
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 17:12
 * @Version 1.0
 */
public enum JudgeInfoMessageEnum {
    ACCEPTED("成功","Accepted"),
    WRONG_ANSWER("答案错误","Wrong Answer"),
    COMPILE_ERROR("编译错误","Compile Error"),
    MEMORY_LIMIT_EXCEEDED("内存溢出","Memory Limit Exceeded"),
    TIME_LIMIT_EXCEEDED("超时","Time Limit Exceeded"),
    PRESENTATION_ERROR("展示错误","Presentation Error"),
    WAITING("等待中","Waiting"),
    OUTPUT_LIMIT_EXCEEDED("输出溢出","Output Limit Exceeded"),
    DANGEROUS_OPERATION("危险操作","Dangerous Operation"),
    RUNTIME_ERROR("运行错误","Runtime Error"),
    SYSTEM_ERROR("系统错误","System Error");

    private final String text;
    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
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

    public static JudgeInfoMessageEnum getEnumByValue(String value){
        if(StrUtil.isEmpty(value)){
            return null;
        }
        for(JudgeInfoMessageEnum anEnum:values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }
}
