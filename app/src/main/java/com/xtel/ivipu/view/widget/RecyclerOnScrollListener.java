package com.xtel.ivipu.view.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by vivhp on 2/9/2017.
 */

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD = 10;
    public static String TAG = RecyclerOnScrollListener.class.getSimpleName();
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
    private LinearLayoutManager mLinearLayoutManager;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    protected RecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onScrollUp();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onScrollDown();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }

        if (loading) {
            if (totalItemCount > previousTotal + 1) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            // Do something
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onScrollUp();

    public abstract void onScrollDown();

    public abstract void onLoadMore();
}
