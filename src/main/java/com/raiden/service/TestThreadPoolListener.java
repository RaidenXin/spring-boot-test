package com.raiden.service;

import com.raiden.concurrent.listener.ThreadPoolListener;
import org.springframework.stereotype.Component;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 18:53 2021/2/15
 * @Modified By:
 */
@Component
public class TestThreadPoolListener implements ThreadPoolListener {
    @Override
    public void callback() {
        System.err.println("class:" + this.getClass().getName());
    }
}
