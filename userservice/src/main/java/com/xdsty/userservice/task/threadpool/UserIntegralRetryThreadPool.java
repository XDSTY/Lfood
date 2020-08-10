package com.xdsty.userservice.task.threadpool;

import com.xdsty.userservice.task.threadfactory.UserIntegralRetryThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 张富华
 * @date 2020/8/10 13:50
 */
public class UserIntegralRetryThreadPool {

    private static ExecutorService pool = new ThreadPoolExecutor(
            1,
            1,
            10000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new UserIntegralRetryThreadFactory());

    /**
     * 提交任务
     * @param task
     */
    public static void addTask(Runnable task) {
        pool.submit(task);
    }

}
