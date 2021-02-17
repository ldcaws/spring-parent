package com.ldc.springboot_dubbo_order.controller;

import com.ldc.springboot_dubbo_api.order.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ldc
 * @time: 2021/2/17 22:10
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public Object create() {
        orderService.create("买了一部手机");
        return "买了一部手机";
    }
}
