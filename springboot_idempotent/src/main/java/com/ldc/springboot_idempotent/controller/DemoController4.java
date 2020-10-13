package com.ldc.springboot_idempotent.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @description:固定大小数组+双重检测锁DCL
 * @author: ss
 * @time: 2020/10/13 9:54
 */
@RestController
public class DemoController4 {
    //ID集合，大小为100
    private static String[] cache = new String[100];
    //请求计数器
    private static Integer counter = 0;

    @RequestMapping("addUser4")
    public String addUser(String id) {
        if (Arrays.asList(cache).contains(id)) {
            //重复请求
            System.out.println("请勿重复提交..." + id);
            return "请求失败";
        }
        synchronized (this.getClass()) {
            //重复请求判断
            if (Arrays.asList(cache).contains(id)) {
                //重复请求
                System.out.println("请勿重复提交..." + id);
                return "请求失败";
            }
            //记录ID
            if (counter >= cache.length) {
                counter = 0;
            }
            cache[counter] = id;
            //下标后移一位
            counter++;
        }
        //业务代码
        System.out.println("添加用户ID：" + id);
        return "请求成功";
        //此方式的问题是HashMap是无限增长的，占用内存会越来越大；需要添加可以清除过期的数据
        //此方式解决了HashMap未清除过期数据的问题,添加了双重检测机制提升代码执行效率，注意DCL使用重复提交频繁的业务场景，相反并不适用
    }
}
