package com.withu.service;

import com.withu.pojo.entity.Order;
import com.withu.pojo.entity.VolunteerUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 志愿者用户表 服务类
 * </p>
 *
 * @author 
 * @since 2025-07-20
 */
public interface IVolunteerUserService extends IService<VolunteerUser> {

    List<Order> getAllOrder();

    /**
     * 志愿者放弃抢单（在抢单前放弃）
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return 放弃抢单结果
     */
    boolean abandonGrabOrder(Long orderId, Long volunteerUserId);

    /**
     * 志愿者完成订单
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return 完成订单结果
     */
    boolean completeOrder(Long orderId, Long volunteerUserId);

    /**
     * 获取志愿者完成的所有订单
     * @param volunteerUserId 志愿者用户ID
     * @return 完成的订单列表
     */
    List<Order> getCompletedOrdersByVolunteer(Long volunteerUserId);

    /**
     * 获取志愿者所有进行中的订单
     * @param volunteerUserId 志愿者用户ID
     * @return 进行中的订单列表
     */
    List<Order> getInProgressOrdersByVolunteer(Long volunteerUserId);

    /**
     * 志愿者取消订单
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return 取消订单结果
     */
    boolean cancelOrderByVolunteer(Long orderId, Long volunteerUserId);

}
