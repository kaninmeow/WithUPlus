package com.withu.mapper;

import com.withu.pojo.entity.Order;
import com.withu.constant.OrderStatusConstant;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {

    void saveOrder(Order order);

    @Update("update `order` set status = " + OrderStatusConstant.CANCELED + " where id = #{id}")
    void cancelOrder(Long id);

    @Select("select * from `order` where id = #{id}")
    Order findOrderById(Long id);

    /**
     * 获取所有可接受订单
     * @return
     */
    @Select("select * from `order` where status = " + OrderStatusConstant.PENDING)
    List<Order> getAllOrder();

    /**
     * 志愿者抢单
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return 影响行数
     */
    @Update("update `order` set volunteer_user_id = #{volunteerUserId}, status = " + OrderStatusConstant.IN_PROGRESS + " where id = #{orderId} and status = " + OrderStatusConstant.PENDING)
    int grabOrder(Long orderId, Long volunteerUserId);

    /**
     * 志愿者放弃抢单（记录志愿者放弃某个订单）
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return 影响行数
     */
    @Insert("insert into volunteer_abandon_order (order_id, volunteer_user_id, abandon_time) values (#{orderId}, #{volunteerUserId}, now())")
    int abandonGrabOrder(Long orderId, Long volunteerUserId);

    /**
     * 检查志愿者是否已经放弃过某个订单
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return 记录数
     */
    @Select("select count(*) from volunteer_abandon_order where order_id = #{orderId} and volunteer_user_id = #{volunteerUserId}")
    int checkAbandoned(Long orderId, Long volunteerUserId);

    /**
     * 志愿者完成订单
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return 影响行数
     */
    @Update("update `order` set status = " + OrderStatusConstant.TO_BE_REVIEWED + " where id = #{orderId} and volunteer_user_id = #{volunteerUserId} and status = " + OrderStatusConstant.IN_PROGRESS)
    int completeOrder(Long orderId, Long volunteerUserId);

    /**
     * 获取志愿者完成的所有订单
     * @param volunteerUserId 志愿者用户ID
     * @return 完成的订单列表
     */
    @Select("select * from `order` where volunteer_user_id = #{volunteerUserId} and status = " + OrderStatusConstant.TO_BE_REVIEWED)
    List<Order> getCompletedOrdersByVolunteer(Long volunteerUserId);

    /**
     * 获取志愿者所有进行中的订单
     * @param volunteerUserId 志愿者用户ID
     * @return 进行中的订单列表
     */
    @Select("select * from `order` where volunteer_user_id = #{volunteerUserId} and status = " + OrderStatusConstant.IN_PROGRESS)
    List<Order> getInProgressOrdersByVolunteer(Long volunteerUserId);

    /**
     * 志愿者取消订单
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return 影响行数
     */
    @Update("update `order` set volunteer_user_id = null, status = " + OrderStatusConstant.PENDING + " where id = #{orderId} and volunteer_user_id = #{volunteerUserId} and status = " + OrderStatusConstant.IN_PROGRESS)
    int cancelOrderByVolunteer(Long orderId, Long volunteerUserId);

    /**
     * 根据普通消费者用户ID获取所有待接单的订单（status = 0）
     * @param consumerUserId 普通消费者用户ID
     * @return 待接单订单列表
     */
    @Select("select * from `order` where consumer_id = #{consumerUserId} and status = " + OrderStatusConstant.PENDING)
    List<Order> getPendingOrdersByConsumer(Long consumerUserId);

    /**
     * 根据普通消费者用户ID获取所有审核中的订单（status = 4）
     * @param consumerUserId 普通消费者用户ID
     * @return 审核中订单列表
     */
    @Select("select * from `order` where consumer_id = #{consumerUserId} and status = " + OrderStatusConstant.UNDER_REVIEW)
    List<Order> getReviewingOrdersByConsumer(Long consumerUserId);

    /**
     * 根据普通消费者用户ID获取所有进行中的订单（status = 1）
     * @param consumerUserId 普通消费者用户ID
     * @return 进行中订单列表
     */
    @Select("select * from `order` where consumer_id = #{consumerUserId} and status = " + OrderStatusConstant.IN_PROGRESS)
    List<Order> getInProgressOrdersByConsumer(Long consumerUserId);

    /**
     * 根据普通消费者用户ID获取所有已完成的订单（status = 3）
     * @param consumerUserId 普通消费者用户ID
     * @return 已完成订单列表
     */
    @Select("select * from `order` where consumer_id = #{consumerUserId} and status = " + OrderStatusConstant.COMPLETED)
    List<Order> getCompletedOrdersByConsumer(Long consumerUserId);

    @Update("update `order` set status = #{status} where id = #{id}")
    void setStatus(Long id, int status);
}
