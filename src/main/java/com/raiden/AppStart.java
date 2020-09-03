package com.raiden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 21:41 2020/4/7
 * @Modified By:
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class AppStart {

    public static final void main(String[] args){
        SpringApplication.run(AppStart.class, args);
    }
}
