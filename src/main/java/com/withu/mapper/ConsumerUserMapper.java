package com.withu.mapper;

import com.withu.pojo.entity.Address;
import com.withu.pojo.entity.ConsumerUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.withu.pojo.entity.Elder;
import com.withu.pojo.entity.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 普通消费者用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2025-07-20
 */
public interface ConsumerUserMapper extends BaseMapper<ConsumerUser> {


    Boolean saveElder(Elder elder);

    @Select("select * from consumer_user where id = #{id}")
    ConsumerUser selectConsumerUserById(Long id);

    @Select("select * from elder where consumer_id = #{id}")
    List<Elder> findAllEldersById(Long id);

    void insertOrder(Order order);

    @Select("insert into address (elder_id, type, name, phone, province, city, district, detailed_address, consumer_id) VALUES (#{elderId}, #{type}, #{name}, #{phone}, #{province}, #{city}, #{district}, #{detailAddress}, #{consumerId})")
    //@Update("update address set name = #{name}, phone = #{phone}, province = #{province}, city = #{city}, district = #{district}, detailed_address = #{detailAddress}, consumer_id = #{consumerId} where id = #{id}")
    boolean insertAddress(Address address);

    @Select("select * from address where consumer_id = #{id}")
    List<Address> findAllAddressById(Long id);

    @Select("select * from address where id = #{id}")
    Address findAddressById(Long id);

    @Delete("delete from address where id = #{id}")
    boolean deleteAddressById(Long id);

    @Select("select * from elder where id = #{id}")
    Elder findElderById(Long id);

    @Delete("delete from elder where id = #{id}")
    boolean deleteElderById(Long id);

}
