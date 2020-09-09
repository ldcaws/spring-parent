package com.ldc.springboot_rabbitmq.rabbitmq.service;

/**
 * 消息监听(消费)者
 */
public interface IRabbitMqListener {

    // 处理rabbitMq的消息
    boolean handleMessage(Object object);

}
