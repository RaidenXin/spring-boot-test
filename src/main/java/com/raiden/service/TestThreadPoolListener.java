package com.raiden.service;

import com.raiden.concurrent.listener.ThreadPoolListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 18:53 2021/2/15
 * @Modified By:
 */
@Component
@Slf4j
public class TestThreadPoolListener implements ThreadPoolListener {
    @Override
    public void callback(ThreadPoolExecutor executor) {
        log.error("线程池被打满!");
    }
}
