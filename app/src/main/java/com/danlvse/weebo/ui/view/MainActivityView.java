package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.model.Feed;

import java.util.List;

/**
 * Created by zxy on 16/5/24.
 */
public interface MainActivityView {
    void refreshData(List<Feed> feeds);

    void hideRefreshIcon();

    void showRefreshIcon();

    void showMoreData(List<Feed> feeds);

    void remainMoreData();

    void showErrorInfo(String error);
}
