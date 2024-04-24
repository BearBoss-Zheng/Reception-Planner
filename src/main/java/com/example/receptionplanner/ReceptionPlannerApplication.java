package com.example.receptionplanner;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.receptionplanner.mapper")
public class ReceptionPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReceptionPlannerApplication.class, args);
    }

}
