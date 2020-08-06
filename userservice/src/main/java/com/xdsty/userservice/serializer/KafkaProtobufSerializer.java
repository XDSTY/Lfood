package com.xdsty.userservice.serializer;

import basecommon.serializer.ProtobufSerializer;
import org.apache.kafka.common.serialization.Serializer;

public class KafkaProtobufSerializer implements Serializer {

    @Override
    public byte[] serialize(String topic, Object data) {
        return ProtobufSerializer.serializeToByteArray(data);
    }
}
