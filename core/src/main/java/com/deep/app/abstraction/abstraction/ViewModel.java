package com.deep.app.abstraction.abstraction;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import com.deep.app.util.PicassoCircleTransform;
import com.squareup.picasso.Picasso;

/**
 * Created by deepesh on 17/8/17.
 */

public class ViewModel extends BaseObservable {
    @BindingAdapter({"bind:image"})
    public static void loadImageCircle(ImageView view, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            /*Glide.with(view.getContext()).load(imageUrl)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(view.getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);*/
            Picasso.with(view.getContext()).load(imageUrl).transform(new PicassoCircleTransform()).into(view);
        }
    }

    @BindingAdapter({"bind:imageNotCircle"})
    public static void loadImage(ImageView view, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            /*Glide.with(view.getContext()).load(imageUrl)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(view.getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);*/
            Picasso.with(view.getContext()).load(imageUrl).into(view);
        }
    }
}
