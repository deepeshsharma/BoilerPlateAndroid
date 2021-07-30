package com.deep.app.abstraction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Deepesh on 17-09-2017.
 */

public abstract class BaseSimpleFragment extends Fragment {

    private View viewBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewBinding = inflater.inflate(getLayoutId(), container, false);
        return viewBinding;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getScreenName();
        initView(viewBinding);
        onFragmentReady();
        apiCall();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View binding);

    protected abstract void apiCall();

    protected abstract String getScreenName();

    protected abstract void onFragmentReady();
}
