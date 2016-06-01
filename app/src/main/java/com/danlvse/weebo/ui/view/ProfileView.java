package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.data.User;

/**
 * Created by zxy on 16/6/1.
 */
public interface ProfileView {
    void bindProfile(User user);
    void showErrorInfo();
}
