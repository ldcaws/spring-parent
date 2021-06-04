package com.ldc.springboot_netty_socketio.service;

/**
 * @description:
 * @author: ldc
 * @time: 2021/6/4 9:53
 */
public interface SocketIOService {

    // 启动服务
    void start() throws Exception;

    // 停止服务
    void stop();

    // 推送信息
    void pushMessageToRoom(String event, String jsonString);

}