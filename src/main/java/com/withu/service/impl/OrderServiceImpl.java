package com.withu.service.impl;

import com.withu.mapper.OrderMapper;
import com.withu.pojo.entity.Order;
import com.withu.service.IOrderService;
import com.withu.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public boolean saveOrder(Order order) {
        orderMapper.saveOrder(order);
        return true;
    }

    @Override
    public Order findOrderById(Long id) {
        Order order = orderMapper.findOrderById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        return order;
    }

    @Override
    public boolean cancelOrder(Long id) {
        Integer status = findOrderById(id).getStatus();
        if (status == 2 || status == -1) {
            throw new BusinessException("订单状态错误,不可取消");
        }
        orderMapper.cancelOrder(id);
        return true;
    }

    @Override
    public boolean grabOrder(Long orderId, Long volunteerUserId) {
        // 检查订单是否存在
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        // 检查订单状态是否为待接单状态（0）
        if (order.getStatus() != 0) {
            throw new BusinessException("订单状态错误，无法抢单");
        }

        // 执行抢单操作
        int result = orderMapper.grabOrder(orderId, volunteerUserId);
        if (result > 0) {
            return true;
        } else {
            throw new BusinessException("抢单失败，订单可能已被其他志愿者抢走");
        }
    }

    @Override
    public boolean abandonGrabOrder(Long orderId, Long volunteerUserId) {
        // 检查订单是否存在
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 检查订单状态是否为待接单状态（0）
        if (order.getStatus() != 0) {
            throw new BusinessException("订单状态错误，无法放弃抢单");
        }
        
        // 检查志愿者是否已经放弃过这个订单
        int abandonedCount = orderMapper.checkAbandoned(orderId, volunteerUserId);
        if (abandonedCount > 0) {
            throw new BusinessException("您已经放弃过这个订单");
        }
        
        // 执行放弃抢单操作
        int result = orderMapper.abandonGrabOrder(orderId, volunteerUserId);
        if (result > 0) {
            return true;
        } else {
            throw new BusinessException("放弃抢单失败");
        }
    }

    @Override
    public boolean completeOrder(Long orderId, Long volunteerUserId) {
        // 检查订单是否存在
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 检查订单是否属于该志愿者
        if (order.getVolunteerUserId() == null || !order.getVolunteerUserId().equals(volunteerUserId)) {
            throw new BusinessException("您没有权限完成此订单");
        }
        
        // 检查订单状态是否为已接单状态（1）
        if (order.getStatus() != 1) {
            throw new BusinessException("订单状态错误，无法完成");
        }
        
        // 执行完成订单操作
        int result = orderMapper.completeOrder(orderId, volunteerUserId);
        if (result > 0) {
            return true;
        } else {
            throw new BusinessException("完成订单失败");
        }
    }

    @Override
    public List<Order> getCompletedOrdersByVolunteer(Long volunteerUserId) {
        if (volunteerUserId == null) {
            throw new BusinessException("志愿者用户ID不能为空");
        }
        return orderMapper.getCompletedOrdersByVolunteer(volunteerUserId);
    }

    @Override
    public List<Order> getInProgressOrdersByVolunteer(Long volunteerUserId) {
        if (volunteerUserId == null) {
            throw new BusinessException("志愿者用户ID不能为空");
        }
        return orderMapper.getInProgressOrdersByVolunteer(volunteerUserId);
    }

    @Override
    public boolean cancelOrderByVolunteer(Long orderId, Long volunteerUserId) {
        // 检查订单是否存在
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 检查订单是否属于该志愿者
        if (order.getVolunteerUserId() == null || !order.getVolunteerUserId().equals(volunteerUserId)) {
            throw new BusinessException("您没有权限取消此订单");
        }
        
        // 检查订单状态是否为已接单状态（1）
        if (order.getStatus() != 1) {
            throw new BusinessException("订单状态错误，无法取消");
        }
        
        // 执行取消订单操作
        int result = orderMapper.cancelOrderByVolunteer(orderId, volunteerUserId);
        if (result > 0) {
            return true;
        } else {
            throw new BusinessException("取消订单失败");
        }
    }
}
