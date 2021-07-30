package com.deep.app.abstraction;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by deepesh on 4/4/17.
 */

public abstract class BaseRecyclerWidget<T extends BaseWidget.IItemList<F>, F> extends BaseWidget<T> {

    protected RecyclerView recycleView;

    public BaseRecyclerWidget(Context context) {
        super(context);
    }

    public BaseRecyclerWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    * binding of views in recycler view
    * @
    */
    protected abstract void bind(RecyclerView.ViewHolder holder, F model, int pos);

    /*
    * creation of holder and view for a new row
    * @
    */
    protected abstract RecyclerView.ViewHolder createView(ViewGroup parent, int viewType);

    /*
    * click of a object in recycler view
    * @
    */
    protected abstract void clicked(int position, F models);

    /*
    * UI invalidate after data received
    * @
    */
    @Override
    protected void invalidateUi(T viewModel) {
        List<F> list = viewModel.getItems();
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter(list);
        recycleView.setAdapter(adapter);
    }

    protected class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<F> viewModelList;

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
            bind(h, viewModelList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
        }

        @Override
        public int getItemCount() {
            return viewModelList.size();
        }
    }

    /*
    * Holder class for recycler view holder
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
