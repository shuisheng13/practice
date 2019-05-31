package com.yhh.springcloud.scloudeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ScloudEurekaApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(ScloudEurekaApplication2.class, args);
    }

}
