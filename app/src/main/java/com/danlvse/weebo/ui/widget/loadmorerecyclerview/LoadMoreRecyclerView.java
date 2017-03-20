package com.danlvse.weebo.ui.widget.loadmorerecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xinyuanzhong on 16/9/6.
 */
public class LoadMoreRecyclerView extends RecyclerView {
    private OnLoadMoreEvent onLoadMoreEvent;

    private Context mContext;

    //总数少于此数值就不显示底部布局
    private int mPageCount = 20;

    private View customEmptyView;

    public void setShowEmptyViewWhenFewData(boolean showEmptyViewWhenFewData) {
        if (showEmptyViewWhenFewData) {
            this.mPageCount = 1;
        }
    }

    //总数少于此数值上拉就不会触发加载更多事件
    private int countToTrigger = 20;

    public void setCountToTrigger(int countToTrigger) {
        this.countToTrigger = countToTrigger;
        removeOnScrollListener(mOnScrollListener);
        addOnScrollListener(mOnScrollListener);
    }

    /**
     * 触发加载更多后的事件,一般为分页请求网络数据
     *
     * @param onLoadMoreEvent
     */
    public void setOnLoadMoreEvent(OnLoadMoreEvent onLoadMoreEvent) {
        this.onLoadMoreEvent = onLoadMoreEvent;
    }

    private static final int DEFAULT_RESULT_COUNT = 20;


    public void setOnScrollListener(OnScrollListener mOnScrollListener) {
        removeOnScrollListener(this.mOnScrollListener);
        this.mOnScrollListener = mOnScrollListener;
        addOnScrollListener(mOnScrollListener);
    }

    public RecyclerView.OnScrollListener mOnScrollListener =
            new EndlessRecyclerOnScrollListener(Integer.valueOf(countToTrigger)) {
                @Override
                public void onLoadNextPage(View view) {
                    super.onLoadNextPage(view);
                    if (onLoadMoreEvent != null) {
                        onLoadMoreEvent.loadMore();
                    }
                }
            };

    public LoadMoreRecyclerView(Context context) {
        super(context);
        mContext = context;
        addOnScrollListener(mOnScrollListener);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        addOnScrollListener(mOnScrollListener);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        addOnScrollListener(mOnScrollListener);
    }

    /**
     * 设置加载更多状态为网络错误
     */
    public void setLoadMoreResultNetworkError(View.OnClickListener onClickListener) {
        setFooterViewState(LoadingFooter.State.NetWorkError, onClickListener, true);
    }

    /**
     * 设置加载更多状态为没有更多数据
     */
    public void setLoadMoreResultNoMoreData() {
        setFooterViewState(LoadingFooter.State.TheEnd, null, true);
    }

    public void showEmptyViewWithoutScroll() {
        setFooterViewState(LoadingFooter.State.TheEnd, null, false);
    }

    /**
     * 设置加载更多状态为正在加载
     */
    public void setLoadMoreStateLoading() {
        setFooterViewState(LoadingFooter.State.Loading, null, true);
    }

    /**
     * 设置加载更多状态为完成
     */
    public void setLoadMoreResultCompleted() {
        setFooterViewState(LoadingFooter.State.Normal, null, false);
    }

    /**
     * 不滑动到底部隐藏Footer
     */
    public void resetLoadMoreState() {
        setFooterViewState(LoadingFooter.State.Normal, null, false);
    }

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     * @param scrollToEnd   是否滑到底部
     */
    public void setFooterViewState(LoadingFooter.State state, View.OnClickListener errorListener, boolean scrollToEnd) {

        if (mContext == null) {
            return;
        }

        RecyclerView.Adapter outerAdapter = getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;

        //只有一页的时候，就别加什么FooterView了
        if (headerAndFooterAdapter.getInnerAdapter().getItemCount() < mPageCount) {
            return;
        }

        LoadingFooter footerView;

        //已经有footerView了
        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();
            if (customEmptyView != null) {
                footerView.setEmptyView(customEmptyView);
            }
            footerView.setState(state);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }
            if (scrollToEnd) {
                scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
            }
        } else {
            footerView = new LoadingFooter(mContext);
            if (customEmptyView != null) {
                footerView.setEmptyView(customEmptyView);
            }
            footerView.setState(state);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

            headerAndFooterAdapter.addFooterView(footerView);
            if (scrollToEnd) {
                scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
            }
        }
    }


    /**
     * 获取当前RecyclerView.FooterView的状态
     */
    public LoadingFooter.State getFooterViewState() {
        LoadingFooter footer = getCurrentFooterView();
        if (footer == null) {
            return LoadingFooter.State.Normal;
        } else {
            return footer.getState();
        }
    }

    private LoadingFooter getCurrentFooterView() {
        RecyclerView.Adapter outerAdapter = getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooter footerView = (LoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                return footerView;
            }
        }
        return null;
    }

    public void setPageCount(int pageCount) {
        this.mPageCount = pageCount;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public void setEmptyView(View view) {
        customEmptyView = view;
    }
}
