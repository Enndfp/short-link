package com.enndfp.shortlink.admin.config;

import com.enndfp.shortlink.admin.interceptor.UserContextInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 拦截器配置类
 *
 * @author Enndfp
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final List<String> EXCLUDE_PATHS = List.of(
            "/user/login",
            "/user/register",
            "/user/check-username"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new UserContextInterceptor(stringRedisTemplate))
                .excludePathPatterns(EXCLUDE_PATHS)
                .order(0);
    }
}
