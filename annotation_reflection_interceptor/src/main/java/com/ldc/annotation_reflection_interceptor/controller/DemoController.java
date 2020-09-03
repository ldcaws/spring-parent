package com.ldc.annotation_reflection_interceptor.controller;

import com.ldc.annotation_reflection_interceptor.annotation.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/3 15:07
 */
@RestController
public class DemoController {

    @RequestMapping("/hello")
    @Auth
    public Object hello() {
        return "已通过权限校验，顺利进入该方法";
    }

    @RequestMapping("/hello2")
    @Auth(key = false)
    public Object hello2() {
        return "已通过权限校验，顺利进入该方法";
    }

    @RequestMapping("/hello3")
    public Object hello3() {
        return "已通过权限校验，顺利进入该方法";
    }

}
