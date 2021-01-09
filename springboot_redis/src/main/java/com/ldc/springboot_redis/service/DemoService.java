package com.ldc.springboot_redis.service;

import com.ldc.springboot_redis.redis.RedisLock;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/9 15:59
 */
public class DemoService {

    private static JedisPool jedisPool = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(200);
        // 设置最大空闲数
        config.setMaxIdle(8);
        // 设置最大等待时间
        config.setMaxWaitMillis(1000 * 100);
        jedisPool = new JedisPool(config, "127.0.0.1", 6379, 3000);
    }

    RedisLock redisLock = new RedisLock(jedisPool);

    int n = 500;

    public void seckill() {
        // 返回锁的value值，供释放锁时候进行判断
        String indentifier = null;
        indentifier = redisLock.lockWithTimeout("resource", 5000, 1000);
        System.out.println(Thread.currentThread().getName() + "获得了锁");
        if (indentifier != null) {
            // 获取到锁后才可以进行操作（只有占用厕所，才可以如厕），具体能够有几个操作需要看acquireTimeout、expire
            System.out.println(--n);
            redisLock.releaseLock("resource", indentifier);
        }
    }
}