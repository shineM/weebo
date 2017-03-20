package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.model.User;

import java.util.List;

/**
 * Created by zxy on 16/6/1.
 */
public interface ProfileView {
    void bindProfile(User user);
    void updateWeiboList(List<Feed> feeds);
    void showErrorInfo();
    void showLoadingIcon();
    void hideLoadingIcon();
}
