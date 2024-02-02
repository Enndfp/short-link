package com.enndfp.shortlink.admin.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 布隆过滤器配置
 *
 * @author Enndfp
 */
@Configuration
public class RbloomFilterConfig {

    /**
     * 防止用户注册查询数据库的布隆过滤器
     */
    @Bean
    public RBloomFilter<String> userRegisterCachePenetrationBloomFilter(RedissonClient redissonClient) {
        // 1. 创建布隆过滤器对象
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter("userRegisterCachePenetrationBloomFilter");
        // 2. 初始化布隆过滤器（预计元素数量为100000000L，误差率为0.001）
        cachePenetrationBloomFilter.tryInit(100000000L, 0.001);

        return cachePenetrationBloomFilter;
    }

    /**
     * 防止Gid生成重复查询数据库的布隆过滤器
     */
    @Bean
    public RBloomFilter<String> gidGenerateCachePenetrationBloomFilter(RedissonClient redissonClient) {
        // 1. 创建布隆过滤器对象
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter("gidGenerateCachePenetrationBloomFilter");
        // 2. 初始化布隆过滤器（预计元素数量为100000000L，误差率为0.001）
        cachePenetrationBloomFilter.tryInit(100000000L, 0.001);

        return cachePenetrationBloomFilter;
    }
}