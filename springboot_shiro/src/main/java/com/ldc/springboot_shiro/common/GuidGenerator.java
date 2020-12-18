package com.ldc.springboot_shiro.common;

import java.util.UUID;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/17 21:50
 */
public abstract class GuidGenerator {

    /**
     * private constructor
     */
    private GuidGenerator() {
    }

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

}