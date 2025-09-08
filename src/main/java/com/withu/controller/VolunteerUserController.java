package com.withu.controller;


import com.withu.pojo.entity.Order;
import com.withu.pojo.entity.VolunteerUser;
import com.withu.result.Result;
import com.withu.service.IVolunteerUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 志愿者用户表
 *
 * @author 
 * @since 2025-07-20
 */
@Slf4j
@RestController
@RequestMapping("/volunteer-user")
public class VolunteerUserController {
    @Autowired
    IVolunteerUserService iVolunteerUserService;
    @PostMapping("/update")
    @ApiOperation("志愿者用户信息更新")
    public Result<String> update(@RequestBody VolunteerUser volunteerUser) {
        log.info("志愿者用户信息更新：{}",volunteerUser);
        if(iVolunteerUserService.updateById(volunteerUser)){
            return Result.success("更新成功");
        }else {
            return Result.error("更新失败");
        }
    }
    /**
     * 获取所有可接订单
     * @return
     *
     */
    @GetMapping("/getAllOrder")
    @ApiOperation("获取所有可接订单")
    public Result<List<Order>> getAllOrder() {
        return Result.success("获取成功",iVolunteerUserService.getAllOrder());
    }

    /**
     * 志愿者放弃抢单（在抢单前放弃）
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return
     */
    @PostMapping("/abandonGrabOrder")
    @ApiOperation("志愿者放弃抢单")
    public Result<String> abandonGrabOrder(@RequestParam Long orderId, @RequestParam Long volunteerUserId) {
        log.info("志愿者放弃抢单：订单ID={}, 志愿者ID={}", orderId, volunteerUserId);
        try {
            if (iVolunteerUserService.abandonGrabOrder(orderId, volunteerUserId)) {
                return Result.success("放弃抢单成功");
            } else {
                return Result.error("放弃抢单失败");
            }
        } catch (Exception e) {
            log.error("放弃抢单异常：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 志愿者完成订单
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return
     */
    @PostMapping("/completeOrder")
    @ApiOperation("志愿者完成订单")
    public Result<String> completeOrder(@RequestParam Long orderId, @RequestParam Long volunteerUserId) {
        log.info("志愿者完成订单：订单ID={}, 志愿者ID={}", orderId, volunteerUserId);
        try {
            if (iVolunteerUserService.completeOrder(orderId, volunteerUserId)) {
                return Result.success("完成订单成功");
            } else {
                return Result.error("完成订单失败");
            }
        } catch (Exception e) {
            log.error("完成订单异常：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取志愿者完成的所有订单详情
     * @param volunteerUserId 志愿者用户ID
     * @return
     */
    @GetMapping("/getCompletedOrders")
    @ApiOperation("获取志愿者完成的所有订单详情")
    public Result<List<Order>> getCompletedOrders(@RequestParam Long volunteerUserId) {
        log.info("获取志愿者完成的所有订单：志愿者ID={}", volunteerUserId);
        try {
            List<Order> completedOrders = iVolunteerUserService.getCompletedOrdersByVolunteer(volunteerUserId);
            return Result.success("获取成功", completedOrders);
        } catch (Exception e) {
            log.error("获取志愿者完成订单异常：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取志愿者所有进行中的订单详情
     * @param volunteerUserId 志愿者用户ID
     * @return
     */
    @GetMapping("/getInProgressOrders")
    @ApiOperation("获取志愿者所有进行中的订单详情")
    public Result<List<Order>> getInProgressOrders(@RequestParam Long volunteerUserId) {
        log.info("获取志愿者所有进行中的订单：志愿者ID={}", volunteerUserId);
        try {
            List<Order> inProgressOrders = iVolunteerUserService.getInProgressOrdersByVolunteer(volunteerUserId);
            return Result.success("获取成功", inProgressOrders);
        } catch (Exception e) {
            log.error("获取志愿者进行中订单异常：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 志愿者取消订单
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return
     */
    @PostMapping("/cancelOrder")
    @ApiOperation("志愿者取消订单")
    public Result<String> cancelOrder(@RequestParam Long orderId, @RequestParam Long volunteerUserId) {
        log.info("志愿者取消订单：订单ID={}, 志愿者ID={}", orderId, volunteerUserId);
        try {
            if (iVolunteerUserService.cancelOrderByVolunteer(orderId, volunteerUserId)) {
                return Result.success("取消订单成功");
            } else {
                return Result.error("取消订单失败");
            }
        } catch (Exception e) {
            log.error("志愿者取消订单异常：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

}
