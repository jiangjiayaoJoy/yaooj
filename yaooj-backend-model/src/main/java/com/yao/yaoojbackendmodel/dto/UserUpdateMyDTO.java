package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserUpdateMyDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 用户更新个人信息请求
 *
 * @Author Joy_瑶
 * @Create 2024/7/21 20:41
 * @Version 1.0
 */
@Data
public class UserUpdateMyDTO implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    private static final long serialVersionUID = 1L;
}
