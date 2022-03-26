package com.raiden.factory;

import com.raiden.config.CommonConfiguration;
import com.raiden.croe.SendMessageInovker;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 代理bean广场
 */
public class ProxyBeanFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceClass;

    private SendMessageInovker inovker;

    public ProxyBeanFactory(CommonConfiguration config){
        this.inovker = new SendMessageInovker(config);
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] {interfaceClass}, inovker);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
