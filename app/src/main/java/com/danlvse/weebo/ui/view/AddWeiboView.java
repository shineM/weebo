package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.model.Feed;

/**
 * Created by zxy on 16/6/2.
 */
public interface AddWeiboView {
    void showErrorInfo();

    void showSuccessInfo(Feed feed);

    void showLoadingIcon();

    void hideLoadingIcon();
}
