package com.raiden.concurrent.listener;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 18:00 2021/2/15
 * @Modified By:
 */
 class DefaultThreadPoolListener implements ThreadPoolListener{

    private List<ThreadPoolListener> listeners;

    public DefaultThreadPoolListener(){}

    public DefaultThreadPoolListener(List<ThreadPoolListener> listeners){
        this.listeners = listeners;
    }

    public boolean addListener(ThreadPoolListener listener){
        if (listeners == null){
            listeners = new ArrayList<>();
        }
        if (listener == null){
            return false;
        }
        return listeners.add(listener);
    }

    public boolean addListeners(List<ThreadPoolListener> threadPoolListeners){
        if (listeners == null){
            listeners = new ArrayList<>();
        }
        if (threadPoolListeners == null){
            return false;
        }
        return listeners.addAll(threadPoolListeners);
    }

    @Override
    public void callback(ThreadPoolExecutor executor) {
        if (listeners != null){
            listeners.forEach(t -> t.callback(executor));
        }
    }
}
