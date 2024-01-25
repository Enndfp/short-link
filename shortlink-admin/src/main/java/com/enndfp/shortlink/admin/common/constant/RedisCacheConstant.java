package com.enndfp.shortlink.admin.common.constant;

/**
 * Redis缓存常量
 *
 * @author Enndfp
 */
public class RedisCacheConstant {

    // 用户注册锁
    public static final String LOCK_USER_REGISTER_KEY = "short-link:lock:user-register:";

    // 用户登录token
    public static final String USER_LOGIN_KEY = "user:login:";
    // 用户登录token过期时间
    public static final Long USER_LOGIN_TTL = 30L;

}
