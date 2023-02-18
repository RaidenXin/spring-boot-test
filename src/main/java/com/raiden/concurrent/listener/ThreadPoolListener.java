package com.raiden.concurrent.listener;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 17:58 2021/2/15
 * @Modified By:
 */
public interface ThreadPoolListener {
    void callback(ThreadPoolExecutor executor);

    public static ThreadPoolListener getDefaultInstance(List<ThreadPoolListener> listeners){
        return new DefaultThreadPoolListener(listeners);
    }
}
