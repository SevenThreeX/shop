package com.sidianzhong.sdz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.sidianzhong.sdz.mapper")
public class ShopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(ShopApplication.class);
//    }

}
