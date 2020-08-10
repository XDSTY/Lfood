package com.xdsty.userservice.task.set;

import com.xdsty.userservice.entity.UserIntegral;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author 张富华
 * @date 2020/8/10 9:29
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

    public static void putRetryRecord(UserIntegral userIntegral) throws InterruptedException {
        userIntegrals.put(userIntegral);
    }

    public static void put(UserIntegral userIntegral) throws InterruptedException {
        userIntegrals.put(userIntegral);
        lastIntegral = userIntegral;
    }

}
