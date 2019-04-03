package com.xiao.springcloudproducer.service;

import com.xiao.springcloudproducer.dto.user.in.LoginDtoIn;
import com.xiao.springcloudproducer.entity.User;

public interface AuthService {

    User findUserByMobile(LoginDtoIn loginDtoIn);
}
