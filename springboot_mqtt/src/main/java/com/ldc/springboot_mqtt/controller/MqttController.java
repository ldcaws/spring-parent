package com.ldc.springboot_mqtt.controller;

import com.ldc.springboot_mqtt.mqtt.MqttMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: ldc
 * @time: 2021/2/7 22:51
 */
@RestController
public class MqttController {

    /**
     * 注入发送MQTT的Bean
     */
    @Resource
    private MqttMessageProducer mqttMessageProducer;

    /**
     * 发送MQTT消息
     * @param message 消息内容
     * @return 返回
     */
    @GetMapping(value = "/mqtt", produces ="text/html")
    public ResponseEntity<String> sendMqtt(@RequestParam(value = "msg") String message) {
        mqttMessageProducer.sendToMqtt(message);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
