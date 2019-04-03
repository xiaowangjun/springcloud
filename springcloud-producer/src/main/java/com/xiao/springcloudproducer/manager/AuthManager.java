package com.xiao.springcloudproducer.manager;

import com.xiao.springcloudcommon.util.ApiCodeEnum;
import com.xiao.springcloudcommon.util.ApiOutput;
import com.xiao.springcloudcommon.util.GenerateUtil;
import com.xiao.springcloudproducer.dto.user.in.LoginDtoIn;
import com.xiao.springcloudproducer.dto.user.out.LoginDtoOut;
import com.xiao.springcloudproducer.entity.User;
import com.xiao.springcloudproducer.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class AuthManager {
    @Autowired
    private AuthService authService;

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, LoginDtoOut> sessionDTORedisTemplate;

    public ApiOutput login(LoginDtoIn loginDtoIn) {
        User user = authService.findUserByMobile(loginDtoIn);
        if (StringUtils.isEmpty(user)) {
            return new ApiOutput(ApiCodeEnum.MOBILE_NOT_EXIST);
        }
        if (!user.getPassword().equals(loginDtoIn.getPassword())) {
            return new ApiOutput(ApiCodeEnum.PWD_ERROR);
        }
        LoginDtoOut loginDtoOut = new LoginDtoOut();
        BeanUtils.copyProperties(user, loginDtoOut);
        loginDtoOut.setToken(GenerateUtil.genUUID());

        sessionDTORedisTemplate.opsForValue().set(loginDtoOut.getToken(), loginDtoOut, 10800, TimeUnit.SECONDS);

        return new ApiOutput(loginDtoOut);
    }

    public void logout(String token) {
        sessionDTORedisTemplate.delete(token);
    }
}
