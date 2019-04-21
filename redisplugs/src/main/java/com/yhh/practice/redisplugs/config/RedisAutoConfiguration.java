package com.yhh.practice.redisplugs.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
@ConditionalOnClass(Jedis.class)
@EnableConfigurationProperties(RedisProperties.class)//开启使用映射实体对象
@ConditionalOnProperty//存在对应配置信息时初始化该配置类
        (
                prefix = "redis",//存在配置前缀redis
                value = "enabled",//开启
                matchIfMissing = true//缺失检查
        )

public class RedisAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public Jedis jedis(RedisProperties redisProperties){
        return new Jedis(redisProperties.getHost(), redisProperties.getPort());
    }

}
