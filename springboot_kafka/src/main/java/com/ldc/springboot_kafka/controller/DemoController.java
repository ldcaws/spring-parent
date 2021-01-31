package com.ldc.springboot_kafka.controller;

import com.ldc.springboot_kafka.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/30 20:06
 */
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/hello")
    public Object hello() {
        return "hello";
    }

    @GetMapping("/send")
    public Object send() {
        demoService.send("hello");
        return "hello";
    }
}
