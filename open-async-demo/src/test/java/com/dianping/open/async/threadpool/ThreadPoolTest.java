/*
 *
 * Copyright (c) 2010-2015 by Shanghai HanHai Information Co., Ltd.
 * All rights reserved.
 *
 */

package com.dianping.open.async.threadpool;

import java.util.concurrent.*;

/*
 * Create Author  : shuang.he
 * Create Date    : 2015-09-14
 * Project        : rpc
 * File Name      : ThreadPoolTest.java
 */
public class ThreadPoolTest
{
    private static ThreadPool defaultThreadPool = new DefaultThreadPool("Check");

    private static ThreadPool defaultThreadPool2 = new DefaultThreadPool("haha", 2, 50,
            new LinkedBlockingQueue<Runnable>(50), new ThreadPoolExecutor.CallerRunsPolicy());//

    public static void testRunalbe()
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getId());
            }
        };
        defaultThreadPool2.execute(runnable);
    }

    public static void testCall() throws InterruptedException, ExecutionException, TimeoutException
    {
        Callable<String> callable = new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                System.out.println("begin call");
                Thread.sleep(3000);
                return "I'm callable ," + Thread.currentThread().getName();
            }
        };
        Future<String> getStr = defaultThreadPool2.submit(callable);
        System.out.println(getStr.get(1000, TimeUnit.MILLISECONDS));
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException
    {
       // testRunalbe();
        testCall();
    }
}
