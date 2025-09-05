package com.withu.mapper;

import com.withu.pojo.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {

    void saveOrder(Order order);

    @Update("update order set status = -1 where id = #{id}")
    void cancelOrder(Long id);

    @Select("select * from order where id = #{id}")
    Order findOrderById(Long id);

    /**
     * 获取所有可接受订单
     * @return
     */
    @Select("select * from `order` where status = 0")
    List<Order> getAllOrder();

}
