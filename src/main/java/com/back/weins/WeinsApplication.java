package com.back.weins;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value="com.back.weins.Dao")
public class WeinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeinsApplication.class, args);
    }

}
