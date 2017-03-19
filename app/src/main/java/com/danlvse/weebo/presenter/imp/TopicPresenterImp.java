package com.danlvse.weebo.presenter.imp;

import android.content.Context;

import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.mvpmodel.TopicModel;
import com.danlvse.weebo.mvpmodel.imp.TopicModelImp;
import com.danlvse.weebo.presenter.TopicPresenter;
import com.danlvse.weebo.ui.view.TopicView;

import java.util.List;

/**
 * Created by zxy on 16/6/11.
 */
public class TopicPresenterImp implements TopicPresenter {
    private TopicModel topicModel;
    private TopicView topicView;
    private Context context;
    private TopicModel.OnDataComplete listener = new TopicModel.OnDataComplete() {
        @Override
        public void onFinished(List<Feed> feeds) {
            topicView.hideLoadingIcon();
            topicView.upadteList(feeds);
        }

        @Override
        public void onError(String s) {
            topicView.hideLoadingIcon();
            topicView.showErrorInfo(s);
        }

        @Override
        public void noMoreData() {

        }
    };
    public TopicPresenterImp(Context context, TopicView topicView) {
        this.context = context;
        this.topicView = topicView;
        topicModel  = new TopicModelImp();
    }


    @Override
    public void loadDatas(String key, Context context) {
        topicView.showLoadingIcon();
        topicModel.search(key, context, listener);
    }

    @Override
    public void refresh(String key, Context context) {

    }

    @Override
    public void getNextPage(String key, Context context) {

    }

}
