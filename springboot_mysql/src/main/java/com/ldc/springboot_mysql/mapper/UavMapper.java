package com.ldc.springboot_mysql.mapper;

import com.ldc.springboot_mysql.model.Uav;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/13 21:19
 */
public interface UavMapper {

    Uav getUavById(String uavId);

}