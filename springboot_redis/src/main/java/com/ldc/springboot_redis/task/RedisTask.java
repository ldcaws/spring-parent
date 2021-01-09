package com.ldc.springboot_redis.task;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/9 20:46
 */
@Component
public class RedisTask extends KeyExpirationEventMessageListener {
    
    public RedisTask(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 接受事件后回调
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("key:" + key + ",chanel:" + channel);
        // 根据key进行处理
    }
}
