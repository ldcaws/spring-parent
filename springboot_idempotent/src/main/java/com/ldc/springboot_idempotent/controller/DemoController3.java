package com.ldc.springboot_idempotent.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:HashMap版防止重复提交+双重检测锁DCL
 * @author: ss
 * @time: 2020/10/13 9:54
 */
@RestController
public class DemoController3 {
    //缓存ID集合
    private Map<String,Integer> map = new HashMap<>();

    @RequestMapping("addUser3")
    public String addUser(String id) {
        //重复请求判断
        if (map.containsKey(id)) {
            //重复请求
            System.out.println("请勿重复提交..." + id);
            return "请求失败";
        }
        synchronized (this.getClass()) {
            //重复请求判断
            if (map.containsKey(id)) {
                //重复请求
                System.out.println("请勿重复提交..." + id);
                return "请求失败";
            }
            //存储ID
            map.put(id,1);
        }
        //业务代码
        System.out.println("添加用户ID：" + id);
        return "请求成功";
        //此方式的问题是HashMap是无限增长的，占用内存会越来越大；需要添加可以清除过期的数据
        //添加了双重检测机制提升代码执行效率，注意DCL使用重复提交频繁的业务场景，相反并不适用
    }
}
