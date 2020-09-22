package com.xdsty.userservice.mq.thread;

import com.xdsty.userclient.serializer.UserIntegralMessageProto;
import com.xdsty.userservice.entity.UserIntegral;
import com.xdsty.userservice.mapper.UserIntegralMapper;
import com.xdsty.userservice.mq.MqConsumer;
import com.xdsty.userservice.util.ApplicationContextHolder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class UserIntegralConsumerThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(UserIntegralConsumerThread.class);

    @Override
    public void run() {
        log.error("消费者线程开始" + Thread.currentThread().getName());
        UserIntegralMapper mapper = (UserIntegralMapper) ApplicationContextHolder.context.getBean("userIntegralMapper");
        KafkaConsumer<String, UserIntegralMessageProto.UserIntegralMessage> consumer = MqConsumer.newConsumer();
        Duration duration = Duration.ofMillis(1000);
        while (true) {
            // 获取数据
            ConsumerRecords<String, UserIntegralMessageProto.UserIntegralMessage> records = consumer.poll(duration);
            log.error("获取到数据数量: {}", records.count());
            if(records.count() > 0) {
                List<UserIntegral> list = new ArrayList<>(records.count());
                for (ConsumerRecord<String, UserIntegralMessageProto.UserIntegralMessage> record : records) {
                    log.error("接收到数据: {}", record.value());
                    list.add(convert2UserIntegral(record.value()));
                }
                // 数据放入db
                insertToDb(mapper, list);
            }
            consumer.commitAsync();
        }
    }

    private UserIntegral convert2UserIntegral(UserIntegralMessageProto.UserIntegralMessage message) {
        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setId(message.getId());
        userIntegral.setIntegral(message.getIntegral());
        userIntegral.setOrderId(message.getOrderId());
        userIntegral.setUserId(message.getUserId());
        return userIntegral;
    }

    /**
     * 数据插入db
     * 先尝试批量插入，批量插入失败说明有重复的数据，则一个一个进行插入
     * @param mapper
     * @param list
     */
    private void insertToDb(UserIntegralMapper mapper, List<UserIntegral> list) {
        try{
            mapper.insertList(list);
        }catch (Exception e) {
            log.error("批量插入失败，进行单个插入", e);
            //批量插入失败
            for (UserIntegral integral : list) {
                try {
                    mapper.insertOne(integral);
                }catch (Exception ex){
                    log.error("单个插入失败 {} {}", integral, ex);
                }
            }
        }
    }
}
