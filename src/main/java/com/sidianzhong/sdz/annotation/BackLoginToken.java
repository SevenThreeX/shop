package com.sidianzhong.sdz.annotation;

import java.lang.annotation.*;

// 后台登录验证
    @Documented
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BackLoginToken{
        boolean required() default true;
    }