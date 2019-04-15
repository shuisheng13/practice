package com.yhh.practice.comsumerhystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class ComsumerHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComsumerHystrixDashboardApplication.class, args);
    }

}
