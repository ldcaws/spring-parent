package com.ldc.springboot_rabbitmq.rabbitmq.service.config;

import com.ldc.springboot_rabbitmq.rabbitmq.service.IRabbitMqQueue;

/**
 * RabbitMq queue(队列)的定义
 * */
public enum RabbitMqQueue implements IRabbitMqQueue {
    MQ_QUEUE_TEST("mq.queue.test");

    private String queueName;

    RabbitMqQueue(String queueName){
        this.queueName = queueName;
    }

    @Override
    public String queueName() {
        return this.queueName;
    }
}
