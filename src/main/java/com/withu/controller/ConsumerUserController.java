package com.withu.controller;


import com.withu.pojo.entity.ConsumerUser;
import com.withu.result.Result;
import com.withu.service.IConsumerUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 普通消费者用户表 前端控制器
 * </p>
 *
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
    public Result update(@RequestBody ConsumerUser consumerUser) {
        log.info("普通消费者用户信息更新：{}",consumerUser);
        if(iConsumerUserService.updateById(consumerUser)){
            return Result.success("更新成功");
        }else {
            return Result.error("更新失败");
        }
    }

}
