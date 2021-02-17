package com.ldc.springboot_dubbo_order;

import com.ldc.springboot_dubbo_api.order.api.OrderService;
import com.ldc.springboot_dubbo_order.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootDubboOrderApplicationTests {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Test
    void contextLoads() {
        System.out.println("------------");
        orderServiceImpl.create("买了一部手机");
    }

}
