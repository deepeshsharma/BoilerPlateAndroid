package com.deep.app.network;

import com.deep.app.network.cache.CacheManager;
import com.deep.app.util.ParseUtil;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CacheUpdateFunction<T> implements Function<Response<ResponseBody>, Observable<T>> {
    private Class<T> clazz;
    private String url;
    private boolean isSaved;

    public CacheUpdateFunction(String url, Class<T> clazz) {
        this.url = url;
        this.clazz = clazz;
    }

    @Override
    public Observable<T> apply(final
                               @NonNull
                                       Response<ResponseBody> networkResponse) throws Exception {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                T response = null;
                ResponseBody body = null;
                try {
                    body = networkResponse.body();
                    if (body != null) {
                        String str = body.string();
                        isSaved = CacheManager.getInstance().saveData(url, str);
                        response = ParseUtil.getObject(str, clazz);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (body != null) {
                        body.close();
                    }
                }
                if (response == null) {
                    subscriber.onError(new RuntimeException("Network response is null."));
                } else if (isSaved) {
                    subscriber.onNext(response);
                }
                subscriber.onComplete();
            }
        }).subscribeOn(Schedulers.computation());
    }
}