package com.xdsty.productservice.mq.orderback;

import com.xdsty.orderbackclient.serializer.OrderBackMessageProto;
import com.xdsty.productservice.entity.ProductModifyStorage;
import com.xdsty.productservice.service.StorageService;
import com.xdsty.productservice.util.ApplicationContextHolder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class OrderBackConsumerWorker implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(OrderBackConsumerWorker.class);

    @Override
    public void run() {
        log.error("消费订单回滚消息线程启动");
        KafkaConsumer<String, OrderBackMessageProto.OrderRollBackMessage> consumer = OrderBackMqConsumer.newConsumer();
        StorageService storageService = (StorageService) ApplicationContextHolder.getApplicationContext().getBean("storageService");
        Duration duration = Duration.ofMillis(1000);
        while (true) {
            try {
                // 获取数据
                ConsumerRecords<String, OrderBackMessageProto.OrderRollBackMessage> records = consumer.poll(duration);
                log.error("订阅订单回滚接收到消息个数{}", records.count());
                for (ConsumerRecord<String, OrderBackMessageProto.OrderRollBackMessage> record : records) {
                    OrderBackMessageProto.OrderRollBackMessage orderRollBackMessage = record.value();
                    // 归还库存  插入回滚表，防止重复的数据
                    List<ProductModifyStorage> storageList = orderRollBackMessage.getProductListList().stream().map(e -> {
                        ProductModifyStorage storage = new ProductModifyStorage();
                        storage.setProductId(e.getProductId());
                        storage.setStorage(e.getProductNum());
                        return storage;
                    }).collect(Collectors.toList());
                    storageService.updateProductStorage(storageList);
                }
                consumer.commitSync();
            }catch (Exception e) {
                log.error("处理回滚订单失败", e);
            }
        }
    }
}
