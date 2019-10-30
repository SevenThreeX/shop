package com.sidianzhong.sdz.utils;

/**
 * @description: 状态统一处理枚举类
 * @author: SY_zheng
 * @create: 2019-05-05
 */
public enum MessageEnums {

    /**
     * 成功消息
     */
    LOGIN_SUCCESS("000000", "登录成功"),
    LOGOUT_SUCCESS("000000", "注销登录"),

    /**
     * 参数检验
     */
    NOT_PARAMS("000010", "参数不许为空"),
    MORE_THAN_PARAMS("000013", "传多参数"),

    /**
     * 接口出错
     */
    API_ERROR("111111", "API出错"),


    /**
     * 账号信息
     */
    USERNAME_NULL_ERROR("300001", "账号不存在"),
    USERNAME_OR_PASSWORD_ERROR("300005", "账号或密码错误"),
    USERNAME_UPDATE_ERROR("300009", "更新失败"),

    /**
     * token消息
     */
    TOKEN_ERROR("500004", "无效token"),
    TOKEN_TIME_OUT("500008", "token超时"),
    TOKEN_NULL_ERROR("500008", "token不能为空"),

    /**
     * 用户是否在线
     */
    OFF_LINE("600001", "未登录")
    ;

    private final String code;
    private final String desc;

    MessageEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }


}