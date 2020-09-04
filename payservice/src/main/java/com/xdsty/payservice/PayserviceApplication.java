package com.xdsty.payservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.xdsty.payservice.mapper"})
public class PayserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayserviceApplication.class, args);
    }

}
