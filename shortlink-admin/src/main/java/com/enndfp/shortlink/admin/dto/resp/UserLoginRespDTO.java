package com.enndfp.shortlink.admin.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录返回实体
 *
 * @author Enndfp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRespDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户token
     */
    private String token;
}
