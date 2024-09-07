package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserAddDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 用户创建请求类
 *
 * @Author Joy_瑶
 * @Create 2024/7/21 19:52
 * @Version 1.0
 */
@Data
public class UserAddDTO implements Serializable {
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 用户角色: user, admin
     */
    private String uesrRole;
    private static final long serialVersionUID = 1L;
}
