package com.ldc.springboot_redis.task;

import com.ldc.springboot_redis.service.DemoService;

/**
 * @description: 模拟线程进行秒杀服务，在指定时间acquireTimeout内进行秒杀活动，每个抢到订单的需要在指定时间expire内完成支付
 * @author: ldc
 * @time: 2021/1/9 16:09
 */
public class DemoThread extends Thread {

    private DemoService demoService;

    public DemoThread(DemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public void run() {
        demoService.seckill();
    }

    public static void main(String[] args) {
        DemoService demoService = new DemoService();
        for (int i = 0; i < 50; i++) {
            DemoThread demoThread = new DemoThread(demoService);
            demoThread.start();
        }
    }
}