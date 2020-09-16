package com.ldc.springboot_feign_server2.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(name = "spring-eureka-feign-server1")
public interface Api {

    @RequestMapping(value = "/rest1/hello1")
    public String getHello1();

}
