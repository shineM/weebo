package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.data.Weibo;

/**
 * Created by zxy on 16/6/2.
 */
public interface AddWeiboView {
    void showErrorInfo();
    void showSuccessInfo(Weibo weibo);
    void showLoadingIcon();
    void hideLoadingIcon();
}
