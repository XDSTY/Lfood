package com.xdsty.orderservice.mq.thread;

import com.xdsty.orderservice.common.Constant;
import com.xdsty.orderservice.entity.UserIntegral;
import com.xdsty.orderservice.mq.MessageSendCallback;
import com.xdsty.orderservice.mq.MqSender;
import com.xdsty.orderservice.mq.UserIntegralBlockingQueue;
import com.xdsty.userclient.serializer.UserIntegralMessageProto;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserIntegralSenderWorker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(UserIntegralSenderWorker.class);

    @Override
    public void run() {
        log.error("发送者线程开始" + Thread.currentThread().getName());
        UserIntegral userIntegral = null;
        KafkaProducer producer = MqSender.newKafkaProducer();
        while (true) {
            try {
                try {
                    userIntegral = UserIntegralBlockingQueue.take();
                }catch (InterruptedException e) {
                    log.error("从积分集合获取数据失败", e);
                }
                if(userIntegral != null) {
                    UserIntegralMessageProto.UserIntegralMessage message = convert2UserIntegralMessage(userIntegral);
                    ProducerRecord<String, UserIntegralMessageProto.UserIntegralMessage> record = new ProducerRecord<>(Constant.USER_INTEGRAL_TOPIC, message);
                    producer.send(record, new MessageSendCallback(record));
                }
            } catch (Exception e) {
                log.error("用户积分消费者异常", e);
            }
        }
    }

    private static UserIntegralMessageProto.UserIntegralMessage convert2UserIntegralMessage(UserIntegral userIntegral) {
        return UserIntegralMessageProto.UserIntegralMessage.newBuilder()
                .setId(userIntegral.getId())
                .setIntegral(userIntegral.getIntegral())
                .setOrderId(userIntegral.getOrderId())
                .setUserId(userIntegral.getUserId())
                .build();
    }
}
