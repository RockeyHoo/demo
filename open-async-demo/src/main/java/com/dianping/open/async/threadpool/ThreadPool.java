package com.dianping.open.async.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public interface ThreadPool {

	public void execute(Runnable run);

	public <T> Future<T> submit(Callable<T> call);

	public Future<?> submit(Runnable run);

	public ThreadPoolExecutor getExecutor();

}
