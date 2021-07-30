package com.deep.app.abstraction.abstraction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Deepesh Sharma on 6/26/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        trackScreen(screenName());
        initializeViews();
        onActivityReady();
    }

    public abstract int setLayoutId();

    public abstract void initializeViews();

    public abstract void onActivityReady();

    public abstract String screenName();

    private void trackScreen(String screenName) {

    }
}
