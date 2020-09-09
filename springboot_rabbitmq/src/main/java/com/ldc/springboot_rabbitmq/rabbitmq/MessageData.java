package com.ldc.springboot_rabbitmq.rabbitmq;

/**
 * @author ldc
 * @description
 * @date 2020/9/8 16:09
 **/

import com.ldc.springboot_rabbitmq.rabbitmq.service.IRabbitMqExchange;
import com.ldc.springboot_rabbitmq.rabbitmq.service.IRabbitMqRouting;

/**
 * 缓存消息对象
 */
public class MessageData {
    /**
     * RabbitMq Exchange
     */
    private IRabbitMqExchange exchange;
    /**
     * RabbitMq routing
     */
    private IRabbitMqRouting routing;
    /**
     * 消息体
     */
    private Object msg;
    /**
     * 延时时间
     */
    private long delay;
    /**
     * 创建时间
     */
    private long now;
    /**
     * 重试次数
     */
    private int retryCount;

    public MessageData() {

    }

    public MessageData(IRabbitMqExchange exchange, IRabbitMqRouting routing, Object msg) {
        this.exchange = exchange;
        this.routing = routing;
        this.msg = msg;
        this.delay = 0;
        this.now = System.currentTimeMillis();
        this.retryCount = 0;
    }

    public MessageData(IRabbitMqExchange exchange, IRabbitMqRouting routing, Object msg, long delay) {
        this.exchange = exchange;
        this.routing = routing;
        this.msg = msg;
        this.delay = delay;
        this.now = System.currentTimeMillis();
        this.retryCount = 0;
    }

    public void addRetryCount() {
        this.retryCount ++;
    }

    public IRabbitMqExchange getExchange() {
        return exchange;
    }

    public void setExchange(IRabbitMqExchange exchange) {
        this.exchange = exchange;
    }

    public IRabbitMqRouting getRouting() {
        return routing;
    }

    public void setRouting(IRabbitMqRouting routing) {
        this.routing = routing;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}
