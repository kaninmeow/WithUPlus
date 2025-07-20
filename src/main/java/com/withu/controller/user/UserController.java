package com.withu.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.withu.annotation.IgnoreAuth;
import com.withu.pojo.dto.UserLoginDto;
import com.withu.pojo.entity.ConsumerUser;
import com.withu.pojo.entity.EnterpriseUser;
import com.withu.pojo.entity.User;
import com.withu.pojo.entity.VolunteerUser;
import com.withu.pojo.vo.UserLoginVo;
import com.withu.properties.JwtProperties;
import com.withu.result.Result;
import com.withu.service.IConsumerUserService;
import com.withu.service.IEnterpriseUserService;
import com.withu.service.IVolunteerUserService;
import com.withu.service.UserService;
import com.withu.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Api("User")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    IConsumerUserService iConsumerUserService;
    @Autowired
    IEnterpriseUserService iEnterpriseUserService;
    @Autowired
    IVolunteerUserService iVolunteerUserService;
    /**
     * login
     *
     */
    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("员工登录")
    @IgnoreAuth
    public Result<UserLoginVo> login(@RequestBody UserLoginDto userLoginDTO) {
        log.info("员工登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVo userLoginVo = UserLoginVo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .token(token)
                .build();

        return Result.success("登录成功",userLoginVo);
    }
    @PostMapping("/register")
    @ApiOperation("员工注册")
    @IgnoreAuth
    public Result register(@RequestBody User user) {
        log.info("员工注册：{}", user);
        if (user.getType() < 0 || user.getType() > 2) {
            return Result.error("用户类型错误");
        }
        switch (user.getType()) {
            case 0:
                return registerUser(user, iConsumerUserService, ConsumerUser.class, () -> new ConsumerUser());
            case 1:
                return registerUser(user, iEnterpriseUserService, EnterpriseUser.class, () -> new EnterpriseUser());
            case 2:
                return registerUser(user, iVolunteerUserService, VolunteerUser.class, () -> new VolunteerUser());
            default:
                return Result.error("用户类型错误");
        }
    }
    private <T> Result registerUser(User user, IService<T> service, Class<T> entityClass, Supplier<T> supplier) {
        // 检查重复
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        if (service.getOne(new QueryWrapper<T>().eq("phone", user.getPhone())) != null) {
            return Result.error("该手机号已被注册");
        }
        if (service.getOne(new QueryWrapper<T>().eq("username", user.getUsername())) != null) {
            return Result.error("该用户名已被注册");
        }
        T targetUser = supplier.get();
        BeanUtils.copyProperties(user, targetUser);
        service.save(targetUser);
        return Result.success("注册成功");
    }
}
