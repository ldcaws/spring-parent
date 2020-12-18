package com.ldc.springboot_shiro.shiro.util;

import org.apache.shiro.util.ByteSource;

/**
 * @description:
 * @author: ldc
 * @time: 2020/12/18 0:30
 */
public class ByteSourceUtils {
    public static ByteSource bytes(byte[] bytes){
        return new SimpleByteSource(bytes);
    }
    public static ByteSource bytes(String arg0){
        return new SimpleByteSource(arg0.getBytes());
    }
}