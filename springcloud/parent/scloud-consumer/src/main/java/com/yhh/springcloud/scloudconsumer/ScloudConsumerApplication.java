package com.yhh.springcloud.scloudconsumer;

import com.yhh.springcloud.ribbon.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name ="SCLOUD-PRODUCT" ,configuration = RibbonConfig.class)
/*
@RibbonClients{(name ="SCLOUD-PRODUCT" ,configuration = RibbonConfig.class),
        (name ="SCLOUD-PRODUCT" ,configuration = RibbonConfig.class)}
*/
public class ScloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScloudConsumerApplication.class, args);
    }

}
