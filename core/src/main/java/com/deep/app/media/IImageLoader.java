package com.deep.app.media;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Deepesh on 10-01-2018.
 */

public interface IImageLoader {

    void loadImage(Context ctx, String url, ImageView imageView);

    void loadCircleImage(Context ctx, String url, ImageView imageView);
}
