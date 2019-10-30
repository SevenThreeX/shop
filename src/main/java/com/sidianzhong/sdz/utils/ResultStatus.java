package com.sidianzhong.sdz.utils;

public enum ResultStatus {
    SUCCESS(200, "成功"),
    ERROR(500, "失败"),

    //提示错误
    LOGIN_PHONE_ERROR(500, "手机号不正确"),
    LOGIN_PASSWORD_ERROR(500, "密码格式错误"),
    LOGIN_CODE_ERROR(500, "验证码不正确"),
    LOGIN_THIRD_ERROR(500, "第三方登陆失败"),
    USER_NOT_EXITS_ERROR(500, "用户不存在"),
    USER_IS_EXITS_ERROR(500, "用户已存在"),
    REGISTER_FAIL_ERROR(500, "注册失败"),
    LOGIN_FAIL_ERROR(500, "登陆失败"),
    UPDATE_FAIL_ERROR(500, "修改失败"),
    SENDMASSAGE_ERROR(500, "短信发送失败"),
    SENDMASSAGE_AGAIN(500, "操作过于频繁，请稍后重试"),
    UPDATE_PASSWORD_ERROR(500, "旧密码不正确"),


    //特殊错误处理
    TOKEN_ERROR(100001, "无效token"),
    THIRD_LOGIN_ERROR(100004, "请绑定手机号"),  //第三方登陆绑定手机号
    USER_IS_FROZEN_ERROR(100005, "用户被冻结"),
    PHONE_BIND_ERROR(100006, "该手机号已被绑定"),
    WECHAT_BINF_ERROR(100006, "该微信绑定过手机号"),
    NOT_PERMISSION(100007, "没有操作权限"),

    //支付
    NO_ALIPAY_WITHDRAW_HAS_BEEN_OPENED_YET(1000008, "暂未开通支付宝提款"),
    PAYMENT_TYPE_ERROR(1000009, "支付类型错误"),
    ORDER_HAS_EXPIRED(1000010, "订单已失效"),

    /**
     * 成功消息
     */
    LOGIN_SUCCESS(200, "登录成功"),
    LOGOUT_SUCCESS(200, "注销登录"),

    /**
     * 参数检验
     */
    NOT_PARAMS(000010, "参数不许为空"),
    MORE_THAN_PARAMS(000013, "传多参数"),

    /**
     * 接口出错
     */
    API_ERROR(111111, "API出错"),


    /**
     * 账号信息
     */
    USERNAME_NULL_ERROR(300001, "账号不存在"),
    USERNAME_OR_PASSWORD_ERROR(300005, "账号或密码错误"),
    USERNAME_UPDATE_ERROR(300009, "更新失败"),

    /**
     * token消息
     */
    TOKEN_TIME_OUT(500008, "token超时"),
    TOKEN_NULL_ERROR(500008, "token不能为空"),

    /**
     * 用户是否在线
     */
    OFF_LINE(600001, "未登录");
    /**
     * 返回码
     */
    private int code;
    /**
     * 返回中文结果描述
     */
    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
