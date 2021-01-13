package com.ldc.springboot_mysql.controller;

import com.ldc.springboot_mysql.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/13 21:29
 */
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/get")
    public Object get() {
        return demoService.getUser();
    }

    @GetMapping("/readSlave")
    public Object readSlave() {
        return demoService.readUser();
    }

    @GetMapping("/writeMaster")
    public Object writeMaster() {
        return demoService.writeUser();
    }

}
