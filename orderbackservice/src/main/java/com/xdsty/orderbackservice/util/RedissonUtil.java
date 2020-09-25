package com.xdsty.orderbackservice.util;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

/**
 * @author 张富华
 * @date 2020/9/25 10:29
 */
@Component
public class RedissonUtil {

    private static RedissonClient client;

    public RedissonUtil() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonUtil.client = Redisson.create(config);
    }

    public static RedissonClient getClient() {
        return client;
    }
}
