package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserUpdateDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 用户更新请求
 *
 * @Author Joy_瑶
 * @Create 2024/7/21 20:20
 * @Version 1.0
 */
@Data
public class UserUpdateDTO implements Serializable {
    /**
     * id
     */
    private Long id;

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

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
