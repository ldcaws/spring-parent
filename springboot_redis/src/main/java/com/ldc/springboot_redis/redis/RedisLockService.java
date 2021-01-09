package com.ldc.springboot_redis.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/9 17:32
 */
@Component
public class RedisLockService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 加锁
     *
     * @param keyName        锁的key
     * @param acquireTimeout 获取超时时间
     * @param expire         锁的过期时间
     * @return 锁标识
     */
    public String lockWithTimeout(String keyName, long acquireTimeout, long expire) {
        // 随机生成一个value
        String value = UUID.randomUUID().toString();
        // 锁名，即key值
        String lockKey = "lock:" + keyName;
        // 锁的超时时间，上锁后超过此时间则自动释放锁
        int lockExpire = (int) (expire / 1000);
        // 获取锁的超时时间，超过这个时间则放弃获取锁
        long end = System.currentTimeMillis() + acquireTimeout;
        while (System.currentTimeMillis() < end) {
            Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(lockKey, value, lockExpire, TimeUnit.SECONDS);
            if (ifAbsent) {
                return value;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param keyName    锁的key
     * @param identifier 释放锁的标识
     * @return
     */
    public boolean releaseLock(String keyName, String identifier) {
        String lockKey = "lock:" + keyName;
        boolean retFlag = false;
        Object value = redisTemplate.opsForValue().get(lockKey);
        if (identifier.equals(value)) {
            retFlag = redisTemplate.delete(keyName);
        }
        return retFlag;
    }
}