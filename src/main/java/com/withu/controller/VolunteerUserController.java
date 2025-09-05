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
    public Result update(@RequestBody VolunteerUser volunteerUser) {
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
}
