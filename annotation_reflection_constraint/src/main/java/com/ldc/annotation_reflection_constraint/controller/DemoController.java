package com.ldc.annotation_reflection_constraint.controller;

import com.ldc.annotation_reflection_constraint.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/3 15:07
 */
@RestController
public class DemoController {

    @RequestMapping("/hello")
    public Object hello(@Valid @RequestBody User user) {
        user.getSex();
        return "已通过字段校验，顺利进入该方法";
    }

    @RequestMapping("/hello2")
    public Object hello2(@RequestBody User user) {
        return "已通过字段校验，顺利进入该方法";
    }

}
