package com.xdsty.orderservice.mq.thread;

import com.xdsty.orderbackclient.serializer.OrderBackMessageProto;
import com.xdsty.orderclient.enums.OrderStatusEnum;
import com.xdsty.orderservice.entity.Order;
import com.xdsty.orderservice.mapper.OrderMapper;
import com.xdsty.orderservice.mq.orderback.OrderBackMqConsumer;
import com.xdsty.orderservice.util.ApplicationContextHolder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OrderBackConsumerWorker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(OrderBackConsumerWorker.class);

    @Override
    public void run() {
        log.error("消费订单回滚消息线程启动");
        OrderMapper orderMapper = (OrderMapper) ApplicationContextHolder.context.getBean("orderMapper");
        KafkaConsumer<String, OrderBackMessageProto.OrderRollBackMessage> consumer = OrderBackMqConsumer.newConsumer();
        Duration duration = Duration.ofMillis(1000);
        while (true) {
            try {
                // 获取数据
                ConsumerRecords<String, OrderBackMessageProto.OrderRollBackMessage> records = consumer.poll(duration);
                log.error("订阅订单回滚接收到消息个数{}", records.count());
                for (ConsumerRecord<String, OrderBackMessageProto.OrderRollBackMessage> record : records) {
                    OrderBackMessageProto.OrderRollBackMessage orderRollBackMessage = record.value();
                    // 更新订单信息
                    Order order = new Order();
                    order.setOrderId(orderRollBackMessage.getOrderId());
                    order.setStatus(OrderStatusEnum.ORDER_OVERTIME.getStatus());
                    orderMapper.updateOrder(order);
                }
                consumer.commitSync();
            }catch (Exception e) {
                log.error("处理回滚订单失败", e);
            }
        }
    }
}
