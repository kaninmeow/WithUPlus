package com.withu.mapper;

import com.withu.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询志愿者用户
     * @param username
     * @return
     */
    @Select("select * from consumer_user where username = #{username}")
    User getConsumerByUsername(String username);
}
