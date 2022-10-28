package com.myth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.myth.system.mysql.mapper","com.myth.system.mapper"})
public class MillAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(MillAdminApplication.class, args);
    }
}
