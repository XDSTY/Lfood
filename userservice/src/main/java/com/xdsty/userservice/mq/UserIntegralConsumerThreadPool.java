package com.xdsty.userservice.mq;

import com.xdsty.userservice.mq.thread.UserIntegralConsumerThread;
import com.xdsty.userservice.mq.threadfactory.UserIntegralConsumerThreadFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//@Component
public class UserIntegralConsumerThreadPool implements ApplicationRunner {

    private static ExecutorService pool = new ThreadPoolExecutor(1,
            1,
            10000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new UserIntegralConsumerThreadFactory());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pool.submit(new UserIntegralConsumerThread());
    }
}
