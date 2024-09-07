package com.yao.yaoojbackendmodel.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: UserVO
 * Package: com.yao.yaoojbackendmy.model.vo
 * Description:
 * 用户视图（脱敏）
 *
 * @Author Joy_瑶
 * @Create 2024/7/21 20:32
 * @Version 1.0
 */
@Data
public class UserVO implements Serializable {

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
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
