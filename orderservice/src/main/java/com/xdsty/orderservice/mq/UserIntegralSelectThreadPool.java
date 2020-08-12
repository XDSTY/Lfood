package com.xdsty.orderservice.mq;

import com.xdsty.orderservice.mq.thread.UserIntegralSelectWorker;
import com.xdsty.orderservice.mq.threadfactory.UserIntegralSelectThreadFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//@Component
//@Order(1024)
public class UserIntegralSelectThreadPool implements ApplicationRunner {

    private static ExecutorService pool = new ThreadPoolExecutor(1,
            1,
            10000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new UserIntegralSelectThreadFactory());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pool.submit(new UserIntegralSelectWorker());
    }
}
