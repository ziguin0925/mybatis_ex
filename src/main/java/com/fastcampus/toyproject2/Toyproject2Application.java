package com.fastcampus.toyproject2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class Toyproject2Application {

    public static void main(String[] args) {
        SpringApplication.run(Toyproject2Application.class, args);
    }

}