package com.danlvse.weebo.presenter.imp;

import android.content.Context;

import com.danlvse.weebo.model.Topic;
import com.danlvse.weebo.mvpmodel.TrendsModel;
import com.danlvse.weebo.mvpmodel.imp.TrendsModelImp;
import com.danlvse.weebo.presenter.TrendsPresenter;
import com.danlvse.weebo.ui.view.TrendsView;

import java.util.List;

/**
 * Created by zxy on 16/6/12.
 */
public class TrendPresenterImp implements TrendsPresenter {
    private TrendsModel trendsModel;
    private TrendsView trendsView;
    private TrendsModel.OnDatasFinish listener = new TrendsModel.OnDatasFinish() {
        @Override
        public void onSuccess(List<Topic> topics) {
            trendsView.hideLoadingIcon();
            trendsView.updateList(topics);
        }

        @Override
        public void onError(String error) {
            trendsView.showErrorInfo(error);
        }
    };

    public TrendPresenterImp(TrendsView trendsView) {
        this.trendsView = trendsView;
        this.trendsModel = new TrendsModelImp();
    }

    @Override
    public void getDailyTrends(Context context) {
        trendsView.showLoadingIcon();
        trendsModel.getTrends(context, listener);

    }

    @Override
    public void getWeeklyTrends(Context context) {

    }
}
