package com.xdsty.orderservice.mq;

import com.xdsty.orderservice.common.UserIntegralConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Properties;

/**
 * @author 张富华
 * @date 2020/8/7 13:59
 */
@Component
@EnableConfigurationProperties(value = KafkaConfig.class)
public class MqSender implements ApplicationRunner {

    private static KafkaProducer userIntegralSender;

    private KafkaConfig kafkaConfig;

    @Autowired
    public void setKafkaConfig(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    private void initUserIntegralSender() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaConfig.getBootstrapServers());
        properties.setProperty("key.serializer", kafkaConfig.getKeySerializer());
        properties.setProperty("value.serializer", kafkaConfig.getValueSerializer());
        properties.setProperty("acks", kafkaConfig.getAcks());
        userIntegralSender = new KafkaProducer<>(properties);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initUserIntegralSender();
    }

    public static <T> void send(String topic, T message) {
        ProducerRecord record = new ProducerRecord(topic, message);
        userIntegralSender.send(record, new MessageSendCallback(record));
    }
}
