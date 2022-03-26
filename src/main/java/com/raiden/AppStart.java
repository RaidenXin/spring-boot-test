package com.raiden;

import com.raiden.annotation.EnableDisableService;
import com.raiden.redis.current.limiter.annotation.EnableRedisCurrentLimiter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 21:41 2020/4/7
 * @Modified By:
 */
@SpringBootApplication
@EnableCaching
@EnableDisableService
@EnableDiscoveryClient
@MapperScan("com.raiden.mapper")
@EnableRedisCurrentLimiter
public class AppStart {

    public static final void main(String[] args){
        SpringApplication.run(AppStart.class, args);
    }
}
