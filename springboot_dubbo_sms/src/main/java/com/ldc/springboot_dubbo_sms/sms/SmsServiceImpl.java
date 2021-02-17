package com.ldc.springboot_dubbo_sms.sms;

import com.ldc.springboot_dubbo_api.sms.api.SmsService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: ldc
 * @time: 2021/2/17 20:32
 */
@DubboService
public class SmsServiceImpl implements SmsService {
    public Object send(String phone,String content) {
        System.out.println("发送短信：" + phone + ":" + content);
        return "短信发送成功";
    }
}
