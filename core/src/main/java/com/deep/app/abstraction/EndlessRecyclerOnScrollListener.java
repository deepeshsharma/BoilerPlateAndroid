package com.deep.app.abstraction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int currentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;

    protected EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleThreshold = 5;
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        boolean isListReset = false;
        if (loading) {
            if (totalItemCount > previousTotal || totalItemCount < previousTotal) {
                loading = false;
                isListReset = totalItemCount < previousTotal;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold) && totalItemCount >= 10) {
            // End has been reached
            //list has been invalidated hence need to reset the page counter
            if (isListReset) {
                currentPage = 1;
            }
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public void reset() {
        currentPage = 1;
    }

    public abstract void onLoadMore(int currentPage);
}
