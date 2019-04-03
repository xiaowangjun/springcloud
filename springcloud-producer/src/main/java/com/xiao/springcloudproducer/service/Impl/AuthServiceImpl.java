package com.xiao.springcloudproducer.service.Impl;

import com.xiao.springcloudproducer.dto.user.in.LoginDtoIn;
import com.xiao.springcloudproducer.entity.User;
import com.xiao.springcloudproducer.mapper.UserMapper;
import com.xiao.springcloudproducer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByMobile(LoginDtoIn loginDtoIn) {
        User user = new User();
        user.setMobile(loginDtoIn.getMobile());
        return userMapper.selectOne(user);
    }

    public static void main(String[] args) {
        String a = "1231231231";
        String b = new String("1231231231");
        System.out.println(a.equals(b));
    }


}
