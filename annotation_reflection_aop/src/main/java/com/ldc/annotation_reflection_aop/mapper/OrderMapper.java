package com.ldc.annotation_reflection_aop.mapper;

import com.ldc.annotation_reflection_aop.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/2 22:04
 */
@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM b_order")
    @Results(id = "orderList",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "customId",column = "custom_id"),
            @Result(property = "customName",column = "custom_name"),
    })
    List<Order> findOrderList();

}
