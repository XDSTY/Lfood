package com.xdsty.userservice.task.thread;

import com.xdsty.userservice.entity.UserIntegral;
import com.xdsty.userservice.service.UserIntegralService;
import com.xdsty.userservice.task.set.UserIntegralBlockingQueue;
import com.xdsty.userservice.task.threadpool.UserIntegralRetryThreadPool;
import com.xdsty.userservice.util.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 张富华
 * @date 2020/8/10 9:17
 */
public class UserIntegralCalculateThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(UserIntegralCalculateThread.class);

    private static final int RETRY = 3;

    @Override
    public void run() {
        log.error("计算用户积分线程开始 " + Thread.currentThread().getName());
        UserIntegralService service = (UserIntegralService) ApplicationContextHolder.context.getBean("userIntegralService");
        while (true) {
            UserIntegral userIntegral = null;
            try {
                userIntegral = UserIntegralBlockingQueue.take();
            } catch (InterruptedException e) {
                log.error("从积分集合获取积分记录失败");
                continue;
            }
            int retry = RETRY;
            // 插入失败则重试，最多3次
            while (retry-- > 0) {
                try {
                    service.calculateUserIntegral(userIntegral);
                }catch (Exception e) {
                    log.error("插入数据库失败, retry: {}, record: {}", RETRY - retry, userIntegral);
                    log.error("异常", e);
                    continue;
                }
                break;
            }
            if(retry == 0) {
                // 重试后还是失败，则异步的重新加入集合，这里如果同步的加入集合会造成死锁
                UserIntegralRetryThreadPool.addTask(new UserIntegralRetryThread(userIntegral));
            }
        }
    }
}
