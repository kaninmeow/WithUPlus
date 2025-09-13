package com.withu.controller;

import com.withu.annotation.IgnoreAuth;
import com.withu.pojo.entity.Order;
import com.withu.result.Result;
import com.withu.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单表
 * @author
 * @since 2025-07-20
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService iOrderService;


    /**
     * 创建订单
     * @param order
     * @return
     */
    @PostMapping("createOrder")
    @ApiOperation("创建订单")
    public Result<String> createOrder(@RequestBody Order order) {
        log.info("创建订单：{}",order);
        if(iOrderService.saveOrder(order)){
            return Result.success("创建成功");
        }else {
            return Result.error("创建失败");
        }
    }
    /**
     * 取消订单
     * @param id
     * @return
     */
    @PutMapping("cancelOrder")
    @ApiOperation("取消订单")
    public Result<String>      cancelOrder(@PathVariable Long id) {
        log.info("取消订单：{}",id);
        if(iOrderService.cancelOrder(id)){
            return Result.success("取消成功");
        }else {
            return Result.error("取消失败");
        }
    }
    /**
     * 根据id获取Id详细信息
     * @param id
     * @return
     */
    @GetMapping("findOrderById/{id}")
    @ApiOperation("根据id获取Id详细信息")
    @IgnoreAuth
    public Result<Order> findOrderById(@PathVariable Long id) {
        log.info("根据id获取Id详细信息：{}",id);
        return Result.success("查询成功", iOrderService.findOrderById(id));
    }

    /**
     * 志愿者抢单
     * @param orderId 订单ID
     * @param volunteerUserId 志愿者用户ID
     * @return
     */
    @PostMapping("grabOrder")
    @ApiOperation("志愿者抢单")
    public Result<String> grabOrder(@RequestParam Long orderId, @RequestParam Long volunteerUserId) {
        log.info("志愿者抢单：订单ID={}, 志愿者ID={}", orderId, volunteerUserId);
        try {
            if (iOrderService.grabOrder(orderId, volunteerUserId)) {
                return Result.success("抢单成功");
            } else {
                return Result.error("抢单失败");
            }
        } catch (Exception e) {
            log.error("抢单异常：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
