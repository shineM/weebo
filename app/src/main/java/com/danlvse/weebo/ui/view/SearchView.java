package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.data.Weibo;

import java.util.List;

/**
 * Created by zxy on 16/6/16.
 */
public interface SearchView {
    void showLoadingIcon();

    void hideLoadingIcon();

    void showEmptyInfo();

    void showErrorInfo();

    void updateResultList(List<Weibo> results);
}
