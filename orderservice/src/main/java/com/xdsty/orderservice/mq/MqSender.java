package com.xdsty.orderservice.mq;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author 张富华
 * @date 2020/8/7 13:59
 */
@Component
@EnableConfigurationProperties(value = KafkaConfig.class)
public class MqSender {

    private static final Logger log = LoggerFactory.getLogger(MqSender.class);

    private static KafkaConfig kafkaConfig;

    @Autowired
    public void setKafkaConfig(KafkaConfig kafkaConfig) {
        MqSender.kafkaConfig = kafkaConfig;
    }

    private static KafkaProducer initUserIntegralSender() {
        KafkaProducer userIntegralSender;
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaConfig.getBootstrapServers());
        properties.setProperty("key.serializer", kafkaConfig.getKeySerializer());
        properties.setProperty("value.serializer", kafkaConfig.getValueSerializer());
        properties.setProperty("acks", kafkaConfig.getAcks());
        System.out.println(properties);
        return new KafkaProducer<>(properties);
    }

    public static KafkaProducer newKafkaProducer() {
        return initUserIntegralSender();
    }
}
