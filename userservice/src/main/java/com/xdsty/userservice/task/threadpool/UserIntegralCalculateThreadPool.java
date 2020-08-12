package com.xdsty.userservice.task.threadpool;

import com.xdsty.userservice.task.thread.UserIntegralCalculateThread;
import com.xdsty.userservice.task.threadfactory.UserIntegralCalculateThreadFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 张富华
 * @date 2020/8/10 9:15
 */
//@Component
public class UserIntegralCalculateThreadPool implements ApplicationRunner {

    private static ExecutorService pool = new ThreadPoolExecutor(1,
            2,
            10000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new UserIntegralCalculateThreadFactory());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pool.submit(new UserIntegralCalculateThread());
        pool.submit(new UserIntegralCalculateThread());
    }
}
