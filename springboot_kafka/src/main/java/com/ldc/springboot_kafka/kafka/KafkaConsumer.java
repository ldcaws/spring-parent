package com.ldc.springboot_kafka.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/30 20:23
 */
@Component
public class KafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private ConsumerFactory consumerFactory;

    @Bean
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略
        factory.setRecordFilterStrategy(consumerRecord -> {
            System.out.println("进入过滤...");
            return false;
            //if (Integer.parseInt(consumerRecord.value().toString()) % 2 == 0) {
            //    return false;
            //}
            //返回true消息则被过滤
            //return true;
        });
        return factory;
    }

    //@KafkaListener(topics = {"topic1","topic2"})
    @KafkaListener(topics = {"topic1"},containerFactory = "filterContainerFactory")
    public void listen(ConsumerRecord<?,?> record) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object result = message.get();
            logger.info("record=" + record);
            logger.info("receive message=" + result);
            // listen topic and receive message and share message to browser
        }
    }
}
