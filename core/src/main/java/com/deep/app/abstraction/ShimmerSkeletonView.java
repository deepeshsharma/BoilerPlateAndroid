package com.deep.app.abstraction;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

public class ShimmerSkeletonView extends FrameLayout implements Runnable {
    private static int lineWidth;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mLineBound;
    private Bitmap mBitmap;
    private boolean isRunning;
    private Paint mPaint;
    private int sleepTime = 15;

    public ShimmerSkeletonView(Context context) {
        super(context);
        init();
    }

    public ShimmerSkeletonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShimmerSkeletonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShimmerSkeletonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mLineBound = new Rect();
        lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG + Paint.DITHER_FLAG + Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!changed) {
            return;
        }
        mLineBound.set(0, 0, lineWidth, bottom - top);
        if (mLineBound.width() <= 0 || mLineBound.height() <= 0) {
            return;
        }
        mBitmap = Bitmap.createBitmap(mLineBound.width(), mLineBound.height(), Bitmap.Config.ARGB_8888);
        if (null != mBitmap) {
            Canvas canvas = new Canvas(mBitmap);

            paint.setShader(new LinearGradient(0, 0, mLineBound.width(), 0,
                    new int[]{Color.argb(10, 255, 255, 255), Color.argb(150, 255, 255, 255), Color.argb(10, 255,
                            255,
                            255)},
                    new float[]{0, 0.5f, 1}, Shader.TileMode.REPEAT));
            canvas.drawRect(mLineBound, paint);
        }
    }

    public void startAnim() {
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    /* set Duration of animation
    *  please give the sleep time greater then 0 and less then 30
    */
    public void setDutation(int time) {
        sleepTime = time;
    }
    /* to stop the animation */

    public void stopAnim() {
        isRunning = false;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean b = super.drawChild(canvas, child, drawingTime);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, null, mLineBound, mPaint);
        }
        return b;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {

                if (mLineBound.left < getWidth()) {
                    mLineBound.offset(mLineBound.width() / 20, 0);
                } else {
                    mLineBound.set(0, 0, lineWidth, getHeight());
                }
                postInvalidate();
                Thread.sleep(sleepTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
