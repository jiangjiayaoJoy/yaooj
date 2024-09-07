package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserLoginDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 用户登录请求类
 *
 * @Author Joy_瑶
 * @Create 2024/7/17 20:05
 * @Version 1.0
 */
@Data
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID=3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
