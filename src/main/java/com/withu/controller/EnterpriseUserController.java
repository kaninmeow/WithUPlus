package com.withu.controller;


import com.withu.pojo.entity.EnterpriseUser;
import com.withu.result.Result;
import com.withu.service.IEnterpriseUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 企业用户表 前端控制器
 * </p>
 *
 * @author 
 * @since 2025-07-20
 */
@Slf4j
@RestController
@RequestMapping("/enterprise-user")
public class EnterpriseUserController {
    @Autowired
    IEnterpriseUserService iEnterpriseUserService;
    @PostMapping("/update")
    @ApiOperation("企业用户信息更新")
    public Result update(@RequestBody EnterpriseUser enterpriseUser) {
        log.info("企业用户信息更新：{}",enterpriseUser);
        if(iEnterpriseUserService.updateById(enterpriseUser)){
            return Result.success("更新成功");
        }else {
            return Result.error("更新失败");
        }
    }
}
