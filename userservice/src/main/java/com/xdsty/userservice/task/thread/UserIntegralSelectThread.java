package com.xdsty.userservice.task.thread;

import com.xdsty.userservice.entity.UserIntegral;
import com.xdsty.userservice.mapper.UserIntegralMapper;
import com.xdsty.userservice.task.set.UserIntegralBlockingQueue;
import com.xdsty.userservice.util.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/8/10 9:50
 */
public class UserIntegralSelectThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(UserIntegralSelectThread.class);

    @Override
    public void run() {
        log.error("查询线程开始" + Thread.currentThread().getName());
        UserIntegralMapper mapper = (UserIntegralMapper) ApplicationContextHolder.context.getBean("userIntegralMapper");
        while (true) {
            // 寻找集合中最后一个记录
            UserIntegral lastUserIntegral = UserIntegralBlockingQueue.peekLast();
            List<UserIntegral> userIntegrals = mapper.getLastRecord(lastUserIntegral == null ? null : lastUserIntegral.getId());
            // 查询数据为空，则休眠100毫秒后重新查询
            if (!CollectionUtils.isEmpty(userIntegrals)) {
                log.error("查询用户积分记录，起始ID: {}, 结束ID: {}", userIntegrals.get(0).getId(), userIntegrals.get(userIntegrals.size() - 1).getId());
                try {
                    addAddToSet(userIntegrals);
                }catch (InterruptedException e) {
                    log.error("放入集合失败", e);
                }
            } else {
                log.error("生产者线程休眠");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("查询用户积分记录线程sleep异常", e);
                }
            }
        }
    }

    /**
     * 将积分数据放入集合，抛出异常则停止后续的放入操作
     * @param userIntegrals
     * @throws InterruptedException
     */
    private void addAddToSet(List<UserIntegral> userIntegrals) throws InterruptedException {
        for(UserIntegral userIntegral : userIntegrals) {
            UserIntegralBlockingQueue.put(userIntegral);
        }
    }
}
