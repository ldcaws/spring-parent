package com.ldc.springboot_idempotent.common;

import org.apache.commons.collections4.map.LRUMap;

/**
 * @description:
 * @author: ss
 * @time: 2020/10/13 10:50
 */
public class IdempotentUtil {
    //根据LRU算法淘汰最近最少使用的数据的Map集合，最大容量100个
    private static LRUMap<String,Integer> lruMap = new LRUMap<>(100);

    /**
     * 幂等性判断
     */
    public static boolean check(String id, Object lockClass) {
        synchronized (lockClass) {
            //重复请求判断
            if (lruMap.containsKey(id)) {
                //重复请求
                System.out.println("请勿重复提交..." + id);
                return false;
            }
            //非重复请求，存储ID
            lruMap.put(id,1);
        }
        return true;
    }
}
