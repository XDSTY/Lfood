package com.xdsty.orderservice.mq;

import com.xdsty.orderservice.entity.UserIntegral;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 用户积分数据集合
 */
public class UserIntegralBlockingQueue {

    private static int DEFAULT_CAPACITY = 1000;

    private static LinkedBlockingDeque<UserIntegral> userIntegrals = new LinkedBlockingDeque<>(DEFAULT_CAPACITY);

    private static UserIntegral lastIntegral;

    public static UserIntegral take() throws InterruptedException {
        return userIntegrals.take();
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

    public static void put(UserIntegral userIntegral) throws InterruptedException {
        userIntegrals.put(userIntegral);
        lastIntegral = userIntegrals.getLast();
    }

}
