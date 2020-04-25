package com.raiden.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Service;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 16:46 2020/4/25
 * @Modified By:
 */
@Service
@Slf4j
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    public void clearCache(String key){
        ConcurrentMapCache cache = (ConcurrentMapCache) cacheManager.getCache("cacheTest");
        cache.clear();
    }
}
