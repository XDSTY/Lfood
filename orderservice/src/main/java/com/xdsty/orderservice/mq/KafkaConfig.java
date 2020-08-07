package com.xdsty.orderservice.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 张富华
 * @date 2020/8/7 14:11
 */
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {

    private String bootstrapServers;

    private String keySerializer;

    private String valueSerializer;

    private String acks;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }
}
