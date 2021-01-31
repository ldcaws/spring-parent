package com.ldc.springboot_kafka.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/30 20:09
 */
public class Message implements Serializable  {

    private static final long serialVersionUID = 1L;

    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
