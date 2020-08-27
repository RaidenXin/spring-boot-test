package com.raiden.service;

import com.raiden.config.Config;
import com.raiden.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:12 2020/4/7
 * @Modified By:
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private Config config;

    @Cacheable(value="cacheTest", key="'CacheKey:'+#orderId")
    public Order getOrder(String orderId) {
        log.error("getOrder start");
        log.error("key:CacheKey:" + orderId);
        String menberId = "111";
        Order order = new Order(menberId, config.getCommodityName(), config.getPrice());
        log.error("getOrder end");
        return order;
    }

    public String getUser(String id, String name, String age, String sex){
        return "hello word";
    }
}
