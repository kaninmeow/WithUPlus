package com.withu.service.impl;

import com.withu.mapper.OrderMapper;
import com.withu.pojo.entity.Order;
import com.withu.pojo.entity.VolunteerUser;
import com.withu.mapper.VolunteerUserMapper;
import com.withu.service.IVolunteerUserService;
import com.withu.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 志愿者用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2025-07-20
 */
@Service
@Slf4j
public class VolunteerUserServiceImpl extends ServiceImpl<VolunteerUserMapper, VolunteerUser> implements IVolunteerUserService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    IOrderService orderService;

    @Override
    public List<Order> getAllOrder() {
        log.info("获取所有可接订单");
        List<Order> orderList = orderMapper.getAllOrder();
        return orderList;
    }

    @Override
    public boolean abandonGrabOrder(Long orderId, Long volunteerUserId) {
        log.info("志愿者放弃抢单：订单ID={}, 志愿者ID={}", orderId, volunteerUserId);
        return orderService.abandonGrabOrder(orderId, volunteerUserId);
    }

    @Override
    public boolean completeOrder(Long orderId, Long volunteerUserId) {
        log.info("志愿者完成订单：订单ID={}, 志愿者ID={}", orderId, volunteerUserId);
        return orderService.completeOrder(orderId, volunteerUserId);
    }

    @Override
    public List<Order> getCompletedOrdersByVolunteer(Long volunteerUserId) {
        log.info("获取志愿者完成的所有订单：志愿者ID={}", volunteerUserId);
        return orderService.getCompletedOrdersByVolunteer(volunteerUserId);
    }

    @Override
    public List<Order> getInProgressOrdersByVolunteer(Long volunteerUserId) {
        log.info("获取志愿者所有进行中的订单：志愿者ID={}", volunteerUserId);
        return orderService.getInProgressOrdersByVolunteer(volunteerUserId);
    }

    @Override
    public boolean cancelOrderByVolunteer(Long orderId, Long volunteerUserId) {
        log.info("志愿者取消订单：订单ID={}, 志愿者ID={}", orderId, volunteerUserId);
        return orderService.cancelOrderByVolunteer(orderId, volunteerUserId);
    }
}
