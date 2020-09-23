package com.xdsty.orderservice.mq.orderback;

import com.xdsty.orderbackclient.serializer.OrderBackMessageProto;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

@Component
@EnableConfigurationProperties(value = OrderBackMqConfig.class)
public class OrderBackMqConsumer {

    private static OrderBackMqConfig orderBackMqConfig;

    @Autowired
    public void setOrderBackMqConfig(OrderBackMqConfig orderBackMqConfig) {
        OrderBackMqConsumer.orderBackMqConfig = orderBackMqConfig;
    }

    private static KafkaConsumer<String, OrderBackMessageProto.OrderRollBackMessage> initConsumer() {
        KafkaConsumer<String, OrderBackMessageProto.OrderRollBackMessage> consumer;
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", orderBackMqConfig.getBootstrapServers());
        properties.setProperty("group.id", orderBackMqConfig.getGroupId());
        properties.setProperty("key.deserializer", orderBackMqConfig.getKeyDeserializer());
        properties.setProperty("value.deserializer", orderBackMqConfig.getValueDeserializer());
        properties.setProperty("enable.auto.commit", orderBackMqConfig.getEnableAutoCommit());
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(orderBackMqConfig.getTopic()));
        return consumer;
    }

    public static KafkaConsumer<String, OrderBackMessageProto.OrderRollBackMessage> newConsumer() {
        return initConsumer();
    }
}
