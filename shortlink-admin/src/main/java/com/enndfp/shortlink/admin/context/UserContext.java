package com.enndfp.shortlink.admin.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Optional;

/**
 * 用户上下文
 *
 * @author Enndfp
 */
public final class UserContext {

    private static final ThreadLocal<UserContextDTO> USER_THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 设置用户至上下文
     *
     * @param user 用户详情信息
     */
    public static void setUser(UserContextDTO user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取上下文中用户 ID
     *
     * @return 用户 ID
     */
    public static String getUserId() {
        UserContextDTO userContextDTO = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userContextDTO).map(UserContextDTO::getId).orElse(null);
    }

    /**
     * 获取上下文中用户名称
     *
     * @return 用户名称
     */
    public static String getUsername() {
        UserContextDTO userContextDTO = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userContextDTO).map(UserContextDTO::getUsername).orElse(null);
    }

    /**
     * 获取上下文中用户真实姓名
     *
     * @return 用户真实姓名
     */
    public static String getRealName() {
        UserContextDTO userContextDTO = USER_THREAD_LOCAL.get();
        return Optional.ofNullable(userContextDTO).map(UserContextDTO::getRealName).orElse(null);
    }

    /**
     * 清理用户上下文
     */
    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }
}