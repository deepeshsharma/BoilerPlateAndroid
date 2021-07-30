package com.deep.app.media;

import android.content.Context;
import android.widget.ImageView;

import com.deep.app.BuildConfig;
import com.deep.app.util.PicassoCircleTransform;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by Deepesh on 10-01-2018.
 */

public class ImageLoader implements IImageLoader {

    private static final int MB = 1024 * 1024;
    private static final int MEMORY_CACHE_SIZE = (int) (50 * MB);
    private static final int DISK_CACHE_SIZE = 50 * MB;
    private static final int CACHE_TIME = 60 * 60 * 24;
    private Picasso picasso;
    private LruCache memoryCache;
    private OkHttpClient client;

    public ImageLoader(Context context)
    {
        memoryCache = new LruCache(MEMORY_CACHE_SIZE);
        Picasso.Builder builder = new Picasso.Builder(context);
        picasso = builder.memoryCache(memoryCache).build();
        if(BuildConfig.DEBUG){
            picasso.setLoggingEnabled(true);
        }
    }

    @Override
    public void loadImage(Context ctx, String url, ImageView imageView) {
        picasso.load(url).into(imageView);
    }

    @Override
    public void loadCircleImage(Context ctx, String url, ImageView imageView) {
        picasso.load(url).transform(new PicassoCircleTransform()).into(imageView);

    }
}
