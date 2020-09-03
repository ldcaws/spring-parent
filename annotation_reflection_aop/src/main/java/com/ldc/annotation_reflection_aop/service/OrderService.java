package com.ldc.annotation_reflection_aop.service;

import com.ldc.annotation_reflection_aop.annotation.SetValue;
import com.ldc.annotation_reflection_aop.mapper.OrderMapper;
import com.ldc.annotation_reflection_aop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/2 22:07
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @SetValue
    public List<Order> findOrderList(int pageNum,int pageSize){
        return orderMapper.findOrderList();
    }

}
