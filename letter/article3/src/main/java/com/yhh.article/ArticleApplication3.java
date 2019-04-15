package com.yhh.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.yhh.article.mapper")
@EnableEurekaClient
@EnableDiscoveryClient
public class ArticleApplication3 {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication3.class, args);
    }

}
