package com.deep.app.abstraction;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.deep.app.analytics.Analytics;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Deepesh on 17-09-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static final String TITLE = "title";
    private ViewDataBinding viewBinding;
    private ProgressDialog progress;
    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytics = FirebaseAnalytics.getInstance(getApplicationContext());
        preSetContentView();
        viewBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initView(viewBinding);
        onActivityReady();
        apiCall();
        trackScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void trackScreen() {
        String name = getScreenName();
        if (!TextUtils.isEmpty(name)) {
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            analytics.logEvent("screen", bundle);
            Analytics.getInstance().pushScreen(name);
        }
    }

    protected void trackEvent(String category, String action, String label) {
        Bundle bundle = new Bundle();
        bundle.putString("event", action);
        bundle.putString("label", label);
        analytics.logEvent(category, bundle);
        Analytics.getInstance().pushEvent(category, action, label);
    }

    protected void trackAttribute(String name, String phone, String userDevice, String userId, String token) {
        Analytics.getInstance().pushUserAttribute(name, phone, userDevice, userId, token);
    }

    protected abstract void preSetContentView();

    protected abstract int getLayoutId();

    protected abstract void initView(ViewDataBinding binding);

    protected abstract void apiCall();

    protected abstract String getScreenName();

    protected abstract void onActivityReady();

    public void startProgress() {
        progress = ProgressDialog.show(BaseActivity.this, "", "");
    }

    public void stopProgress() {
        if (null != progress) {
            progress.dismiss();
            progress = null;
        }
    }


}
