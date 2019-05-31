package com.yhh.springcloud.scloudconsumerturbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class ScloudConsumerTurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScloudConsumerTurbineApplication.class, args);
    }

}
