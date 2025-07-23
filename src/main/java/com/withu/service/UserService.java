package com.withu.service;

import com.withu.pojo.dto.UserLoginDto;
import com.withu.pojo.entity.EnterpriseUser;
import com.withu.pojo.entity.User;

public interface UserService {
    public User login(UserLoginDto userLoginDTO);

    EnterpriseUser enterpriseUserlogin(UserLoginDto userLoginDTO);
}
