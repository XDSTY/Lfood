package com.xdsty.userservice.serializer;

import basecommon.serializer.ProtobufSerializer;
import org.apache.kafka.common.serialization.Deserializer;

public class KafkaProtobufDeserializer<T> implements Deserializer {

    @Override
    public T deserialize(String topic, byte[] bytes) {
        return ProtobufSerializer.deserializeBytes(bytes);
    }
}
