package com.yhh.springcloud.scloudproduct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.yhh.springcloud.scloudproduct.mapper")
@EnableEurekaClient
@EnableDiscoveryClient
@EnableCircuitBreaker //提供对熔断的支持
public class ScloudProductApplicationHystrix {

    public static void main(String[] args) {
        SpringApplication.run(ScloudProductApplicationHystrix.class, args);
    }

}
