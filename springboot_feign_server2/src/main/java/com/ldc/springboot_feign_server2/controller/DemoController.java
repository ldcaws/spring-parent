package com.ldc.springboot_feign_server2.controller;

import com.ldc.springboot_feign_server2.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/16 20:44
 */
@RestController
@RequestMapping("/rest2")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/hello2")
    public Object hello2() {
        return "rest2-------------hello2";
    }

    @RequestMapping("/hello3")
    public Object hello3() {
        return demoService.hello3();
    }

    @RequestMapping("/hello4")
    public Object hello4() {
        return demoService.hello4();
    }
}
