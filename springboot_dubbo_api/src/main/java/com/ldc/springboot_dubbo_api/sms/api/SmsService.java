package com.ldc.springboot_dubbo_api.sms.api;

public interface SmsService {
    Object send(String phone,String content);
}
