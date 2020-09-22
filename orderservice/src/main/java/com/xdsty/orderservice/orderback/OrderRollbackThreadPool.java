package com.xdsty.orderservice.orderback;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 订单15分钟回滚线程池
 */
@Deprecated
public class OrderRollbackThreadPool{

    private static ExecutorService pool = new ThreadPoolExecutor(1,
            1,
            10000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new OrderRollbackThreadFactory());

    public static void addTask(OrderRollbackTask task){
        pool.submit(task);
    }
}
