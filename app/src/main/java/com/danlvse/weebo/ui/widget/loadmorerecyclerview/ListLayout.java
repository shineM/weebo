package com.danlvse.weebo.ui.widget.loadmorerecyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xinyuanzhong on 2017/1/15.
 */

public class ListLayout extends SwipeRefreshLayout {
    //默认触发加载更多事件的最小数量
    private static final int DEFAULT_COUNT_TRIGGER_LOAD_MORE_EVENT = 20;

    private RecyclerView mRecyclerView;

    private OnLoadMoreEvent mOnLoadMoreEvent;

    public void setmOnLoadMoreEvent(OnLoadMoreEvent mOnLoadMoreEvent) {
        this.mOnLoadMoreEvent = mOnLoadMoreEvent;
    }

    public ListLayout(Context context) {
        super(context);
        init();
    }

    public ListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mRecyclerView = new RecyclerView(getContext());

        //if layout manager is null,set default with LinearLayoutManager
        if (mRecyclerView.getLayoutManager() == null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mRecyclerView == null) {
            return;
        }

        //wrap adapter with HeaderAndFooterRecyclerViewAdapter for add footer view
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter =
                new HeaderAndFooterRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        super.setOnRefreshListener(listener);

    }

    public RecyclerView.OnScrollListener mOnScrollListener =
            new EndlessRecyclerOnScrollListener(Integer.valueOf(DEFAULT_COUNT_TRIGGER_LOAD_MORE_EVENT)) {
                @Override
                public void onLoadNextPage(View view) {
                    super.onLoadNextPage(view);
                    if (mOnLoadMoreEvent != null) {
                        mOnLoadMoreEvent.loadMore();
                    }
                }
            };
}
