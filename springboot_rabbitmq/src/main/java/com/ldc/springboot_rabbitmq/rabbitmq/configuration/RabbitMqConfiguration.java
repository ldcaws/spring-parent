package com.ldc.springboot_rabbitmq.rabbitmq.configuration;

import com.ldc.springboot_rabbitmq.rabbitmq.receiver.DefaultRabbitMqRegister;
import com.ldc.springboot_rabbitmq.rabbitmq.service.impl.RabbitMqServiceImpl;
import com.ldc.springboot_rabbitmq.rabbitmq.service.config.RabbitMqBinding;
import com.ldc.springboot_rabbitmq.rabbitmq.service.config.RabbitMqExchange;
import com.ldc.springboot_rabbitmq.rabbitmq.service.config.RabbitMqQueue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author ldc
 * @description
 * @date 2020/9/8 16:06
 **/
@Configuration
public class RabbitMqConfiguration {

    /**
     * 默认采用rabbitMQ
     */
    @Bean
    public RabbitMqServiceImpl rabbitMqService() {
        return new RabbitMqServiceImpl();
    }

    /**
     * 创建 默认的RabbitMQ 注册器
     */
    @Bean
    DefaultRabbitMqRegister rabbitMqRegister(ConnectionFactory connectionFactory) {
        return new DefaultRabbitMqRegister(connectionFactory);
    }

    /**
     * 使用 默认的注册器 注册 Exchange Queue Binding
     */
    @Bean
    Object registerRabbit(DefaultRabbitMqRegister rabbitMqRegister) throws IOException {
        rabbitMqRegister.registerExchange(RabbitMqExchange.values());
        rabbitMqRegister.registerQueue(RabbitMqQueue.values());
        rabbitMqRegister.registerBinding(RabbitMqBinding.values());
        return new Object();
    }
}