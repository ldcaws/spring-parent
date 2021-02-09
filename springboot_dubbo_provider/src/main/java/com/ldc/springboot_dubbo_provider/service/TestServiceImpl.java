package com.ldc.springboot_dubbo_provider.service;

import com.ldc.springboot_dubbo_interface.service.TestService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: ldc
 * @time: 2021/2/9 22:20
 */
@DubboService(version = "1.0.0",interfaceClass = TestService.class)
public class TestServiceImpl implements TestService {
    @Override
    public void ins() {
        System.out.println("insert");
    }

    @Override
    public void del() {
        System.out.println("delete");
    }

    @Override
    public void upd() {
        System.out.println("update");
    }

    @Override
    public void sel() {
        System.out.println("select");
    }
}