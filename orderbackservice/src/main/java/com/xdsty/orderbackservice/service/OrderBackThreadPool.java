package com.xdsty.orderbackservice.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 张富华
 * @date 2020/9/21 10:43
 */
@Component
public class OrderBackThreadPool implements ApplicationRunner {

    private static ExecutorService pool = new ThreadPoolExecutor(1,
            1,
            10000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new OrderRollbackThreadFactory());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pool.submit(new OrderBackWorker());
    }
}
