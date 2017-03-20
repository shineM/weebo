package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.model.User;

import java.util.List;

/**
 * Created by zxy on 16/6/11.
 */
public interface FollowersView {
    void showLoadingIcon();

    void hideLoadingIcon();

    void showErrorInfo(String s);

    void updateList(List<User> users);
}
