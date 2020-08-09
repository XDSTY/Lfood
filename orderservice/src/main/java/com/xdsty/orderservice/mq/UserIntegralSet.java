package com.xdsty.orderservice.mq;

import com.xdsty.orderservice.entity.UserIntegral;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 用户积分数据集合
 */
public class UserIntegralSet {

    private static int DEFAULT_CAPACITY = 1000;

    private static LinkedBlockingDeque<UserIntegral> userIntegrals = new LinkedBlockingDeque<>(DEFAULT_CAPACITY);

    private static UserIntegral lastIntegral;

    public static void addItem(UserIntegral userIntegral) {
        userIntegrals.offer(userIntegral);
    }

    public static UserIntegral poll() {
        return userIntegrals.poll();
    }

    public static UserIntegral poll(long time, TimeUnit timeUnit) throws InterruptedException {
        return userIntegrals.poll(time, timeUnit);
    }

    public static UserIntegral peekLast() {
        return lastIntegral;
    }

    public static int getCapacity() {
        return DEFAULT_CAPACITY;
    }

    public static int getSize() {
        return userIntegrals.size();
    }

    public static void addAll(List<UserIntegral> userIntegralList) {
        userIntegrals.addAll(userIntegralList);
        lastIntegral = userIntegrals.getLast();
    }

}
