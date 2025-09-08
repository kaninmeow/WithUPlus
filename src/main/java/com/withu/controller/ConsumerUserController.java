package com.withu.controller;


import com.withu.pojo.entity.Address;
import com.withu.pojo.entity.ConsumerUser;
import com.withu.pojo.entity.Elder;
import com.withu.pojo.entity.Order;
import com.withu.result.Result;
import com.withu.service.IConsumerUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 普通消费者用户表
 * @author 
 * @since 2025-07-20
 */
@Slf4j
@RestController
@RequestMapping("/consumer-user")
public class ConsumerUserController {
    @Autowired
    IConsumerUserService iConsumerUserService;
    @PostMapping("/update")
    @ApiOperation("普通消费者用户信息更新")
    public Result<String> update(@RequestBody ConsumerUser consumerUser) {
        log.info("普通消费者用户信息更新：{}",consumerUser);
        if(iConsumerUserService.updateById(consumerUser)){
            return Result.success("更新成功");
        }else {
            return Result.error("更新失败");
        }
    }

    /**
     * 普通消费者用户与老年人的关联
     * @param elder
     * @return
     */
    @ApiOperation("普通消费者用户与老年人的关联")
    @PostMapping("relevantToTheElderly")
    public Result<String> relevantToTheElderly(@RequestBody Elder elder) {
        log.info("普通消费者用户与老年人的关联：{}",elder);
        if(iConsumerUserService.relevantToTheElderly(elder)){
            return Result.success("关联成功");
        }else {
            return Result.error("关联失败");
        }
    }

    /**
     * 根据id查询普通消费者用户
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation("根据id查询普通消费者用户")
    public Result<ConsumerUser> getConsumerUserById(@PathVariable Long id) {
        log.info("根据id查询普通消费者用户：{}",id);
        return Result.success("查询成功", iConsumerUserService.getConsumerUserById(id));
    }

    /**
     * 根据Id查找绑定的老年人
     * @param id
     * @return
     */
    @GetMapping("findAllEldersById/{id}")
    @ApiOperation("根据Id查找绑定的所有老人")
    public Result<List<Elder>> findAllEldersById(@PathVariable Long id) {
        log.info("根据Id查找绑定的所有老人：{}",id);
        return Result.success("查询成功", iConsumerUserService.findAllEldersById(id));
    }

    /**
     * 
     * 创建地址
     * @param address
     * @return
     */
    @PostMapping("/createAddress")
    @ApiOperation("创建地址")
    public Result<String> createAddress(@RequestBody Address address) {
        log.info("创建地址：{}",address);
        if(iConsumerUserService.saveAddress(address)){
            return Result.success("创建成功");
        }else {
            return Result.error("创建失败");
        }
    }

    /**
     * 根据用户Id查找绑定的所有地址
     * @param id
     * @return
     */
    @GetMapping("findAllAddressById/{id}")
    @ApiOperation("根据Id查找绑定的所有地址")
    public Result<List<Address>> findAllAddressById(@PathVariable Long id) {
        log.info("根据Id查找绑定的所有地址：{}",id);
        return Result.success("查询成功", iConsumerUserService.findAllAddressById(id));
    }
    /**
     * 根据地址id查询具体地址
     * @param id
     * @return
     */
    @GetMapping("findAddressById/{id}")
    @ApiOperation("根据地址id查询具体地址")
    public Result<Address> findAddressById(@PathVariable Long id) {
        log.info("根据地址id查询具体地址：{}",id);
        return Result.success("查询成功", iConsumerUserService.findAddressById(id));
    }

    /**
     * 根据地址id删除地址
     * @param id
     * @return
     */
    @DeleteMapping("deleteAddressById/{id}")
    @ApiOperation("根据地址id删除地址")
            public Result<String> deleteAddressById(@PathVariable Long id) {
                log.info("根据地址id删除地址：{}",id);
                if(iConsumerUserService.removeAddressById(id)){
                    return Result.success("删除成功");
                }else {
                    return Result.error("删除失败");
        }
    }

    /**
     * 根据地址id更新地址
     * @param address
     * @return
     */
    @PutMapping("updateAddressById")
    @ApiOperation("根据地址id更新地址")
    public Result<String> updateAddressById(@RequestBody Address address) {
        log.info("根据地址id更新地址：{}",address);
        if(iConsumerUserService.updateAddressById(address)){
            return Result.success("更新成功");
        }else {
            return Result.error("更新失败");
        }
    }

    // 获取该用户所有待接单的订单
    @GetMapping("getPendingOrders")
    @ApiOperation("获取该用户所有待接单的订单")
    public Result<List<Order>> getPendingOrders(@RequestParam Long consumerUserId) {
        log.info("获取用户待接单订单：用户ID={}", consumerUserId);
        return Result.success("获取成功", iConsumerUserService.getPendingOrdersByConsumer(consumerUserId));
    }

    // 获取该用户所有审核中的订单
    @GetMapping("getReviewingOrders")
    @ApiOperation("获取该用户所有审核中的订单")
    public Result<List<Order>> getReviewingOrders(@RequestParam Long consumerUserId) {
        log.info("获取用户审核中订单：用户ID={}", consumerUserId);
        return Result.success("获取成功", iConsumerUserService.getReviewingOrdersByConsumer(consumerUserId));
    }

    // 获取该用户所有进行中的订单
    @GetMapping("getInProgressOrders")
    @ApiOperation("获取该用户所有进行中的订单")
    public Result<List<Order>> getInProgressOrders(@RequestParam Long consumerUserId) {
        log.info("获取用户进行中订单：用户ID={}", consumerUserId);
        return Result.success("获取成功", iConsumerUserService.getInProgressOrdersByConsumer(consumerUserId));
    }

    // 获取该用户所有已完成的订单
    @GetMapping("getCompletedOrders")
    @ApiOperation("获取该用户所有已完成的订单")
    public Result<List<Order>> getCompletedOrders(@RequestParam Long consumerUserId) {
        log.info("获取用户已完成订单：用户ID={}", consumerUserId);
        return Result.success("获取成功", iConsumerUserService.getCompletedOrdersByConsumer(consumerUserId));
    }

    /**
     * 根据老人ID获取老人详细信息
     * @param elderId 老人ID
     * @return 老人详细信息
     */
    @GetMapping("getElderById/{elderId}")
    @ApiOperation("根据老人ID获取老人详细信息")
    public Result<Elder> getElderById(@PathVariable Long elderId) {
        log.info("根据老人ID获取老人详细信息：{}", elderId);
        return Result.success("查询成功", iConsumerUserService.getElderById(elderId));
    }

    /**
     * 根据老人ID删除老人
     * @param elderId 老人ID
     * @return 删除结果
     */
    @DeleteMapping("deleteElderById/{elderId}")
    @ApiOperation("根据老人ID删除老人")
    public Result<String> deleteElderById(@PathVariable Long elderId) {
        log.info("根据老人ID删除老人：{}", elderId);
        if (iConsumerUserService.deleteElderById(elderId)) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

}
