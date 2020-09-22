package com.xdsty.userservice.mq;

import com.xdsty.userclient.serializer.UserIntegralMessageProto;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

@Component
@EnableConfigurationProperties(value = KafkaConfig.class)
public class MqConsumer {

    private static KafkaConfig kafkaConfig;

    @Autowired
    public void setKafkaConfig(KafkaConfig kafkaConfig) {
        MqConsumer.kafkaConfig = kafkaConfig;
    }

    private static KafkaConsumer<String, UserIntegralMessageProto.UserIntegralMessage> initConsumer() {
        KafkaConsumer<String, UserIntegralMessageProto.UserIntegralMessage> consumer;
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaConfig.getBootstrapServers());
        properties.setProperty("group.id", kafkaConfig.getGroupId());
        properties.setProperty("key.deserializer", kafkaConfig.getKeyDeserializer());
        properties.setProperty("value.deserializer", kafkaConfig.getValueDeserializer());
        properties.setProperty("enable.auto.commit", kafkaConfig.getEnableAutoCommit());
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(kafkaConfig.getTopic()));
        return consumer;
    }

    public static KafkaConsumer<String, UserIntegralMessageProto.UserIntegralMessage> newConsumer() {
        return initConsumer();
    }
}
