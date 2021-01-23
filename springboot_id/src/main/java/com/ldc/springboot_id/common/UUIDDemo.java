package com.ldc.springboot_id.common;

import java.util.UUID;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/23 20:21
 */
public class UUIDDemo {

    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(uuid);
    }

}
