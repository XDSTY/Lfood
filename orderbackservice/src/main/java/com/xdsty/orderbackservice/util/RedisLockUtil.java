package com.xdsty.orderbackservice.util;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 张富华
 * @date 2020/9/21 10:26
 */
public final class RedisLockUtil {

    /**
     * 锁定时间，默认20秒
     */
    private static final long LOCK_TIME = 20 * 1000;

    /**
     * 简单的redis分布式锁，加锁
     * @param lockName
     * @param lockMember
     * @return
     */
    public static boolean lock(String lockName, String lockMember) {
        return RedisUtil.setnx(lockName, lockMember, LOCK_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * redis分布式锁解锁
     * @param lockName
     * @param lockMember
     * @return
     */
    public static boolean unlock(String lockName, String lockMember) {
        String lockVal = (String) RedisUtil.get(lockName);
        // 锁已经超时或者 加锁人不是当前人则解锁失败
        if(Objects.isNull(lockVal) || !lockVal.equals(lockMember)) {
            return false;
        }
        return RedisUtil.expire(lockName, 0);
    }

}
