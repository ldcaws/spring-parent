package com.ldc.springboot_redis.controller;

import com.ldc.springboot_redis.publish.RedisPublisher;
import com.ldc.springboot_redis.service.DemoSkillService;
import com.ldc.springboot_redis.task.DemoSkillThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/9 15:49
 */
@RestController
public class DemoController {

    @Autowired
    private DemoSkillService demoSkillService;

    @Autowired
    private RedisPublisher redisPublisher;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/skill")
    public void skill() {
        for (int i = 0; i < 50; i++) {
            DemoSkillThread demoSkillThread = new DemoSkillThread(demoSkillService);
            demoSkillThread.start();
        }
    }

    @GetMapping("/redis/{key}")
    public String publishEvent(@PathVariable String key) {
        // 发布事件
        redisPublisher.publish(key);
        return "ok";
    }
}
