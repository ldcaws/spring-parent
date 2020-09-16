package com.ldc.springboot_feign_server2.service;

import com.ldc.springboot_feign_server2.api.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/16 20:51
 */
@Service
public class DemoService {

    @Autowired
    private Api api;

    public Object hello3() {
        return "service----------------hello3";
    }

    public Object hello4() {
        String result = api.getHello1();
        return "通过api调用服务1的接口获取数据：" + result;
    }

}
