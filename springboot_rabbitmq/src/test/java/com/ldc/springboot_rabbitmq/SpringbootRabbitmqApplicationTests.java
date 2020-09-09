package com.ldc.springboot_rabbitmq;

import com.ldc.springboot_rabbitmq.rabbitmq.service.config.RabbitMqExchange;
import com.ldc.springboot_rabbitmq.rabbitmq.service.config.RabbitMqRouting;
import com.ldc.springboot_rabbitmq.rabbitmq.service.IRabbitMqService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringbootRabbitmqApplicationTests {

    Logger logger = LoggerFactory.getLogger(SpringbootRabbitmqApplicationTests.class);

    @Autowired
    private IRabbitMqService iRabbitMqService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSendMq(){
        logger.info("生产者发送消息到mq");
        iRabbitMqService.send(RabbitMqExchange.MQ_EXCHANGE_TEST, RabbitMqRouting.MQ_ROUTING_TEST,"测试发送消息");
    }

    @Test
    public void testSendDelayMq(){
        logger.info("生产者发送延迟消息到mq");
        iRabbitMqService.send(RabbitMqExchange.MQ_EXCHANGE_TEST, RabbitMqRouting.MQ_ROUTING_TEST,"测试发送延时消息60s",60*1000);
    }

    public static final int THREAD_NUM = 500;

//    @Resource
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private AmqpTemplate amqpTemplate;

//    @Test
//    public void send() {
//        CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);
//        for (int i = 0; i < THREAD_NUM; i++) {//多线程，但不是并发执行；通过倒计数模拟多线程并发执行
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        countDownLatch.countDown();//倒计算 -1
//                        countDownLatch.await();//阻塞等待
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    //restTemplate.getForEntity("url",String.class);
//                    String message = "1";
//                    amqpTemplate.convertAndSend("topicExchange","topic.ticket.routeke.send",message);
//                }
//            }).start();
//        }
//    }

}
