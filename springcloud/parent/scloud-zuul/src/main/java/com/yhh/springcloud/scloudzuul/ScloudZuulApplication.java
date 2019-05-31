package com.yhh.springcloud.scloudzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class ScloudZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScloudZuulApplication.class, args);
    }

}
