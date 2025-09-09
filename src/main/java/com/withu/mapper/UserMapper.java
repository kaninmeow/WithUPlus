package com.withu.mapper;

import com.withu.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询志愿者用户
     * @param username
     * @return
     */
    @Select("select * from consumer_user where username = #{username}")
    User getConsumerByUsername(String username);

    @Select("select * from withuplus.volunteer_users where username = #{username}")
    User getvolunteerByUsername(String username);

    @Update("update consumer_user set password = #{password} where username = #{username}")
    void updatePasswordById(User user1);

    @Select("select * from admin_user where username = #{username}")
    User getAdminByUsername(String username);
}
