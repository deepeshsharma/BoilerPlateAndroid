package com.deep.app.abstraction;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deep.app.R;
import com.deep.app.util.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by deepesh on 4/4/17.
 */

public abstract class BaseEndlessRecyclerWidget<T extends BaseWidget.IItemList<F>, F> extends BaseWidget<T> {

    protected RecyclerView recycleView;
    private BaseEndlessListener listener;
    private LinearLayout skelatonLayout;
    private ProgressBar footerProgress;
    private List<F> list = new ArrayList<>();
    private BaseRecyclerAdapter adapter;
    private EndlessRecyclerOnScrollListener endlessListener;
    private List<Integer> loadedPageIndexes = new ArrayList<>();

    public BaseEndlessRecyclerWidget(Context context) {
        super(context);
    }

    public BaseEndlessRecyclerWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     * Binding of view and object for recycler view
     * @
     */
    protected abstract void bind(RecyclerView.ViewHolder holder, F model, int pos);

    /*
     * comparator if need any type of sorting in the provided list
     * @
     */
    protected Comparator getComparator() {
        return null;
    }

    protected abstract int getViewType(int position);

    /*
     * creation of view and holder for new row in recycler view
     * @
     */
    protected abstract RecyclerView.ViewHolder createView(ViewGroup parent, int viewType);

    /*
     * click of a item in recycler view
     * Note: use it if necessary, currently the clicks are in the row item itself.
     */
    protected abstract void clicked(int position, F models);

    /*
     * used for auto scrolling data download and show
     * @
      */
    public void addOnScrollListener(BaseEndlessListener l) {
        this.listener = l;
    }

    /*
     * UI updation after data received
     * @
      */
    @Override
    protected void invalidateUi(T viewModel) {
        skelatonLayout.setVisibility(View.GONE);
        if (!loadedPageIndexes.contains(viewModel.getCurrentPage())) {
            list.addAll(viewModel.getItems());
            loadedPageIndexes.add(viewModel.getCurrentPage());
        }
        if (null != getComparator()) {
            Collections.sort(list, getComparator());
        }
        if (null == adapter) {
            adapter = new BaseRecyclerAdapter(list);
            recycleView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public List<F> getItems() {
        return list;
    }

    /*
     * initialization of views
     * @
      */
    @Override
    protected void initViews(View v) {
        skelatonLayout = findViewById(R.id.layout_skeleton);
        footerProgress = findViewById(R.id.progressBarLoading);
        recycleView = findViewById(R.id.recycler);
        final WrapContentLinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addOnScrollListener(endlessListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (null != listener) {
                    footerProgress.setVisibility(View.VISIBLE);
                    listener.onLoadMore(currentPage);
                }
            }
        });
    }

    /*
     * return layout of widget
     * @
      */
    @Override
    public int getLayoutId() {
        return R.layout.generic_recycler_view;
    }

    /*
     * refresh the widget
     * @
      */
    public void resetItems() {
        list.clear();
        loadedPageIndexes.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        adapter = null;
        endlessListener.reset();
        skelatonLayout.setVisibility(View.VISIBLE);
    }

    public void updateEmptyData() {
        skelatonLayout.setVisibility(View.GONE);
    }

    @Override
    protected T getItem() {
        return super.getItem();
    }

    /*
         * start or stop progress while data downloading for endless scrolling
         * @
         */
    public void showProgress(boolean b) {
        footerProgress.setVisibility(b ? VISIBLE : GONE);
    }

    public void refresh() {
        if (null != adapter) {
            adapter.notifyDataSetChanged();
        }
    }

    protected class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<F> viewModelList;
        private int lastPosition = -1;

        public BaseRecyclerAdapter(List<F> list) {
            viewModelList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return createView(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            WidgetHolder h = (WidgetHolder) holder;
            h.setItem(viewModelList.get(holder.getAdapterPosition()));
            if (position > lastPosition) {
                Animation alpha = new AlphaAnimation(0, 1);
                alpha.setDuration(300);
                alpha.setInterpolator(new DecelerateInterpolator());
                h.root.startAnimation(alpha);
                lastPosition = position;
            }
            bind(h, viewModelList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
        }

        @Override
        public int getItemCount() {
            return viewModelList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return getViewType(position);
        }
    }

    /*
    * Holder for Recycler view
    * @
    */
    protected class WidgetHolder extends RecyclerView.ViewHolder {

        View root;
        F item;

        public WidgetHolder(View view) {
            super(view);
            root = view;
            root.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != item) {
                        clicked(getAdapterPosition(), item);
                    }
                }
            });
        }

        public F getItem() {
            return item;
        }

        public void setItem(F item) {
            this.item = item;
        }
    }
}
