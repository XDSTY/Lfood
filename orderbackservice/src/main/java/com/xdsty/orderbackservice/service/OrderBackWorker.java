package com.xdsty.orderbackservice.service;

import com.xdsty.orderbackclient.message.OrderRollBackInfo;
import com.xdsty.orderbackclient.serializer.OrderBackMessageProto;
import com.xdsty.orderbackclient.serializer.OrderRollBackProductMessageProto;
import com.xdsty.orderbackservice.util.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 从redis的zset获取数据并进行处理
 * @author 张富华
 * @date 2020/9/21 10:48
 */
public class OrderBackWorker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(OrderBackWorker.class);

    private KafkaProducer kafkaProducer;

    public OrderBackWorker() {
    }

    public OrderBackWorker(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void run() {
        String zsetName, redisKeyName = null;
        RLock lock = null;
        while (true) {
            try {
                log.error("开始获取zset");
                //随机获取一个zset并尝试加锁 加锁失败则重试
                zsetName = ZsetListUtil.random();
                if (Objects.isNull(zsetName)) {
                    Thread.sleep(1000);
                    continue;
                }
                redisKeyName = Constant.REDIS_LOCK_PREFIX + zsetName;
                lock = RedissonUtil.getClient().getLock(redisKeyName);

                // 尝试加锁
                if(!lock.tryLock(3000, TimeUnit.MILLISECONDS)) {
                    log.error("{}加锁失败，尝试其它zset", zsetName);
                    continue;
                }
                log.error("{}加锁成功，开始尝试获取过期的订单", zsetName);

                // 加锁成功则一直处理当前zset的过期订单直到没有符合的记录
                Set<Object> orderIds;
                while (!CollectionUtils.isEmpty(orderIds = RedisUtil.zrangeByScore(zsetName, 0, System.currentTimeMillis(), 0, 1))) {
                    Long orderId = (Long) orderIds.stream().findFirst().orElse(null);
                    // 处理数据 将订单回退信息发送到mq，由库存和订单服务消费
                    OrderRollBackInfo rollBackInfo = JsonUtil.parseJson(RedisUtil.get(Constant.ORDER_BACK_PREFIX + orderId), OrderRollBackInfo.class);
                    log.error("回退订单id: {}, 信息: {}", orderId, rollBackInfo);
                    OrderBackMessageProto.OrderRollBackMessage message = constructOrderBackInfo(rollBackInfo);
                    ProducerRecord<String, OrderBackMessageProto.OrderRollBackMessage> record = new ProducerRecord<>(Constant.ORDER_ROLLBACK_TOPIC, message);
                    try {
                        kafkaProducer.send(record).get();
                    } catch (Exception e) {
                        log.error("发送到topic:order-rollback失败，信息: {}, 异常: {}", rollBackInfo, e);
                    }
                    // 确保mq数据发送成功之后 zset中删除对应的节点
                    RedisUtil.zrem(zsetName, orderId);
                }
            }catch (Exception e) {
                log.error("获取回滚订单失败", e);
            }finally {
                if(!StringUtils.isEmpty(redisKeyName)) {
                    lock.unlock();
                }
            }
        }
    }

    private OrderBackMessageProto.OrderRollBackMessage constructOrderBackInfo(OrderRollBackInfo orderRollBackInfo) {
        OrderBackMessageProto.OrderRollBackMessage.Builder orderRollBackMessageBuilder =
                OrderBackMessageProto.OrderRollBackMessage.newBuilder()
                .setOrderId(orderRollBackInfo.getOrderId())
                .setCreateTime(orderRollBackInfo.getCreateTime())
                .setEndTime(orderRollBackInfo.getEndTime())
                .setStatus(orderRollBackInfo.getStatus());

        orderRollBackMessageBuilder.addAllProductList(orderRollBackInfo.getProductList().stream()
                .map(e -> OrderRollBackProductMessageProto.OrderRollBackProductMessage.newBuilder()
                .setProductId(e.getProductId())
                .setProductNum(e.getProductNum()).build()).collect(Collectors.toList()));
        return orderRollBackMessageBuilder.build();
    }

}
