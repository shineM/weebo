package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.data.User;
import com.danlvse.weebo.data.Weibo;

import java.util.List;

/**
 * Created by zxy on 16/6/1.
 */
public interface ProfileView {
    void bindProfile(User user);
    void updateWeiboList(List<Weibo> weibos);
    void showErrorInfo();
    void showLoadingIcon();
    void hideLoadingIcon();
}
