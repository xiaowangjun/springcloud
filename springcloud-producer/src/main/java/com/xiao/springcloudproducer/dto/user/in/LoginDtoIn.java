package com.xiao.springcloudproducer.dto.user.in;

import javax.validation.constraints.NotNull;

public class LoginDtoIn {
    @NotNull(message = "手机号不能为空")
    private String mobile;

    @NotNull(message = "密码不能为空")
    private String password;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
