package com.sidianzhong.sdz.annotation;

import java.lang.annotation.*;

//  用户登录验证
    @Documented
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UserLoginToken {
        boolean required() default true;
    }