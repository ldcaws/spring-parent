package com.ldc.springboot_mysql.service;

import com.ldc.springboot_mysql.datasource.DataSourceAnnotation;
import com.ldc.springboot_mysql.datasource.DataSourceEnum;
import com.ldc.springboot_mysql.mapper.UavMapper;
import com.ldc.springboot_mysql.mapper.UserMapper;
import com.ldc.springboot_mysql.model.Uav;
import com.ldc.springboot_mysql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: ldc
 * @time: 2021/1/13 21:24
 */
@Service
public class DemoService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UavMapper uavMapper;

    public User getUser() {
        String userId = "1";
        User user = userMapper.getUserById(userId);
        return user;
    }

    /**
     * 读操作
     */
    @DataSourceAnnotation(DataSourceEnum.SLAVE)
    public User readUser() {
        String userId = "1";
        User user = userMapper.getUserById(userId);
        return user;
    }

    /**
     * 写操作
     */
    @DataSourceAnnotation(DataSourceEnum.MASTER)
    public Uav writeUser() {
        String uavId = "1";
        Uav uav = uavMapper.getUavById(uavId);
        return uav;
    }

}