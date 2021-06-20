package com.raiden.utils;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 16:26 2021/6/20
 * @Modified By:
 */
public final class WaitingUtil {

    public static final void waiting(long timeout){
        Object lock = new Object();
        synchronized (lock){
            try {
                lock.wait(timeout);
            } catch (InterruptedException e) {
            }
        }
    }
}
