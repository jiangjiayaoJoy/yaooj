package com.yao.yaoojbackendmodel.dto;

import com.yao.yaoojbackendcommon.common.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ClassName: UserQueryDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 用户查询请求
 *
 * @Author Joy_瑶
 * @Create 2024/7/24 16:20
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDTO extends PageDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 开放平台id
     */
    private String unionId;

    /**
     * 公众号openId
     */
    private String mpOpenId;

    /**
     * 用户昵称
     */
    private String userName;

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
