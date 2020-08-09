package com.xdsty.orderservice.mq.thread;

import com.xdsty.orderservice.common.Constant;
import com.xdsty.orderservice.entity.UserIntegral;
import com.xdsty.orderservice.mq.MessageSendCallback;
import com.xdsty.orderservice.mq.MqSender;
import com.xdsty.orderservice.mq.UserIntegralSet;
import com.xdsty.userclient.message.UserIntegralMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class UserIntegralSenderWorker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(UserIntegralSenderWorker.class);

    @Override
    public void run() {
        log.error("发送者线程开始" + Thread.currentThread().getName());
        UserIntegral userIntegral;
        KafkaProducer producer = MqSender.newKafkaProducer();
        while (true) {
            try {
                userIntegral = UserIntegralSet.poll(1000, TimeUnit.MILLISECONDS);
                if(userIntegral != null) {
                    UserIntegralMessage message = convert2UserIntegralMessage(userIntegral);
                    ProducerRecord<String, UserIntegralMessage> record = new ProducerRecord<>(Constant.USER_INTEGRAL_TOPIC, message);
                    producer.send(record, new MessageSendCallback(record));
                }
            } catch (Exception e) {
                log.error("用户积分消费者异常", e);
            }
        }
    }

    private static UserIntegralMessage convert2UserIntegralMessage(UserIntegral userIntegral) {
        UserIntegralMessage message = new UserIntegralMessage();
        message.setId(userIntegral.getId());
        message.setUserId(userIntegral.getUserId());
        message.setIntegral(userIntegral.getIntegral());
        message.setOrderId(userIntegral.getOrderId());
        return message;
    }
}
