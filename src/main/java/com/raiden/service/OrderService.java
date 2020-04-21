package com.raiden.service;

import com.raiden.config.Config;
import com.raiden.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:12 2020/4/7
 * @Modified By:
 */
@Service
public class OrderService {

    @Autowired
    private Config config;

    public Order getOrder(String orderId) {
        String menberId = "111";
        Order order = new Order(menberId, config.getCommodityName(), config.getPrice());
        return order;
    }
}
