package com.withu.service.impl;

import com.withu.mapper.OrderMapper;
import com.withu.pojo.entity.Order;
import com.withu.pojo.entity.VolunteerUser;
import com.withu.mapper.VolunteerUserMapper;
import com.withu.service.IVolunteerUserService;
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

    @Override
    public List<Order> getAllOrder() {
        log.info("获取所有可接订单");
        List<Order> orderList = orderMapper.getAllOrder();
        return orderList;
    }
}
