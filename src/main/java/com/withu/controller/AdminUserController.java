package com.withu.controller;

import com.withu.result.Result;
import com.withu.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员用户表
 */
@Slf4j
@RestController
@RequestMapping("/admin-user")
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;

    /**
     * 订单通过审核
     * @param id
     * @return
     */
    @PostMapping("/approveOrder")
    public Result approveOrder(@RequestParam("id") Long id) {
        log.info("订单通过审核，id为：{}", id);
        adminUserService.approveOrder(id);
        return Result.success("成功");
    }

    /**
     * 订单拒绝
     * @param id
     * @return
     */
    @PostMapping("/reject")
    public Result rejectOrder(@RequestParam("id") Long id, @RequestParam("reason") String reason) {
        log.info("订单拒绝，id为：{}，拒绝原因：{}", id, reason);
        adminUserService.rejectOrder(id, reason);
        return Result.success("成功");
    }

}
