package com.dianping.open.async.threadpool;

import com.dianping.pigeon.threadpool.DefaultThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

public class DefaultThreadPool implements ThreadPool
{

    private String name;

    private ThreadPoolExecutor executor;

    private DefaultThreadFactory factory;

    public DefaultThreadPool(String poolName)
    {
        this.name = poolName;
        this.executor = (ThreadPoolExecutor) Executors.newCachedThreadPool(new DefaultThreadFactory(poolName));
    }

    public DefaultThreadPool(String poolName, int corePoolSize, int maximumPoolSize)
    {
        this(poolName, corePoolSize, maximumPoolSize, new SynchronousQueue<Runnable>());
    }

    public DefaultThreadPool(String poolName, int corePoolSize, int maximumPoolSize, BlockingQueue<Runnable> workQueue)
    {
        this(poolName, corePoolSize, maximumPoolSize, workQueue, new AbortPolicy());
    }

    public DefaultThreadPool(String poolName, int corePoolSize, int maximumPoolSize, BlockingQueue<Runnable> workQueue,
                             RejectedExecutionHandler handler)
    {
        this.name = poolName;
        this.factory = new DefaultThreadFactory(this.name);
        this.executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS, workQueue,
                this.factory, handler);
    }

    public void execute(Runnable run)
    {
        this.executor.execute(run);
    }

    public <T> Future<T> submit(Callable<T> call)
    {
        return this.executor.submit(call);
    }

    public Future<?> submit(Runnable run)
    {
        return this.executor.submit(run);
    }

    public ThreadPoolExecutor getExecutor()
    {
        return this.executor;
    }

}
