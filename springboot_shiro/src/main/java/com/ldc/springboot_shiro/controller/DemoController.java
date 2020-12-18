package com.ldc.springboot_shiro.controller;

import com.ldc.springboot_shiro.common.ResponseData;
import com.ldc.springboot_shiro.model.User;
import com.ldc.springboot_shiro.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/17 21:45
 */
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/hello")
    public Object hello() {
        return "hello";
    }

    @GetMapping("/getUserByName")
    public Object getUserByName() {
        User user = demoService.getUserByName("caocao");
        return ResponseData.ok(user,"查询用户成功");
    }
}
