package com.withu.service;

import com.withu.pojo.dto.UserLoginDto;
import com.withu.pojo.entity.User;

public interface UserService {
    public User login(UserLoginDto userLoginDTO);


    void changePassword(User user);

    boolean checkType(UserLoginDto userLoginDTO);
}
