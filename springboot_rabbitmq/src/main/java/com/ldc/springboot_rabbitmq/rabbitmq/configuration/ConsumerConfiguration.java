package com.ldc.springboot_rabbitmq.rabbitmq.configuration;

import com.ldc.springboot_rabbitmq.rabbitmq.consumer.TestConsumer;
import com.ldc.springboot_rabbitmq.rabbitmq.receiver.DefaultRabbitMqRegister;
import com.ldc.springboot_rabbitmq.rabbitmq.service.config.RabbitMqQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ldc
 * @description
 * @date 2020/9/8 16:05
 **/
@Configuration
public class ConsumerConfiguration {
    /**
     * 使用 默认的注册器 注册 消息队列的消费者(消息处理器)
     */
    @Bean
    Object listenerRabbit(DefaultRabbitMqRegister rabbitMqRegister) {
        rabbitMqRegister.listenerQueue(testConsumer(), RabbitMqQueue.MQ_QUEUE_TEST);
        return null;
    }
    @Bean
    TestConsumer testConsumer(){
        return new TestConsumer();
    }


}
