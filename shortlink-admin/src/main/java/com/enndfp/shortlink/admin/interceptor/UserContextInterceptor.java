package com.enndfp.shortlink.admin.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.context.UserContext;
import com.enndfp.shortlink.admin.context.UserContextDTO;
import com.enndfp.shortlink.admin.utils.ThrowUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.enndfp.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
 * 用户上下文信息传递拦截器
 *
 * @author Enndfp
 */
@RequiredArgsConstructor
public class UserContextInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从请求头中获取用户名和token
        String username = request.getHeader("username");
        String token = request.getHeader("token");

        // 2. 校验用户是否登录
        String userInfoJsonStr = (String) stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username, token);
        ThrowUtil.throwServerIf(StrUtil.isEmpty(userInfoJsonStr), ErrorCode.USER_NOT_LOGIN);

        // 3. 将用户信息放入上下文
        UserContextDTO userContextDTO = JSONUtil.toBean(userInfoJsonStr, UserContextDTO.class);
        UserContext.setUser(userContextDTO);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 1. 清除用户上下文信息
        UserContext.removeUser();
    }
}
