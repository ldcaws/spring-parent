package com.ldc.springboot_rabbitmq.rabbitmq.consumer;

import com.ldc.springboot_rabbitmq.rabbitmq.service.impl.AbstractMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ldc
 * @description
 * @date 2020/9/8 16:07
 **/
public class TestConsumer extends AbstractMessageListener {

    Logger logger = LoggerFactory.getLogger(TestConsumer.class);

    @Override
    public boolean handleMessage(Object obj) {
        logger.info("rabbitmq消费者开始消费，消息内容：" +obj.toString());
        return true;
    }
}
