package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.model.Feed;

import java.util.List;

/**
 * Created by zxy on 16/6/11.
 */
public interface TopicView {
    void showLoadingIcon();

    void hideLoadingIcon();

    void showErrorInfo(String s);

    void upadteList(List<Feed> feeds);
}
