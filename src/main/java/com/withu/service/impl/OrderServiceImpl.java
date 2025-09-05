package com.withu.service.impl;

import com.withu.mapper.OrderMapper;
import com.withu.pojo.entity.Order;
import com.withu.service.IOrderService;
import com.withu.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
