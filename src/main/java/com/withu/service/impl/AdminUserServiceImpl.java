package com.withu.service.impl;

import com.withu.constant.OrderStatusConstant;
import com.withu.mapper.OrderMapper;
import com.withu.pojo.entity.Order;
import com.withu.service.AdminUserService;
import com.withu.utils.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public void approveOrder(Long id) {
        log.info("订单通过审核，id为：{}", id);
        // 订单通过审核，更新订单状态
        Order order = orderMapper.findOrderById(id);
        if (order.getStatus() == OrderStatusConstant.UNDER_REVIEW) {
            orderMapper.setStatus(id, OrderStatusConstant.PENDING);
        } else {
            throw new BusinessException("订单状态错误");
        }
    }

    @Override
    public void rejectOrder(Long id, String reason) {
        log.info("订单通过审核，id为：{}，拒绝原因：{}", id, reason);
        // 订单拒绝，更新订单状态
        Order order = orderMapper.findOrderById(id);
        if (order.getStatus() == OrderStatusConstant.UNDER_REVIEW) {
            orderMapper.setStatus(id, OrderStatusConstant.REJECTED);
        } else {
            throw new BusinessException("订单状态错误");
        }
    }
}
