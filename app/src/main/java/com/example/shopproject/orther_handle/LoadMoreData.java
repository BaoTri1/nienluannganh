package com.example.shopproject.orther_handle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class LoadMoreData implements NestedScrollView.OnScrollChangeListener {

    private SwipeRefreshLayout swipeRefreshLayout;

    public LoadMoreData(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == 0) {
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }

        if(isLoading() || isLeastPage())
            return;

        if(scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
            loadMoreItem();
        }
    }

    public abstract void loadMoreItem();
    public abstract boolean isLoading();
    public abstract boolean isLeastPage();
}
