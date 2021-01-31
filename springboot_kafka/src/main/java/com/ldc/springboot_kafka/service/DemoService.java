package com.ldc.springboot_kafka.service;

import com.ldc.springboot_kafka.kafka.KafkaProducer;
import com.ldc.springboot_kafka.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/30 20:29
 */
@Service
public class DemoService {

    @Autowired
    private KafkaProducer kafkaProducer;

    public void send(String param) {
        Message message = new Message();
        message.setId(1L);
        message.setMsg(param);
        message.setSendTime(new Date());
        kafkaProducer.send(message);
    }
}
