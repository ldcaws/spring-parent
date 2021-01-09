package com.ldc.springboot_redis.service;

import com.ldc.springboot_redis.redis.RedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/9 17:43
 */
@Component
public class DemoSkillService {

    int n = 500;

    @Autowired
    private RedisLockService redisLockService;

    public void seckill() {
        // 返回锁的value值，供释放锁时候进行判断
        String indentifier = null;
        indentifier = redisLockService.lockWithTimeout("resource", 5000, 1000);
        if (indentifier != null) {
            // 获取到锁后才可以进行操作（只有占用厕所，才可以如厕），具体能够有几个操作需要看acquireTimeout、expire
            System.out.println(Thread.currentThread().getName() + "获得了锁");
            System.out.println(--n);
            redisLockService.releaseLock("resource", indentifier);
        }
    }
}
