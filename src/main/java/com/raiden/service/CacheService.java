package com.raiden.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 16:46 2020/4/25
 * @Modified By:
 */
@Slf4j
public class CacheService {

    private CacheManager cacheManager;

    public CacheService(Cache cache, CacheManager cacheManager){
        if (cache == null){
            this.cacheManager = cacheManager;
        }else {
            this.cacheManager = cache.getCacheManager();
        }
    }

    public void clearCache(String key){
        ConcurrentMapCache concurrentMapCache = (ConcurrentMapCache) cacheManager.getCache("cacheTest");
        concurrentMapCache.clear();
    }
}
