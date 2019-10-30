package com.sidianzhong.sdz.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description: 构建网络传输的类
 * @author: SY_zheng
 * @create: 2019-04-09
 */
@ToString
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageVO<T> implements Serializable{


    private static final long serialVersionUID = 7366217171681294440L;
    private int code;
    private String message;
    private T data;

    private MessageVO(Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.data = (T)builder.data;
    }


    /**
     * 由于redis序列换是JDK序列，所以要无参构造函数给JSON调用，解决不能序列化问题
     */
    public MessageVO() {
    }

    /**
     * 使用build模式，较少重复代码
     * @return
     */

    public static MessageVO.Builder builder(){
        return new Builder();
    }

    public static <T>MessageVO.Builder builder(T data){
        Builder<T> builder = new Builder<>();
        builder.data(data);
        return builder;
    }



    public static class Builder<T> {
        private int code;
        private String message;
        private T data;

        public Builder msgCode(ResultStatus messageEnums) {
            this.message = messageEnums.getMessage();
            this.code = messageEnums.getCode();
            return this;
        }

        public Builder msgCodes(JSONObject body) {
            this.code =body.getInteger("code");
            this.message =body.getString("message");
            this.data = (T) body.get("data");
            return this;
        }

        public MessageVO build() {
            return new MessageVO(this);
        }

        /**
         * 不在对外提供，解决泛型在builder模式底下有warning
         * @param data
         * @return
         */
        private Builder<T> data(T data) {
            this.data = data;
            return this;
        }

    }

    public static void main(String[] args) {

        System.err.println(MessageVO.builder("123213")
                .msgCode(ResultStatus.API_ERROR)
                .build().toString());
    }

}