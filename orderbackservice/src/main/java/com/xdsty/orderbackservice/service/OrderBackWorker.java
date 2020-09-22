package com.xdsty.orderbackservice.service;

import com.xdsty.orderbackclient.message.OrderRollBackInfo;
import com.xdsty.orderbackclient.serializer.OrderBackMessageProto;
import com.xdsty.orderbackservice.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
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
        String zsetName = null, uuidKey = null, redisKeyName = null;
        while (true) {
            try {
                log.error("开始获取zset");
                //随机获取一个zset并尝试加锁 加锁失败则重试
                uuidKey = UUID.randomUUID().toString();
                zsetName = ZsetListUtil.random();
                redisKeyName = Constant.REDIS_LOCK_PREFIX + zsetName;
                if (Objects.isNull(zsetName) || !RedisLockUtil.lock(redisKeyName, uuidKey)) {
                    Thread.sleep(1000);
                    log.error("加锁失败重试");
                    continue;
                }

                // 根据score(过期时间)获取数据
                Set<Object> orderIds = RedisUtil.zrangeByScore(zsetName, 0, System.currentTimeMillis(), 0, 1);
                if (CollectionUtils.isEmpty(orderIds)) {
                    Thread.sleep(1000);
                    log.error("没有符合数据进行重试");
                    continue;
                }
                Long orderId = (Long) orderIds.stream().findFirst().orElse(null);

                // 处理数据 将订单回退信息发送到mq，由库存和订单服务消费
                OrderRollBackInfo rollBackInfo = JsonUtil.parseJson(RedisUtil.get(Constant.ORDER_BACK_PREFIX + orderId), OrderRollBackInfo.class);
                log.error("回退订单id: {}, 信息: {}", orderId, rollBackInfo);

                // 确保mq数据发送成功之后 zset中删除对应的节点
                RedisUtil.zrem(zsetName, orderId);
            }catch (Exception e) {
                log.error("获取回滚订单失败", e);
            }finally {
                if(!StringUtils.isEmpty(redisKeyName) && !StringUtils.isEmpty(uuidKey)) {
                    RedisLockUtil.unlock(redisKeyName, uuidKey);
                }
            }
        }
    }

    public static void main(String[] args) {
        long val = 268435456;
        val = val / 1024 / 1024;
        System.out.println(val);
    }
}
