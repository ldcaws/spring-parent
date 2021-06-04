package com.ldc.springboot_netty_socketio.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.ldc.springboot_netty_socketio.service.SocketIOService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: ldc
 * @time: 2021/6/4 15:58
 */
@Component
public class DemoHandler2 {

    @Resource(name = "socketIOService")
    private SocketIOService socketIOService;

    @OnEvent("test_event")
    public void helloEvent(SocketIOClient client, String info) {
        socketIOService.pushMessageToRoom("push_event", "JSON.toJSONString(map)");
    }

}
