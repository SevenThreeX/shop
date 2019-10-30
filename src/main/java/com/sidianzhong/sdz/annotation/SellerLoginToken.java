package com.sidianzhong.sdz.annotation;

import java.lang.annotation.*;

// 商家登录验证
    @Documented
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SellerLoginToken {
        boolean required() default true;
    }