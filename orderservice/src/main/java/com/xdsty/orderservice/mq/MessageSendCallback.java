package com.xdsty.orderservice.mq;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @author 张富华
 * @date 2020/8/7 16:28
 */
public class MessageSendCallback implements Callback {

    private ProducerRecord record;

    public MessageSendCallback(ProducerRecord record) {
        this.record = record;
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception e) {

    }
}
