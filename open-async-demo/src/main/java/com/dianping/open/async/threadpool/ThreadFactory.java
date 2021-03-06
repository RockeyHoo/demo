package com.dianping.open.async.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactory implements java.util.concurrent.ThreadFactory
{

    static final AtomicInteger poolNumber = new AtomicInteger(1);

    final AtomicInteger threadNumber;

    final ThreadGroup group;

    final String namePrefix;

    boolean isDaemon = true;

    public ThreadFactory()
    {
        this("Default-Pool");
    }

    public ThreadFactory(String name)
    {
        this(name, true);
    }

    public ThreadFactory(String preffix, boolean daemon)
    {
        this.threadNumber = new AtomicInteger(1);
        this.group = new ThreadGroup(preffix + "-" + poolNumber.getAndIncrement() + "-threadGroup");
        this.namePrefix = preffix + "-" + poolNumber.getAndIncrement() + "-thread-";
        this.isDaemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r)
    {
        Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(),
                -3715992351445876736L);
        t.setDaemon(this.isDaemon);
        if (t.getPriority() != 5)
        {
            t.setPriority(5);
        }
        return t;
    }

    /**
     * @return the group
     */
    public ThreadGroup getGroup()
    {
        return group;
    }

}
