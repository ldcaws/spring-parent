package com.ldc.springboot_redis;

import com.ldc.springboot_redis.service.OrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootRedisApplication.class)
class SpringbootRedisApplicationTests {

    @Before
    public void start() {

    }

    @After
    public void end() {

    }

    private static final int num = 100;

    private CountDownLatch countDownLatch = new CountDownLatch(num);

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        for (int i = 0; i <num; i++) {
            Thread thread = new Thread(()-> {
                try {
                    countDownLatch.await();
                    orderService.orderId();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            countDownLatch.countDown();
        }
    }

}
