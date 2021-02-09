package com.ldc.springboot_netty_client;

import com.ldc.springboot_netty_client.nettyClient.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootNettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNettyClientApplication.class, args);

        //启动netty客户端
        NettyClient nettyClient = new NettyClient();
        nettyClient.start();
    }

}
