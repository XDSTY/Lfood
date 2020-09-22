package com.xdsty.userservice.serializer;

import com.xdsty.userclient.message.UserIntegralMessage;
import com.xdsty.userservice.BaseTest;
import org.junit.Test;
import java.util.Date;

/**
 * @author 张富华
 * @date 2020/8/7 9:51
 */
public class KafkaProtobufSerializerTest extends BaseTest {

    private KafkaProtobufSerializer serializer = new KafkaProtobufSerializer<>();
    private KafkaProtobufDeserializer deserializer = new KafkaProtobufDeserializer<>();


    @Test
    public void t() {

    }
}