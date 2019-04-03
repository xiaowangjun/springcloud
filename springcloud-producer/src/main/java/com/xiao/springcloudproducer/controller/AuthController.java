package com.xiao.springcloudproducer.controller;

import com.xiao.springcloudcommon.util.ApiOutput;
import com.xiao.springcloudcommon.util.ArrayUtil;
import com.xiao.springcloudcommon.util.JsonToObject;
import com.xiao.springcloudcommon.util.WebUtil;
import com.xiao.springcloudproducer.dto.user.in.LoginDtoIn;
import com.xiao.springcloudproducer.manager.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthManager authManager;

    @RequestMapping("login")
    public ApiOutput login(@RequestBody String json) {
        LoginDtoIn loginDtoIn = JsonToObject.checkAndParseParams(json, LoginDtoIn.class);

        return authManager.login(loginDtoIn);
    }

    @RequestMapping("logout")
    public ApiOutput logout(@RequestBody String json) {
        Map<String, Object> map = WebUtil.getJsonParams(json);
        String token = ArrayUtil.getMapString(map, "token");
        authManager.logout(token);
        return new ApiOutput();
    }
}
