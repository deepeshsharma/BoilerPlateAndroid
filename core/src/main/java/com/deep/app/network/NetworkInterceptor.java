package com.deep.app.network;

import android.content.Context;

import com.deep.app.util.NetworkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Deepesh on 31-12-2017.
 */

public class NetworkInterceptor implements Interceptor {

    private Context mContext;

    public NetworkInterceptor(Context ctx) {
        mContext = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (NetworkUtil.isOnline(mContext)) {
            int maxAge = 60; // read from cache for 1 minute
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
