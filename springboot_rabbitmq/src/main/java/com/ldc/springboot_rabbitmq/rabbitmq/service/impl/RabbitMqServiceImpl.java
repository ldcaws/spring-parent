package com.ldc.springboot_rabbitmq.rabbitmq.service.impl;

import com.ldc.springboot_rabbitmq.rabbitmq.service.IRabbitMqExchange;
import com.ldc.springboot_rabbitmq.rabbitmq.service.IRabbitMqRouting;
import com.ldc.springboot_rabbitmq.rabbitmq.service.IRabbitMqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @author ldc
 * @description
 * @date 2020/9/8 15:48
 **/
public class RabbitMqServiceImpl implements IRabbitMqService, RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    private Logger logger = LoggerFactory.getLogger(RabbitMqServiceImpl.class);

    @Autowired
    protected RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void send(IRabbitMqExchange exchange, IRabbitMqRouting routing, Object msg) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchange.exchangeName(), routing.routingKey(), msg, correlationId);
    }

    @Override
    public void send(IRabbitMqExchange exchange, IRabbitMqRouting routing, Object msg, long delay) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        if (delay > 0) {
            MessagePostProcessor processor = (Message message) -> {
                message.getMessageProperties().setExpiration(delay + "");
                return message;
            };
            rabbitTemplate.convertAndSend(exchange.delayExchangeName(), routing.routingKey(), msg, processor, correlationId);
        } else {
            rabbitTemplate.convertAndSend(exchange.exchangeName(), routing.routingKey(), msg, correlationId);
        }
    }

    /**
     * 消息发送的确认回调
     */
    @Override
    public void confirm(CorrelationData correlationId, boolean ack, String cause) {
        if (ack) {
            logger.info("消息发送成功 correlationId: {} cause: {}", correlationId, cause);
        } else {
            logger.error("消息发送失败 correlationId: {} cause: {}", correlationId, cause);
        }
    }

    /**
     * 消息发送的返回回调
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyTest, String exchange, String routingKey) {
        logger.info("returnedMessage message: {} replyCode: {} exchange: {} routingKey: {}", message, replyCode, exchange, routingKey);
    }

}
