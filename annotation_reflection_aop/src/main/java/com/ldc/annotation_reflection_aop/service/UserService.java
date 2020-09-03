package com.ldc.annotation_reflection_aop.service;

import com.ldc.annotation_reflection_aop.mapper.UserMapper;
import com.ldc.annotation_reflection_aop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/3 10:10
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

}
