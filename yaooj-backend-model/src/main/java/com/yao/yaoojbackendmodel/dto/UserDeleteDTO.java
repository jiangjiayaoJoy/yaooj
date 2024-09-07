package com.yao.yaoojbackendmodel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserDeleteDTO
 * Package: com.yao.yaoojbackendmy.model.dto
 * Description:
 * 删除用户请求
 *
 * @Author Joy_瑶
 * @Create 2024/7/21 20:16
 * @Version 1.0
 */
@Data
public class UserDeleteDTO implements Serializable {
    /**
     * id
     */
    private Long id;
    private static final long serialVersionUID = 1L;
}
