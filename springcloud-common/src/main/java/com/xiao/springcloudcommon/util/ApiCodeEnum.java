package com.xiao.springcloudcommon.util;

public enum ApiCodeEnum {
    // 系统
    SUCCESS(0, "成功"), BUSY(-1, "繁忙"), FAIL(1, "失败"), LOCK_FAIL(2, "资源锁定失败"), DB_OPERATION_FAIL(3, "数据库操作失败"),
    // 接口
    NO_METHOD(50, "无接口"), NO_PERMISSION(51, "无权限"), DEPRECATED(52, "接口废弃"), OPERATION_FAST(53, "操作频繁"), MAX_TRY(54, "超过最大尝试次数"),
    // 输入参数检查
    NO_PARAM(80, "无参数"), PARAM_ERROR(81, "参数错误"), PARAM_LENTH_ERROR(82, "参数长度错误"), PARAM_FORMAT_ERROR(82, "参数格式错误"),
    // 业务处理
    NOT_EXIST(400, "数据不存在"), EXIST(401, "数据已存在"), DATA_DUP(402, "数据重复"), DELETE_ERROR(413, "删除失败"), CREATE_ERROR(414, "创建失败"), UPDATE_ERROR(415, "修改失败"), CAPTCHA_ERROR(450, "验证码错误"), CAPTCHA_SEND_FAST(451, "验证码发送频繁"), ADDRESS_PARSE_ERROR(460, "地址解析错误"),
    // 账号
    LOGIN_FAIL(500, "登录失败"), NEED_LOGIN(501, "需登录"), ACCOUNT_FROZEN(503, "账户冻结"), ACCOUNT_WARNING(506, "账户警告"), MOBILE_USED(507, "手机号已使用"), ACCOUNT_BAN(508, "该账户已被禁用，请联系客服"), ACCOUNT_BIND(509, "该账号已完成绑定，请勿重复提交"), WX_USER_USED(510, "微信和微信公众号不是同一个账号"), MOBILE_NOT_EXIST(511, "手机号不存在"), PWD_ERROR(512, "密码错误"), SAME_PWD(513, "新旧密码相同"), ROLE_CODE_ERROR(520, "角色编码错误"),
    // 文件系统
    NO_DIR(900, "目录不存在"), EMPTY_DIR(901, "目录为空"), EXIST_DIR(905, "目录已存在"), EMPTY_FILE(902, "文件为空"), ERROR_FILE_TYPE(903, "文件格式错误"), NO_FILE(904, "文件不存在");

    private final int code;
    private final String msg;

    ApiCodeEnum(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.msg;
    }
}
