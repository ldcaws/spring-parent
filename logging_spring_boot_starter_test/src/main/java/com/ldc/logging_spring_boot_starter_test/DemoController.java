package com.ldc.logging_spring_boot_starter_test;

import com.ldc.logging.TimeLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/4 22:01
 */
@RestController
public class DemoController {

    @TimeLog
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

}
