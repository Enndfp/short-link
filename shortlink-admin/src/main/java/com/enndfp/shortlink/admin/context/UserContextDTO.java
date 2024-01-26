package com.enndfp.shortlink.admin.context;

import lombok.Data;

/**
 * 用户上下文信息传输实体
 *
 * @author Enndfp
 */
@Data
public class UserContextDTO {

    /**
     * 用户 ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;
}