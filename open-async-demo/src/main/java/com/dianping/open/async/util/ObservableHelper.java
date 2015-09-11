package com.dianping.open.async.util;

import com.dianping.open.async.exception.OnPurposeError;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

@Component
public class ObservableHelper
{
    private static final int retryCount = 2;     //lion 可调

    private static final long retryInterval = 500;  //500毫秒

    public static <T> Observable<T> createImmediateObservable(final Func0<T> func)
    {
        return Observable.create(new Observable.OnSubscribe<T>()
        {
            @Override
            public void call(Subscriber<? super T> subscriber)
            {
                try
                {
                    subscriber.onNext(func.call());
                    subscriber.onCompleted();
                }
                catch (Throwable e)
                {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.immediate()).cache();
    }

    public <T> Observable<T> createObservable(final Func0<T> func)
    {
        return createObservable(func, retryCount);
    }

    public <T> Observable<T> createObservable(final Func0<T> func, int count)
    {
        return retry(Observable.create(new Observable.OnSubscribe<T>()
        {
            @Override
            public void call(Subscriber<? super T> subscriber)
            {
                try
                {
                    subscriber.onNext(func.call());
                    subscriber.onCompleted();
                }
                catch (Throwable e)
                {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()), count, retryInterval).cache();
    }

    public static <T> Observable<T> retry(final Observable<T> observable, final int count, final long millis)
    {
        return Observable.create(new Observable.OnSubscribe<T>()
        {
            @Override
            public void call(final Subscriber<? super T> subscriber)
            {
                observable.subscribe(new Subscriber<T>()
                {
                    @Override
                    public void onCompleted()
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        if (count > 0 && !(e instanceof OnPurposeError))
                        {
                            Observable.just(observable)
                                    .delay(millis, TimeUnit.MILLISECONDS)
                                    .subscribe(new Action1<Observable<T>>()
                                    {
                                        @Override
                                        public void call(Observable<T> ob)
                                        {
                                            retry(ob, count - 1, millis).subscribe(subscriber);
                                        }
                                    });
                        }
                        else
                        {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void onNext(T t)
                    {
                        subscriber.onNext(t);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }
}
