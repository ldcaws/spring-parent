package com.ldc.springboot_netty_socketio.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: ldc
 * @time: 2021/6/4 9:57
 */
@Service(value = "socketIOService")
public class SocketIOServiceImpl implements SocketIOService {

    // 用来存已连接的客户端
    private static List<SocketIOClient> clientList = Collections.synchronizedList(new ArrayList());

    @Autowired
    private SocketIOServer socketIOServer;

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     *
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     *
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception {
        stop();
    }

    @Override
    public void start() throws Exception {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            if (loginUserNum != null) {
                System.out.println(loginUserNum);
                System.out.println("建立连接:  " + client.getSessionId());
                System.out.println("RemoteAddress:  " + client.getRemoteAddress());
                System.out.println("Transport:  " + client.getTransport());
                clientList.add(client);
            } else {
                System.out.println("建立连接:  " + client.getSessionId());
                System.out.println("RemoteAddress:  " + client.getRemoteAddress());
                System.out.println("Transport:  " + client.getTransport());
                clientList.add(client);
            }
        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            if (loginUserNum != null) {
                clientList.remove(client);
                System.out.println("断开连接： " + loginUserNum);
                System.out.println("断开连接： " + client.getSessionId());
                client.disconnect();
            } else {
                System.out.println("断开连接： " + client.getSessionId());
                clientList.remove(client);
                client.disconnect();
            }
        });

        // 处理自定义的事件，与连接监听类似
        socketIOServer.addEventListener("test_event", Object.class, (client, data, ackSender) -> {
            client.getHandshakeData();
            System.out.println( " 客户端：" + data);

        });

        socketIOServer.start();
    }



    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    @Override
    public void pushMessageToRoom(String event, String jsonString) {
        for (SocketIOClient client : clientList) {
            client.sendEvent(event, jsonString);
        }
    }

    /**
     * 此方法为获取client连接中的参数，可根据需求更改
     * @param client
     * @return
     */
    private String getParamsByClient(SocketIOClient client) {
        // 从请求的连接中拿出参数（这里的loginUserNum必须是唯一标识）
        Map<String, List<String>> params = client.getHandshakeData ().getUrlParams();
        List<String> list = params.get("loginUserNum");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}