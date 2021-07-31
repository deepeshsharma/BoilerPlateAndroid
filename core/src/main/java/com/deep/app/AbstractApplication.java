package com.deep.app;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import com.deep.app.media.IImageLoader;
import com.deep.app.media.ImageLoader;
import com.deep.app.network.RetrofitClient;
import retrofit2.Retrofit;

public abstract class AbstractApplication extends MultiDexApplication {

    protected static IImageLoader imageLoader;
    private static Application app;
    private static Retrofit client;

    public static Application getApplication() {
        return app;
    }

    public static Retrofit getClient() {
        return client;
    }

    public static IImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        client = RetrofitClient.getClient(getApplicationContext(), getBaseUrl());
        imageLoader = new ImageLoader(getApplicationContext());
        getDeviceId();
    }
    public abstract void getDeviceId();
    public abstract String getBaseUrl();
}
