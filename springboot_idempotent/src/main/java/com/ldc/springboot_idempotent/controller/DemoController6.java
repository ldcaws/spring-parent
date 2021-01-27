package com.ldc.springboot_idempotent.controller;

import com.ldc.springboot_idempotent.common.IdempotentUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 幂等性工具校验
 * @author: ss
 * @time: 2020/10/13 10:55
 */
@RestController
public class DemoController6 {
    @RequestMapping("addUser6")
    public String addUser(String id) {
        //调用幂等性工具判断
        if (!IdempotentUtil.check(id, this.getClass())) {
            return "请求失败";
        }
        //业务代码
        System.out.println("添加用户ID：" + id);
        return "请求成功";
    }
}
