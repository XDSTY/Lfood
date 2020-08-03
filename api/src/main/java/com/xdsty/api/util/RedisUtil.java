package com.xdsty.api.util;

import com.xdsty.api.common.ApplicationContextHolder;
import com.xdsty.api.common.exceptions.ApiRuntimeException;
import com.xdsty.api.config.redis.AlgRedisTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public final class RedisUtil {

    private static AlgRedisTemplate redisTemplate;

    private static StringRedisTemplate stringRedisTemplate;

    static {
        redisTemplate = (AlgRedisTemplate) ApplicationContextHolder.getApplicationContext().getBean("algRedisTemplate");
        stringRedisTemplate = (StringRedisTemplate) ApplicationContextHolder.getApplicationContext().getBean("stringRedisTemplate");
    }

    public static boolean existKey(String key) {
        Boolean val = redisTemplate.hasKey(key);
        return Optional.ofNullable(val).orElse(false);
    }


    public static boolean expire(String key, long time) {
        Boolean val = redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(val).orElse(false);
    }

    public static boolean delKey(String key) {
        return expire(key, 0L);
    }

    public static long getExpireTime(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new ApiRuntimeException("redis key不能为空");
        }
        Long val = redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(val).orElse(-1L);
    }


    public static void set(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
    }

    public static void set(String key, Object val, long time) {
        redisTemplate.opsForValue().set(key, val, time, TimeUnit.MILLISECONDS);
    }

    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 向hash中添加数据 hash不存在则创建
     *
     * @param key  hash的key
     * @param item hash中item的key
     * @param val  hash中item的val
     */
    public static void hset(String key, String item, Object val) {
        redisTemplate.opsForHash().put(key, item, val);
    }

    /**
     * 向hash中添加数据 hash不存在则创建
     *
     * @param key  hash的key
     * @param item hash中item的key
     * @param val  hash中item的val
     * @param ttl  过期时间 毫秒
     */
    public static void hset(String key, String item, Object val, long ttl) {
        redisTemplate.opsForHash().put(key, item, val);
        if (ttl > 0) {
            redisTemplate.expire(key, ttl, TimeUnit.MILLISECONDS);
        }
    }

    public static void hmset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public static void hmset(String key, Map<String, Object> map, long ttl) {
        redisTemplate.opsForHash().putAll(key, map);
        if (ttl > 0) {
            redisTemplate.expire(key, ttl, TimeUnit.MILLISECONDS);
        }
    }

    public static void sset(String key, long ttl, Object... val) {
        redisTemplate.opsForSet().add(key, val);
        if (ttl > 0) {
            redisTemplate.expire(key, ttl, TimeUnit.MILLISECONDS);
        }
    }

    public static boolean sexist(String key, Object val) {
        Boolean exist = redisTemplate.opsForSet().isMember(key, val);
        return Optional.ofNullable(exist).orElse(false);
    }
}
