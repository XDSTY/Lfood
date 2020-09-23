package com.xdsty.userservice.mq;

import com.xdsty.userclient.serializer.UserIntegralMessageProto;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

@Component
@EnableConfigurationProperties(value = IntegralMqConfig.class)
public class IntegralMqConsumer {

    private static IntegralMqConfig integralMqConfig;

    @Autowired
    public void setKafkaConfig(IntegralMqConfig integralMqConfig) {
        IntegralMqConsumer.integralMqConfig = integralMqConfig;
    }

    private static KafkaConsumer<String, UserIntegralMessageProto.UserIntegralMessage> initConsumer() {
        KafkaConsumer<String, UserIntegralMessageProto.UserIntegralMessage> consumer;
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", integralMqConfig.getBootstrapServers());
        properties.setProperty("group.id", integralMqConfig.getGroupId());
        properties.setProperty("key.deserializer", integralMqConfig.getKeyDeserializer());
        properties.setProperty("value.deserializer", integralMqConfig.getValueDeserializer());
        properties.setProperty("enable.auto.commit", integralMqConfig.getEnableAutoCommit());
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(integralMqConfig.getTopic()));
        return consumer;
    }

    public static KafkaConsumer<String, UserIntegralMessageProto.UserIntegralMessage> newConsumer() {
        return initConsumer();
    }
}
