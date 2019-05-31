package com.yhh.springcloud.scloudconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ScloudConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScloudConfigApplication.class, args);
    }

}
