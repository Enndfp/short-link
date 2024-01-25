package com.enndfp.shortlink.admin.dto.req.user;

import lombok.Data;

/**
 * 用户登录请求实体
 *
 * @author Enndfp
 */
@Data
public class UserLoginReqDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
