package com.xdsty.orderservice.mq;

import com.xdsty.orderservice.common.UserIntegralConstant;
import com.xdsty.orderservice.mapper.UserIntegralRecordMapper;
import com.xdsty.orderservice.util.ApplicationContextHolder;
import org.springframework.context.ApplicationContext;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 张富华
 * @date 2020/8/7 17:47
 */
public class UserIntegralWorker implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        ApplicationContext context = ApplicationContextHolder.context;
        UserIntegralRecordMapper mapper = (UserIntegralRecordMapper) context.getBean("userIntegralRecordMapper");


    }

    private void initSendingFlag(UserIntegralRecordMapper mapper) {
        // 发送标识还未初始化
        if(UserIntegralConstant.sendingFlag.get() == -1) {
            try{
                lock.lock();
                if(UserIntegralConstant.sendingFlag.get() != -1) {
                    return;
                }
                getUserIntegral(mapper);
                updateSendingFlag();
            }finally {
                lock.unlock();
            }
        }
    }

    private void getUserIntegral(UserIntegralRecordMapper mapper) {
        long id = mapper.getLastRecord();

    }

    private void updateSendingFlag() {

    }

}
