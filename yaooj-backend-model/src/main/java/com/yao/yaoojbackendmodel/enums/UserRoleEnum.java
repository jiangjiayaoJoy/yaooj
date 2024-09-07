package com.yao.yaoojbackendmodel.enums;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: UserRoleEnum
 * Package: com.yao.yaoojbackendmy.enums
 * Description:
 * 用户角色枚举
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 10:28
 * @Version 1.0
 */
public enum UserRoleEnum {
    USER("用户","user"),
    ADMIN("管理员","admin"),
    BAN("被封号","ban");
    private final String text;
    private final String value;
    UserRoleEnum(String text,String value){
        this.text=text;
        this.value=value;
    }

    /**
     * 获取值列表
     * @return
     */
    public static List<String> getValues(){
        return Arrays.stream(values()).map(item->item.value).collect(Collectors.toList());
    }

    /**
     * 根据value获取枚举
     * @param value
     * @return
     */
    public static UserRoleEnum getEnumByValue(String value){
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        for(UserRoleEnum anEnum:UserRoleEnum.values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
