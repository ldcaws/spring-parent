package com.ldc.annotation_reflection_aop;

import com.ldc.annotation_reflection_aop.model.Order;
import com.ldc.annotation_reflection_aop.service.OrderService;
import com.ldc.annotation_reflection_aop.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AnnotationReflectionAopApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        System.out.println(userService.findUserById(1).getName());
    }

    @Test
    void test() {
        List<Order> orderList = orderService.findOrderList(1, 10);
        System.out.println(orderList.get(1).getCustomName());
    }

}
