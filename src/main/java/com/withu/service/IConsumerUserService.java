package com.withu.service;

import com.withu.pojo.entity.Address;
import com.withu.pojo.entity.ConsumerUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.withu.pojo.entity.Elder;
import com.withu.pojo.entity.Order;

import java.util.List;

/**
 * <p>
 * 普通消费者用户表 服务类
 * </p>
 *
 * @author 
 * @since 2025-07-20
 */
public interface IConsumerUserService extends IService<ConsumerUser> {

    boolean relevantToTheElderly(Elder elder);

    ConsumerUser getConsumerUserById(Long id);

    List<Elder> findAllEldersById(Long id);

    boolean saveAddress(Address address);

    List<Address> findAllAddressById(Long id);

    Address findAddressById(Long id);

    boolean removeAddressById(Long id);

    boolean updateAddressById(Address address);

    /**
     * 获取该普通消费者用户所有待接单的订单
     * @param consumerUserId 普通消费者用户ID
     * @return 待接单订单列表
     */
    List<Order> getPendingOrdersByConsumer(Long consumerUserId);

    /**
     * 获取该普通消费者用户所有审核中的订单
     * @param consumerUserId 普通消费者用户ID
     * @return 审核中订单列表
     */
    List<Order> getReviewingOrdersByConsumer(Long consumerUserId);

    /**
     * 获取该普通消费者用户所有进行中的订单
     * @param consumerUserId 普通消费者用户ID
     * @return 进行中订单列表
     */
    List<Order> getInProgressOrdersByConsumer(Long consumerUserId);

    /**
     * 获取该普通消费者用户所有已完成的订单
     * @param consumerUserId 普通消费者用户ID
     * @return 已完成订单列表
     */
    List<Order> getCompletedOrdersByConsumer(Long consumerUserId);

    /**
     * 根据老人ID获取老人详细信息
     * @param elderId 老人ID
     * @return 老人详细信息
     */
    Elder getElderById(Long elderId);

    /**
     * 根据老人ID删除老人
     * @param elderId 老人ID
     * @return 删除结果
     */
    boolean deleteElderById(Long elderId);

    /**
     * 更新老人信息
     * @param elder 老人信息
     * @return 更新结果
     */
    boolean updateElderById(Elder elder);

    /**
     * 根据订单ID获取志愿者电话
     * @param orderId 订单ID
     * @return 志愿者电话
     */
    String getVolunteerPhoneByOrderId(Long orderId);
}
