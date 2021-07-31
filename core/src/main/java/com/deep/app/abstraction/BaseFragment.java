package com.deep.app.abstraction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Deepesh on 17-09-2017.
 */

public abstract class BaseFragment extends Fragment {

    private View viewBinding;
    //private FirebaseAnalytics analytics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewBinding = inflater.inflate(getLayoutId(), container, false);
        return viewBinding;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //analytics = FirebaseAnalytics.getInstance(getActivity().getApplicationContext());
        initView(viewBinding);
        onFragmentReady();
        apiCall();
        trackScreen();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View binding);

    protected abstract void apiCall();

    protected abstract String getScreenName();

    protected abstract void onFragmentReady();

    private void trackScreen() {
        String name = getScreenName();
        if (!TextUtils.isEmpty(name)) {
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            //analytics.logEvent("screen", bundle);
            //Analytics.getInstance().pushScreen(name);
        }
    }

    protected void trackEvent(String category, String event, String label) {
        Bundle bundle = new Bundle();
        bundle.putString("event", event);
        bundle.putString("label", label);
        //analytics.logEvent(category, bundle);
        //Analytics.getInstance().pushEvent(category, event, label);
    }

    protected void trackUserAttribute(String name, String phone, String userDevice, String userId, String token) {
        //Analytics.getInstance().pushUserAttribute(name, phone, userDevice, userId, token);
    }
}
