package com.raiden.config;

import com.raiden.service.Cache;
import com.raiden.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:04 2021/1/19
 * @Modified By:
 */
@Configuration
public class CacheConfig {


    @Bean
    public CacheService cacheService(@Autowired(required = false)@Qualifier("cache") Cache cache, @Autowired(required = false)CacheManager cacheManager){
        return new CacheService(cache, cacheManager);
    }
}
