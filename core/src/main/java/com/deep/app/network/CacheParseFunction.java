package com.deep.app.network;

import com.deep.app.util.ParseUtil;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CacheParseFunction<T> implements Function<String, Observable<T>> {
    private Class<T> clazz;

    public CacheParseFunction(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Observable<T> apply(final
                               @NonNull
                                       String responseBodyResponse) throws Exception {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                T response = null;
                try {
                    response = ParseUtil.getObject(responseBodyResponse, clazz);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response != null) {
                    subscriber.onNext(response);
                }
                subscriber.onComplete();
            }
        }).subscribeOn(Schedulers.computation());
    }
}
