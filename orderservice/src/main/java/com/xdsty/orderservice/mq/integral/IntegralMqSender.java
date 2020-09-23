package com.xdsty.orderservice.mq.integral;

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
@EnableConfigurationProperties(value = IntegralMqConfig.class)
public class IntegralMqSender {

    private static final Logger log = LoggerFactory.getLogger(IntegralMqSender.class);

    private static IntegralMqConfig integralMqConfig;

    @Autowired
    public void setKafkaConfig(IntegralMqConfig integralMqConfig) {
        IntegralMqSender.integralMqConfig = integralMqConfig;
    }

    private static KafkaProducer initUserIntegralSender() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", integralMqConfig.getBootstrapServers());
        properties.setProperty("key.serializer", integralMqConfig.getKeySerializer());
        properties.setProperty("value.serializer", integralMqConfig.getValueSerializer());
        properties.setProperty("acks", integralMqConfig.getAcks());
        return new KafkaProducer<>(properties);
    }

    public static KafkaProducer newKafkaProducer() {
        return initUserIntegralSender();
    }
}
