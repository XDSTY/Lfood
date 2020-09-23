package com.xdsty.productservice.mq.orderback;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class OrderBackConsumerThreadPool implements ApplicationRunner {

    private static ExecutorService pool = new ThreadPoolExecutor(1,
            1,
            10000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pool.submit(new OrderBackConsumerWorker());
    }
}
