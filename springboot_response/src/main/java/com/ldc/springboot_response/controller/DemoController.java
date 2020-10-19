package com.ldc.springboot_response.controller;

import com.ldc.springboot_response.model.User;
import com.ldc.springboot_response.response.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/3 8:18
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    //post请求
    @com.ldc.springboot_response.annotation.ResponseResult
    @RequestMapping(value = "/get1", method = RequestMethod.POST)
    public ResponseResult get1(@RequestBody User user) {
        Long id = user.getId();
        String name = user.getName();
        Integer age = user.getAge();
        Date time = user.getTime();
        return ResponseResult.success(user);
    }

    //post请求
    @com.ldc.springboot_response.annotation.ResponseResult
    @RequestMapping(value = "/get2", method = RequestMethod.POST)
    public Object get2(@RequestBody  User user) {
        Long id = user.getId();
        String name = user.getName();
        Integer age = user.getAge();
        Date time = user.getTime();
        return user;
    }

}
