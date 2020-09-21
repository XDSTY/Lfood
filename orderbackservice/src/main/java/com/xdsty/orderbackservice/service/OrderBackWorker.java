package com.xdsty.orderbackservice.service;

import com.xdsty.orderbackservice.util.Constant;
import com.xdsty.orderbackservice.util.RedisLockUtil;
import com.xdsty.orderbackservice.util.RedisUtil;
import com.xdsty.orderbackservice.util.ZsetListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.util.Set;
import java.util.UUID;

/**
 * 从redis的zset获取数据并进行处理
 * @author 张富华
 * @date 2020/9/21 10:48
 */
public class OrderBackWorker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(OrderBackWorker.class);

    @Override
    public void run() {
        while (true) {
            //随机获取一个zset并尝试加锁 加锁失败则重试
            String uuidKey = UUID.randomUUID().toString();
            String zsetName = ZsetListUtil.random();
            if(!RedisLockUtil.lock(zsetName, uuidKey)) {
                continue;
            }

            // 根据score(过期时间)获取数据
            Set<Object> orderIds = RedisUtil.zrangeByScore(zsetName, 0, System.currentTimeMillis(), 0, 1);
            if(CollectionUtils.isEmpty(orderIds)) {
                continue;
            }
            Long orderId = (Long) orderIds.stream().findFirst().orElse(null);

            // zset中删除对应的节点
            RedisUtil.zrem(zsetName, orderId);
            // 释放锁
            RedisLockUtil.unlock(zsetName, uuidKey);

            // 处理数据 将订单回退信息发送到mq，由库存和订单服务消费
            OrderRollBackInfo rollBackInfo = (OrderRollBackInfo) RedisUtil.get(Constant.ORDER_BACK_PREFIX + orderId);
            log.error("回退订单id: {}, 信息: {}", orderId, rollBackInfo);
        }
    }
}
