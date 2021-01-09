package com.ldc.springboot_redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/9 21:38
 */
@Service
public class OderServiceImpl implements OrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String orderId() {
        String key = "163:study:order:id";
        String prefix = getPrefix();
        Long id = redisTemplate.opsForValue().increment(key);
        System.out.println("prefix:" + prefix);
        System.out.println("id:" + prefix + id);
        return prefix + id;
    }

    private String getPrefix() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfYear();
        // 处理
        return String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
    }
}
