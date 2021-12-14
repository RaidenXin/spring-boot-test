package com.raiden.croe;


import com.raiden.config.CommonConfiguration;
import com.raiden.model.Order;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 发送消息代理方法类
 */
public class SendMessageInovker implements InvocationHandler {

    private CommonConfiguration config;

    public SendMessageInovker(CommonConfiguration config){
        this.config = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return new Order("1111111111111111", config.getCommodityName(), config.getPrice());
    }
}
