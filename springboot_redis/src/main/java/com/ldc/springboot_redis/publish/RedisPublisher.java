package com.ldc.springboot_redis.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/9 21:16
 */
@Component
public class RedisPublisher {

    @Autowired
    private RedisTemplate redisTemplate;

    public void publish(String key) {
        redisTemplate.opsForValue().set(key,new Random().nextInt(200),10, TimeUnit.SECONDS);
    }

    //@Scheduled(cron = "0/10 * * * * ?")
    public void ScheduledPublish() {
        redisTemplate.opsForValue().set("123",new Random().nextInt(200),10, TimeUnit.SECONDS);
    }
}
