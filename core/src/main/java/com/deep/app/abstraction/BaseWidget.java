package com.deep.app.abstraction;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.deep.app.R;

import java.util.List;

/**
 * Created by deepesh on 4/4/17.
 */

public abstract class BaseWidget<T> extends LinearLayout {

    protected View view;
    protected TypedArray attributes = null;
    private T viewModel;

    public BaseWidget(Context context) {
        super(context);
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            view = inflate(context, layoutId, this);
        }
        initViews(view);
    }

    public BaseWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            view = inflate(context, layoutId, this);
        }
        attributes = getContext().obtainStyledAttributes(attrs, R.styleable.widgets);
        initViews(view);
    }

    /*
     * Used to set the layout for inflator
     * @
     */
    public abstract int getLayoutId();

    /*
     * initialization of views and binding
     * @
     */
    protected abstract void initViews(View v);

    /*
     * get data from widget
     * @
     */
    protected T getItem() {
        return viewModel;
    }

    /*
     * set data in widget
     * @
     */
    public void setItem(T viewModel) {
        this.viewModel = viewModel;
        invalidateUi(viewModel);
    }

    /*
     * update UI after setItem(T viewModel) called
     * @
     */
    protected abstract void invalidateUi(T viewModel);

    /*
     * return view of widget
     * @
     */
    public View getView() {
        return view;
    }

    public interface IItemList<F> {
        List<F> getItems();

        int getCurrentPage();

        void setCurrentPage(int page);
    }
}
