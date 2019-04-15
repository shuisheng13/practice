package com.yhh.comsumer;

import com.yhh.rule.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@RibbonClient(name ="article" ,configuration = RibbonConfig.class)
public class ComsumerRabbionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComsumerRabbionApplication.class, args);
    }

}
