package com.withu.service.impl;

import com.withu.mapper.UserMapper;
import com.withu.pojo.dto.UserLoginDto;
import com.withu.pojo.entity.EnterpriseUser;
import com.withu.pojo.entity.User;
import com.withu.service.UserService;
import com.withu.utils.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
        //志愿者用户登录
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        //判断用户类型
        if (userLoginDTO.getType() == 0) {
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

        } else if (userLoginDTO.getType() == 1) {
            //普通用户登录
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
        } else {
            throw new BusinessException("用户类型错误");
        }


    }

    @Override
    public EnterpriseUser enterpriseUserlogin(UserLoginDto userLoginDTO) {
        //普通用户登录
        EnterpriseUser enterpriseUser = userMapper.getEnterpriseByUsername(userLoginDTO.getUsername());
        if (enterpriseUser == null) {
            //账号不存在
            throw new BusinessException("账号不存在，请注册");
        }
        //密码比对
        //对密码进行Md5加密处理
        String password = userLoginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(enterpriseUser.getPassword())) {
            //密码错误
            throw new BusinessException("密码错误请重新尝试");
        }
        return enterpriseUser;
    }
}
