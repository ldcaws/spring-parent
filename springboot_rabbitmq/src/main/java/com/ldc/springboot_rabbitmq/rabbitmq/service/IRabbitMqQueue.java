package com.ldc.springboot_rabbitmq.rabbitmq.service;

import java.util.Map;

/**
 * 队列(Queue)
 */
public interface IRabbitMqQueue {

    // Queue(队列)名称
    String queueName();

    // 是否持久化
    default boolean durable() {return true;}

    // 排他性
    default boolean exclusive() {return false;}

    // 是否自动删除
    default boolean autoDelete() {return false;}

    // 其他属性设置
    default Map<String,Object> arguments() {return null;}

    // 默认的延时队列名称
    default String delayQueueName() {return "delay." + this.queueName();}

}
