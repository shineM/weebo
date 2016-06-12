package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.data.Topic;

import java.util.List;

/**
 * Created by zxy on 16/6/12.
 */
public interface TrendsView {
    void showLoadingIcon();

    void hideLoadingIcon();

    void showErrorInfo(String s);

    void updateList(List<Topic> topics);
}
