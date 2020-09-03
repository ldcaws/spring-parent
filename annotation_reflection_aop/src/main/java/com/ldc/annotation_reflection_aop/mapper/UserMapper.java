package com.ldc.annotation_reflection_aop.mapper;

import com.ldc.annotation_reflection_aop.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @description:
 * @author: ss
 * @time: 2020/9/2 22:04
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM s_user WHERE id = #{customId}")
    User findUserById(@Param("customId") int customId);

}
