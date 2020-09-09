package com.ldc.springboot_rabbitmq.rabbitmq.service.impl;

import com.ldc.springboot_rabbitmq.rabbitmq.service.IRabbitMqListener;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author ldc
 * @description
 * @date 2020/9/8 15:44
 **/
public abstract class AbstractMessageListener implements ChannelAwareMessageListener, IRabbitMqListener {

    private Logger logger = LoggerFactory.getLogger(AbstractMessageListener.class);

    private MessageConverter messageConverter = new Jackson2JsonMessageConverter();


    @Override
    public boolean handleMessage(Object object) {
        return false;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            Object obj = messageConverter.fromMessage(message);
            boolean handleResult = handleMessage(obj);
            if (handleResult) {
                channel.basicAck(tag, false);
            } else {
                logger.error("消息处理失败 message: {}", message);
                channel.basicNack(tag, false, false);
            }
        } catch (Exception e) {
            channel.basicNack(tag, false, false);
            logger.error("消息处理异常 message: " + message + " " + e.getMessage(), e);
        }
    }
}
