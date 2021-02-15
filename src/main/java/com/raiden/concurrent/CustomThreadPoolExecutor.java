package com.raiden.concurrent;

import com.raiden.concurrent.listener.ThreadPoolListener;

import java.util.concurrent.*;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 13:19 2021/2/15
 * @Modified By:
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    private ThreadPoolListener listener;

    public CustomThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor,ThreadPoolListener listener,TimeUnit unit){
        this(threadPoolExecutor, unit);
        this.listener = listener;
    }

    public CustomThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor,TimeUnit unit){
        this(threadPoolExecutor.getCorePoolSize(), threadPoolExecutor.getMaximumPoolSize(), threadPoolExecutor.getKeepAliveTime(unit), unit, threadPoolExecutor.getQueue(), threadPoolExecutor.getThreadFactory(), threadPoolExecutor.getRejectedExecutionHandler());
    }

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public void execute(Runnable command) {
        BlockingQueue<Runnable> queue = super.getQueue();
        if (!queue.isEmpty() && listener != null){
            listener.callback();
        }
        super.execute(command);
    }

}
