package com.danlvse.weebo.presenter.imp;

import android.content.Context;

import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.model.WeiboListModel;
import com.danlvse.weebo.model.imp.WeiboListModelImp;
import com.danlvse.weebo.presenter.MainActivityPresenter;
import com.danlvse.weebo.ui.view.MainActivityView;

import java.util.ArrayList;

/**
 * Created by zxy on 16/5/24.
 */
public class MainActivityPresenterImp implements MainActivityPresenter {
    private WeiboListModel weiboListModel;
    private MainActivityView mainActivityView;


    public MainActivityPresenterImp(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
        this.weiboListModel = new WeiboListModelImp();
    }

    @Override
    public void loadDatas(long groupId, Context context, Boolean isFirstIn) {
        mainActivityView.showRefreshIcon();
        if (isFirstIn) {
            weiboListModel.friendsTimeline(context, onDataFinishedListener);
        } else {
            weiboListModel.friendsTimelineParceCacheLoad(context, onDataFinishedListener);
        }
    }

    @Override
    public void refresh(long groupId, Context context) {
        mainActivityView.showRefreshIcon();
        weiboListModel.friendsTimeline(context, onDataFinishedListener);

    }


    @Override
    public void getNextPage(Context context) {
        weiboListModel.friendsTimelineNextPage(context, onMoreDataFinishedListener);
    }

    private WeiboListModel.OnDataFinishedListener onDataFinishedListener = new WeiboListModel.OnDataFinishedListener() {
        @Override
        public void noMoreData() {
            mainActivityView.hideRefreshIcon();
            mainActivityView.remainMoreData();

        }


        @Override
        public void onComplete(ArrayList<Weibo> statuslist) {
            mainActivityView.hideRefreshIcon();
            mainActivityView.refreshData(statuslist);

        }

        @Override
        public void onError(String error) {
            mainActivityView.hideRefreshIcon();
            mainActivityView.showErrorInfo(error);
        }
    };
    private WeiboListModel.OnDataFinishedListener onMoreDataFinishedListener = new WeiboListModel.OnDataFinishedListener() {
        @Override
        public void noMoreData() {

        }


        @Override
        public void onComplete(ArrayList<Weibo> weibos) {
            System.out.println("--------------------load more count；"+weibos.size());
            mainActivityView.showMoreData(weibos);
        }

        @Override
        public void onError(String error) {

        }
    };

}
