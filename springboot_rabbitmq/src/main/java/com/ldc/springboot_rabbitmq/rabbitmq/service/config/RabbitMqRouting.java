package com.ldc.springboot_rabbitmq.rabbitmq.service.config;

import com.ldc.springboot_rabbitmq.rabbitmq.service.IRabbitMqRouting;

/**
 * RabbitMq routing（路由定义）
 * */
public enum RabbitMqRouting implements IRabbitMqRouting {
    MQ_ROUTING_TEST("mq.routing.test");

    private String routingKey;

    RabbitMqRouting(String routingKey){
        this.routingKey = routingKey;
    }

    @Override
    public String routingKey() {
        return this.routingKey;
    }
}