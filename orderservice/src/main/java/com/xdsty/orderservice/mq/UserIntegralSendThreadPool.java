package com.xdsty.orderservice.mq;

import com.xdsty.orderservice.mq.thread.UserIntegralSenderWorker;
import com.xdsty.orderservice.mq.threadfactory.UserIntegralSendThreadFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 张富华
 * @date 2020/8/7 17:31
 */
@Component
public class UserIntegralSendThreadPool implements ApplicationRunner {

    private static ExecutorService pool = new ThreadPoolExecutor(1,
            1,
            10000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new UserIntegralSendThreadFactory());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pool.submit(new UserIntegralSenderWorker());
    }
}
