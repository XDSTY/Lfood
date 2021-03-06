package com.xdsty.orderservice.mq.thread;

import com.xdsty.orderservice.entity.UserIntegral;
import com.xdsty.orderservice.mapper.UserIntegralRecordMapper;
import com.xdsty.orderservice.mq.UserIntegralBlockingQueue;
import com.xdsty.orderservice.util.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 读取用户积分信息线程
 * 开启循环一直读取数据库，当读取数据为空时，睡眠一段时间再进行读取
 * @author 张富华
 * @date 2020/8/7 17:47
 */
public class UserIntegralSelectWorker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(UserIntegralSelectWorker.class);

    @Override
    public void run() {
        log.error("查询线程开始" + Thread.currentThread().getName());
        UserIntegralRecordMapper mapper = (UserIntegralRecordMapper) ApplicationContextHolder.context.getBean("userIntegralRecordMapper");
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
