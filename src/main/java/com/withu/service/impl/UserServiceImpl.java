package com.withu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.withu.constant.UserTypeConstant;
import com.withu.mapper.UserMapper;
import com.withu.pojo.dto.UserLoginDto;
import com.withu.pojo.entity.User;
import com.withu.pojo.entity.VolunteerUser;
import com.withu.service.UserService;
import com.withu.utils.BusinessException;
import jakarta.annotation.Resource;
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
    @Resource
    private VolunteerUserServiceImpl volunteerUserServiceImpl;

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
        if (userLoginDTO.getType() == UserTypeConstant.CONSUMER) {
            //1、根据用户名查询数据库中的用户
            User user = userMapper.getConsumerByUsername(username);
            //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
            if (user == null) {
                //账号不存在
                return null;
            }
            //密码比对
            //对密码进行Md5加密处理
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!password.equals(user.getPassword())) {
                //密码错误
                return null;
            }

            //3、返回实体对象
            return user;
        } else if (userLoginDTO.getType() == UserTypeConstant.VOLUNTEER) {
            //1、根据用户名查询数据库中的用户
            VolunteerUser user = volunteerUserServiceImpl.getOne(new QueryWrapper<VolunteerUser>().eq("username", username));
            //将VolunteerUser转换为User
            User user1 = new User();
            BeanUtils.copyProperties(user, user1);

            //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
            if (user == null) {
                //账号不存在
                return null;
            }
            //密码比对
            //对密码进行Md5加密处理
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!password.equals(user.getPassword())) {
                //密码错误
                return null;
            }

            //3、返回实体对象
            return user1;
        } else if (userLoginDTO.getType() == UserTypeConstant.ADMIN) {
            //1、根据用户名查询数据库中的用户
            User user = userMapper.getAdminByUsername(username);
            //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
            if (user == null) {
                //账号不存在
                return null;
            }
            //密码比对
            //对密码进行Md5加密处理
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (!password.equals(user.getPassword())) {
                //密码错误
                return null;
            }

            //3、返回实体对象
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

    @Override
    public boolean checkType(UserLoginDto userLoginDTO) {
        if (Objects.equals(userLoginDTO.getType(), UserTypeConstant.CONSUMER)) {
            return true;
        }
        return false;
    }
}
