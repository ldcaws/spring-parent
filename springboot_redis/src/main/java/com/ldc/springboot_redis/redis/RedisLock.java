package com.ldc.springboot_redis.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/9 15:55
 */
public class RedisLock {

    private final JedisPool jedisPool;

    public RedisLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

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
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            while (System.currentTimeMillis() < end) {
                Long setnx = jedis.setnx(lockKey, value);
                if (setnx == 1) {
                    jedis.expire(lockKey,lockExpire);
                    return value;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
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
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            while (true) {
                // 监视lock，准备开始事务
                jedis.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(jedis.get(lockKey))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                jedis.unwatch();
                break;
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return retFlag;
    }
}