package com.ldc.springboot_rabbitmq.rabbitmq.service;

public interface IRabbitMqService {

    // 给Rabbitmq发送消息
    void send(IRabbitMqExchange exchange,IRabbitMqRouting routing,Object msg);

    // 给Rabbitmq发送延迟消息
    void send(IRabbitMqExchange exchange,IRabbitMqRouting routing,Object msg,long delay);

}
