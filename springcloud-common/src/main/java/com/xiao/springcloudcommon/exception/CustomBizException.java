package com.xiao.springcloudcommon.exception;

import com.google.common.base.Strings;
import com.xiao.springcloudcommon.util.ApiCodeEnum;

public class CustomBizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;

    private String subcode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public CustomBizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CustomBizException(int code, String message, String subcode) {
        super(message);
        this.code = code;
        this.subcode = subcode;
    }

    public CustomBizException(String message) {
        super(message);
        this.code = ApiCodeEnum.BUSY.getCode();
    }

    public CustomBizException(ApiCodeEnum apiCodeEnum) {
        super(apiCodeEnum.getMessage());
        this.code = apiCodeEnum.getCode();
    }

    public CustomBizException(ApiCodeEnum apiCodeEnum, String message) {
        super(message);
        this.code = apiCodeEnum.getCode();
    }

    /**
     * @param apiCodeEnum
     * @param subcode     大于0的数字
     */
    public CustomBizException(ApiCodeEnum apiCodeEnum, String message, String subcode) {
        super(Strings.isNullOrEmpty(message) ? apiCodeEnum.getMessage() : message);
        this.code = apiCodeEnum.getCode();
        this.subcode = subcode;
    }

    /*
     * 未知的系统异常
     */
    public boolean isSys() {
        return (this.code == ApiCodeEnum.BUSY.getCode() || this.code == ApiCodeEnum.FAIL.getCode() || this.code == ApiCodeEnum.LOCK_FAIL.getCode());
    }
}
