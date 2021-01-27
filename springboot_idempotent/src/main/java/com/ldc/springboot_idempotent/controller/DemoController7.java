package com.ldc.springboot_idempotent.controller;

import com.ldc.springboot_idempotent.annotation.Idempotent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 注解校验
 * @author: ss
 * @time: 2020/10/13 10:55
 */
@RestController
public class DemoController7 {

    //@Idempotent
    @RequestMapping("addUser7")
    public String addUser(String id) {
        //业务代码
        System.out.println("添加用户ID：" + id);
        return "请求成功";
    }

    @Idempotent(id = "#id",lockClass = DemoController7.class)
    @RequestMapping("addUser8")
    public String addUser8(String id) {
        //业务代码
        System.out.println("添加用户ID：" + id);
        return "请求成功";
    }

}
