package com.raiden.service;

import org.springframework.cache.CacheManager;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:10 2021/1/19
 * @Modified By:
 */
public class Cache {

    private CacheManager cacheManager;

    public Cache(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }

    public CacheManager getCacheManager(){
        return cacheManager;
    }
}
