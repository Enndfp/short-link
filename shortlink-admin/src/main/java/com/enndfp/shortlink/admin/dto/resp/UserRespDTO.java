package com.enndfp.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * 用户信息返回实体
 *
 * @author Enndfp
 */
@Data
public class UserRespDTO {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
}
