package com.bassem.catfacts.ui.controls;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


/**
 * Created by mab on 11/13/17.
 * A recycler  view that has a listener to notify if it reached the last item so this way a new page from api or whatever can be done
 */

public class LoadMoreRecyclerView extends RecyclerView {
    private LinearLayoutManager mLinearLayoutManager;
    private OnScrolledToEndListener mOnScrolledToEndListener;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        initialize();
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {

        this.addOnScrollListener(mOnScrollListener);
    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mLinearLayoutManager == null && getLayoutManager() != null) {
                if (getLayoutManager() instanceof LinearLayoutManager) {
                    mLinearLayoutManager = (LinearLayoutManager) getLayoutManager();
                }
            }
            if (mLinearLayoutManager != null) {
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    if (mOnScrolledToEndListener != null) {
                        mOnScrolledToEndListener.reachedEnd();
                    }
                }
            }
        }
    };

    public void setOnScrolledToEndListener(OnScrolledToEndListener listener) {
        this.mOnScrolledToEndListener = listener;
    }

    public interface OnScrolledToEndListener {
        void reachedEnd();
    }

}