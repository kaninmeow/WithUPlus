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
    //ToDo 需要删除IgnoreAuth注解
    //ToDo 有bug
    @PostMapping("createOrder")
    @ApiOperation("创建订单")
    @IgnoreAuth
    public Result createOrder(@RequestBody Order order) {
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
    @IgnoreAuth
    public Result cancelOrder(@PathVariable Long id) {
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
}
