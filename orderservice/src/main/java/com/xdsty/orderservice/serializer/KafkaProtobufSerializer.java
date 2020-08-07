package com.xdsty.orderservice.serializer;

import basecommon.serializer.ProtobufSerializer;
import org.apache.kafka.common.serialization.Serializer;

public class KafkaProtobufSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(String topic, T data) {
        return ProtobufSerializer.serializeToByteArray(data);
    }


}
