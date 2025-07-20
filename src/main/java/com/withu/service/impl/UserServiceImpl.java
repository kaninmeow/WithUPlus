package com.withu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.withu.mapper.UserMapper;
import com.withu.pojo.dto.UserLoginDto;
import com.withu.pojo.entity.User;
import com.withu.service.UserService;
import com.withu.utils.BusinessException;
import lombok.extern.slf4j.Slf4j;
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

        } else {
            //ToDo 增加其他类型用户
            User user = userMapper.getConsumerByUsername(username);
            return null;
        }


    }
}
