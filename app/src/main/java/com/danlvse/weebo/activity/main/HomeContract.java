package com.danlvse.weebo.activity.main;

import com.danlvse.weebo.data.DataCallBack;
import com.danlvse.weebo.model.Feed;

import java.util.List;

/**
 * Created by zxy on 17/3/19.
 */

public interface HomeContract {
    interface View {
        void updateFeedList(List<Feed> feedList);

        void appendFeeds(List<Feed> feeds);

        void showErrorView(boolean shouldShow);

        void showException(String exception);

        void showLoadingMore(boolean shouldShow);

        void showLoadingMoreError(boolean shouldShow);

        void showLoadingMoreEnd(boolean shouldShow);
    }

    interface Presenter {
        void loadFeeds();

        void loadMoreFeeds();
    }
}
