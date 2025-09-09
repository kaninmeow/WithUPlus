package com.withu.service.impl;

import com.withu.constant.UserTypeConstant;
import com.withu.mapper.UserMapper;
import com.withu.pojo.dto.UserLoginDto;
import com.withu.pojo.entity.User;
import com.withu.service.UserService;
import com.withu.utils.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    public User login(UserLoginDto userLoginDTO) {
        //普通用户登录
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        //判断用户类型
        if (Objects.equals(userLoginDTO.getType(), UserTypeConstant.CONSUMER)) {
            User user = userMapper.getConsumerByUsername(username);
            //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
            if (user == null) {
                //账号不存在
                throw new BusinessException("账号不存在，请注册");
            }

            //密码比对
            //对密码进行Md5加密处理
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!password.equals(user.getPassword())) {
                //密码错误
                throw new BusinessException("密码错误请重新尝试");
            }
            //3、返回实体对象
            return user;

        } else if (Objects.equals(userLoginDTO.getType(), UserTypeConstant.VOLUNTEER)) {
            //志愿者用户登录
            User user = userMapper.getvolunteerByUsername(username);
            if (user == null) {
                //账号不存在
                throw new BusinessException("账号不存在，请注册");
            }
            //密码比对
            //对密码进行Md5加密处理
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!password.equals(user.getPassword())) {
                //密码错误
                throw new BusinessException("密码错误请重新尝试");
            }
            return user;
        } else if(Objects.equals(userLoginDTO.getType(), UserTypeConstant.ADMIN)) {
            //管理员用户登录
            User user = userMapper.getAdminByUsername(username);
            if (user == null) {
                //账号不存在
                throw new BusinessException("账号不存在，请注册");
            }
            //密码比对
            //对密码进行Md5加密处理
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!password.equals(user.getPassword())) {
                //密码错误
                throw new BusinessException("密码错误请重新尝试");
            }
            return user;
        } else {
            throw new BusinessException("用户类型错误");
        }


    }

    @Override
    public void changePassword(User user) {
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user1 = userMapper.getConsumerByUsername(user.getUsername());
        if (password.equals(user1.getPassword())) {
            user1.setPassword(password);
            userMapper.updatePasswordById(user1);
        } else {
            throw new BusinessException("原密码错误");
        }
    }
}
