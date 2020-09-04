package com.ldc.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/4 21:40
 */
@ConfigurationProperties(prefix = "time.log")
public class TimeLogProperties {
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
