package com.withu.service;

import com.withu.pojo.entity.Order;

public interface IOrderService {
    boolean saveOrder(Order order);

    boolean cancelOrder(Long id);

    Order findOrderById(Long id);
}
