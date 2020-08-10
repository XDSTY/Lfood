package com.xdsty.userservice.task.thread;

import com.xdsty.userservice.entity.UserIntegral;
import com.xdsty.userservice.task.set.UserIntegralBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 张富华
 * @date 2020/8/10 11:21
 */
public class UserIntegralRetryThread implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(UserIntegralRetryThread.class);

    private static final int RETRY = 3;

    private UserIntegral userIntegral;

    public UserIntegralRetryThread(UserIntegral userIntegral) {
        this.userIntegral = userIntegral;
    }

    @Override
    public void run() {
        log.error("重新放入集合任务: {}, {}", userIntegral, Thread.currentThread().getName());
        int retry = RETRY;
        while (retry -- > 0) {
            try {
                UserIntegralBlockingQueue.put(userIntegral);
            } catch (InterruptedException e) {
                log.error("重试放入集合失败, retry: {}, record: {}, {}", RETRY - retry, userIntegral, e);
                continue;
            }
            break;
        }
    }
}
