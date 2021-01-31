package com.ldc.springboot_kafka.kafka;

import com.alibaba.fastjson.JSON;
import com.ldc.springboot_kafka.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/30 20:13
 */
@Component
public class KafkaProducer {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void send(Message message) {
        logger.info("send message=" + JSON.toJSONString(message));
        kafkaTemplate.send("topic1", JSON.toJSONString(message));
    }
}
