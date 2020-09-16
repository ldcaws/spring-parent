package com.ldc.springboot_feign_server1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/16 20:44
 */
@RestController
@RequestMapping("/rest1")
public class DemoController {

    @RequestMapping("/hello1")
    public Object hello1() {
        return "rest1-------------hello1";
    }
}
