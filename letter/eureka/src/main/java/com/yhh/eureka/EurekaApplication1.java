package com.yhh.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication1 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication1.class, args);
    }

}

