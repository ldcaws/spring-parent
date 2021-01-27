package com.ldc.springboot_idempotent.controller;

import org.apache.commons.collections4.map.LRUMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:LRUMap
 * @author: ss
 * @time: 2020/10/13 9:54
 */
@RestController
public class DemoController5 {
    //最大容量100个，根据LRU算法淘汰数据的Map集合
    private LRUMap<String, Integer> lruMap = new LRUMap<>(100);

    @RequestMapping("addUser5")
    public String addUser(String id) {
        if (lruMap.containsKey(id)) {
            //重复请求
            System.out.println("请勿重复提交..." + id);
            return "请求失败";
        }
        synchronized (this.getClass()) {
            if (lruMap.containsKey(id)) {
                //重复请求
                System.out.println("请勿重复提交..." + id);
                return "请求失败";
            }
            //存储ID
            lruMap.put(id, 1);
        }
        //业务代码
        System.out.println("添加用户ID：" + id);
        return "请求成功";
        //此方式的问题是HashMap是无限增长的，占用内存会越来越大；需要添加可以清除过期的数据
        //此方式解决了HashMap未清除过期数据的问题,通过LRU算法清除最不常使用的数据
    }
}
