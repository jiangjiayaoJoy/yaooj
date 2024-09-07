package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserRegisterDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 用户注册请求体
 *
 * @Author Joy_瑶
 * @Create 2024/7/18 21:57
 * @Version 1.0
 */
@Data
public class UserRegisterDTO implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;//注册的用户名

    private String userPassword;//注册的密码

    private String checkPassword;//第二次输入的注册密码
}
