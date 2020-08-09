package com.xdsty.orderservice.mq;

import com.xdsty.orderservice.entity.enums.UserIntegralEnum;
import com.xdsty.orderservice.mapper.UserIntegralRecordMapper;
import com.xdsty.orderservice.util.ApplicationContextHolder;
import com.xdsty.userclient.message.UserIntegralMessage;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 张富华
 * @date 2020/8/7 16:28
 */
public class MessageSendCallback implements Callback {

    private static final Logger log = LoggerFactory.getLogger(MessageSendCallback.class);

    private ProducerRecord<String, UserIntegralMessage> record;

    private static int RETRY = 3;

    public MessageSendCallback(ProducerRecord<String, UserIntegralMessage> record) {
        this.record = record;
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception e) {
        log.error("发送成功回调");
        // 发送失败则打印日志并重试
        if(e != null) {
            log.error("发送失败{} ,{}", record, e);
            return;
        }
        // 发送成功则修改该记录状态
        UserIntegralRecordMapper mapper = (UserIntegralRecordMapper) ApplicationContextHolder.context.getBean("userIntegralRecordMapper");
        int retry = RETRY;
        while (retry-- > 0) {
            int count = mapper.updateRecord(record.value().getId(), UserIntegralEnum.SEND.status);
            if(count > 0) {
                break;
            }
            log.error("更新用户积分记录状态失败, retry；{}, record: {}", retry, record.value());
        }
    }
}
