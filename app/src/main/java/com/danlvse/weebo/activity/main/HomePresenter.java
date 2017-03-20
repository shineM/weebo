package com.danlvse.weebo.activity.main;

import android.content.Context;

import com.danlvse.weebo.data.DataCallBack;
import com.danlvse.weebo.data.FeedsRepository;
import com.danlvse.weebo.model.Feed;

import java.util.List;

/**
 * Created by zxy on 17/3/19.
 */

public class HomePresenter implements HomeContract.Presenter {
    private Context context;
    private HomeContract.View view;
    private FeedsRepository feedsRepository;

    public HomePresenter(Context context, HomeContract.View view) {
        this.context = context;
        this.view = view;
        this.feedsRepository = new FeedsRepository();
    }

    @Override
    public void loadFeeds() {
        feedsRepository.loadAllFeeds(context, new DataCallBack<List<Feed>>() {
            @Override
            public void onSuccess(List<Feed> data) {

            }

            @Override
            public void onException(Exception e) {

            }

            @Override
            public void onEmpty() {

            }
        });
    }

    @Override
    public void loadMoreFeeds() {

    }
}
