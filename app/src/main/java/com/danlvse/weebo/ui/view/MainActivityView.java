package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.data.Weibo;

import java.util.List;

/**
 * Created by zxy on 16/5/24.
 */
public interface MainActivityView {
    void refreshData(List<Weibo> weibos);

    void hideRefreshIcon();

    void showRefreshIcon();

    void showMoreData(List<Weibo> weibos);

    void remainMoreData();

    void showErrorInfo(String error);
}
