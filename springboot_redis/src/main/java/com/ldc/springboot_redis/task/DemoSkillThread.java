package com.ldc.springboot_redis.task;

import com.ldc.springboot_redis.service.DemoSkillService;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/9 17:47
 */
public class DemoSkillThread extends Thread {

    private DemoSkillService demoSkillService;

    public DemoSkillThread(DemoSkillService demoSkillService) {
        this.demoSkillService = demoSkillService;
    }

    @Override
    public void run() {
        demoSkillService.seckill();
    }

}