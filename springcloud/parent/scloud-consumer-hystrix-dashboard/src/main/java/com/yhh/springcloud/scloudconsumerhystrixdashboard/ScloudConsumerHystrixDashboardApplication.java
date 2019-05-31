package com.yhh.springcloud.scloudconsumerhystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class ScloudConsumerHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScloudConsumerHystrixDashboardApplication.class, args);
    }

}
