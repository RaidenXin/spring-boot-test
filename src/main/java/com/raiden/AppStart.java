package com.raiden;

import com.raiden.annotation.EnableDisableService;
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
public class AppStart {

    public static final void main(String[] args){
        SpringApplication.run(AppStart.class, args);
    }
}
