package com.danlvse.weebo.ui.widget.loadmorerecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqyxjy.common.R;
import com.pnikosis.materialishprogress.ProgressWheel;


/**
 * Created by zhong on 2016/4/9.
 * <p>
 * ListView/GridView/RecyclerView 分页加载时使用到的FooterView
 */
public class LoadingFooter extends RelativeLayout {

    protected State mState = State.Normal;
    private View mLoadingView;
    private View mNetworkErrorView;
    private View mTheEndView;
    private ProgressWheel mLoadingProgress;
    private TextView mLoadingText;

    private OnClickListener onEmptyViewClickListener;
    private View mCustomEmptyView;

    public LoadingFooter(Context context) {
        super(context);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        inflate(context, R.layout.common_list_footer, this);
        setOnClickListener(null);

        setState(State.Normal, true);
    }

    public State getState() {
        return mState;
    }

    public void setState(State status) {
        setState(status, true);
    }

    /**
     * 设置状态
     *
     * @param status
     * @param showView 是否展示当前View
     */
    public void setState(State status, boolean showView) {
        if (mState == status) {
            return;
        }
        mState = status;

        switch (status) {

            case Normal:
                setOnClickListener(null);
                hideLoadingView();
                hideEndView();
                hideErrorView();

                break;
            case Loading:
                setOnClickListener(null);
                hideEndView();
                hideErrorView();
                hideLoadingView();
                if (mLoadingView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
                    mLoadingView = viewStub.inflate();

                    mLoadingProgress = (ProgressWheel) mLoadingView.findViewById(R.id.loading_progress);
                    mLoadingText = (TextView) mLoadingView.findViewById(R.id.loading_text);
                } else {
                    mLoadingView.setVisibility(VISIBLE);
                }

                mLoadingView.setVisibility(showView ? VISIBLE : GONE);

                mLoadingProgress.setVisibility(View.VISIBLE);
                mLoadingText.setText(R.string.list_footer_loading);
                break;
            case TheEnd:
                setOnClickListener(null);
                hideLoadingView();
                hideEndView();
                hideErrorView();

                if (mTheEndView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewstub);

                    if (mCustomEmptyView != null) {
                        viewStub.inflate().setVisibility(GONE);

                        mTheEndView = mCustomEmptyView;
                        addView(mTheEndView);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTheEndView.getLayoutParams();
                        params.addRule(CENTER_IN_PARENT);
                        mTheEndView.setLayoutParams(params);
                    }else {
                        mTheEndView = viewStub.inflate();
                    }
                } else {
                    mTheEndView.setVisibility(VISIBLE);
                }

                mTheEndView.setVisibility(showView ? VISIBLE : GONE);
                break;
            case NetWorkError:

                hideLoadingView();
                hideErrorView();
                hideEndView();

                if (mNetworkErrorView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.network_error_viewstub);
                    mNetworkErrorView = viewStub.inflate();
                } else {
                    mNetworkErrorView.setVisibility(VISIBLE);
                }
                mNetworkErrorView.setVisibility(showView ? VISIBLE : GONE);
                break;
            default:

                break;
        }
    }

    private void hideErrorView() {
        if (mNetworkErrorView != null) {
            mNetworkErrorView.setVisibility(GONE);
        }
    }

    private void hideEndView() {
        if (mTheEndView != null) {
            mTheEndView.setVisibility(GONE);
        }
    }

    private void hideLoadingView() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }
    }

    public void setEmptyView(View view) {
        mCustomEmptyView = view;
    }

    public static enum State {
        Normal/**正常*/
        , TheEnd/**加载到最底了*/
        , Loading/**加载中..*/
        , NetWorkError/**网络异常*/
    }
}