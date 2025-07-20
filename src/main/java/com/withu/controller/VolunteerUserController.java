package com.withu.controller;


import com.withu.pojo.entity.VolunteerUser;
import com.withu.result.Result;
import com.withu.service.IVolunteerUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 志愿者用户表 前端控制器
 * </p>
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
}
