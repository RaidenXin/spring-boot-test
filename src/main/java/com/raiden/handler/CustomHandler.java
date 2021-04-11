package com.raiden.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 0:21 2020/12/31
 * @Modified By:
 */
@Component
public class CustomHandler implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(1111);
    }

}
