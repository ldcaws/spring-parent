package com.ldc.springboot_dubbo_order.order;

import com.ldc.springboot_dubbo_api.order.api.OrderService;
import com.ldc.springboot_dubbo_api.sms.api.SmsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: ldc
 * @time: 2021/2/17 20:56
 */
@DubboService
public class OrderServiceImpl implements OrderService {
    @DubboReference
    private SmsService smsService;

    public void create(String orderContent) {
        System.out.println("订单创建成功：" + orderContent);
        Object smsResult = smsService.send("10086","你的短信来了");
        System.out.println("smsService调用结果：" + smsResult);
    }

}
