package com.ldc.springboot_netty_socketio.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.ldc.springboot_netty_socketio.service.SocketIOService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: ldc
 * @time: 2021/6/4 11:03
 */
@RestController
public class DemoHandler {

    @Resource(name = "socketIOService")
    private SocketIOService socketIOService;

    @RequestMapping("/hello")
    public String hello() {
        socketIOService.pushMessageToRoom("push_event", "JSON.toJSONString(map)");
        return "hello";
    }

    @OnEvent("test_event")
    public void helloEvent(SocketIOClient client, String info) {
        socketIOService.pushMessageToRoom("push_event", "JSON.toJSONString(map)");
    }

}
