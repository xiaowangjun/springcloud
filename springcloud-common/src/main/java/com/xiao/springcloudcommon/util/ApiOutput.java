package com.xiao.springcloudcommon.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Strings;

@JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL) // 类的属性为NULL则不参与序列化
public class ApiOutput {
    // 通用字段
    private int code;
    private String msg;
    private String sign;
    // 成功时的字段
    private Object data;
    // 错误时的字段
    private String subcode; // 在具体错误上，code无法覆盖，msg又不方便填写。用途如接口的逻辑错误号，PARAM_ERROR的错误字段
    private String verbose; // 明细，如堆栈

    /*
     * success
     */
    public ApiOutput() {
        this.initSuccess(null);
    }

    /*
     * success
     */
    public ApiOutput(Object data) {
        this.initSuccess(data);
    }

    public ApiOutput(int code) {
        this.init(code, null, null, null);
    }

    public ApiOutput(int code, String message) {
        this.init(code, message, null, null);
    }

    public ApiOutput(int code, String message, String subcode) {
        this.init(code, message, null, subcode);
    }

    public ApiOutput(int code, String message, String subcode, String verbose) {
        this.init(code, message, null, subcode, verbose);
    }

    public ApiOutput(ApiCodeEnum ace) {
        this.init(ace.getCode(), null, ace.getMessage(), null);
    }

    public ApiOutput(ApiCodeEnum ace, String message) {
        this.init(ace.getCode(), message, ace.getMessage(), null);
    }

    public ApiOutput(ApiCodeEnum ace, String message, String subcode) {
        this.init(ace.getCode(), message, ace.getMessage(), subcode);
    }

    public ApiOutput(ApiCodeEnum ace, String message, String subcode, String verbose) {
        this.init(ace.getCode(), message, ace.getMessage(), subcode, verbose);
    }

    public void fail(ApiCodeEnum code, String message) {
        this.init(code.getCode(), message, code.getMessage(), null);
    }

    private void init(int code, String message, String defaultMessage, String subcode) {
        this.init(code, message, defaultMessage, subcode, null);
    }

    private void init(int code, String message, String defaultMessage, String subcode, String verbose) {
        this.code = code;
        this.msg = Strings.isNullOrEmpty(message) ? defaultMessage : message;
        this.data = null;
        this.subcode = subcode;
        this.verbose = verbose;
    }

    private void initSuccess(Object data) {
        this.code = ApiCodeEnum.SUCCESS.getCode();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getVerbose() {
        return verbose;
    }

    public void setVerbose(String verbose) {
        this.verbose = verbose;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return String.format("ApiOutput [code=%s, msg=%s, sign=%s, data=%s, subcode=%s, verbose=%s]", code, msg, sign, data, subcode, verbose);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return (this.code == ApiCodeEnum.SUCCESS.getCode());
    }
}
