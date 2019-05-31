package com.yhh.springcloud.scloudconsumer;

import com.yhh.springcloud.ribbon.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name ="SCLOUD-PRODUCT" ,configuration = RibbonConfig.class)
@EnableFeignClients("com.yhh.springcloud.scloudservice.service")
public class ScloudConsumerApplicationHystrix {

    public static void main(String[] args) {
        SpringApplication.run(ScloudConsumerApplicationHystrix.class, args);
    }

}
