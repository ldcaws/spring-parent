package com.ldc.springboot_feign_server2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringbootFeignServer2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFeignServer2Application.class, args);
    }

}
