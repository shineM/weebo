package com.danlvse.weebo.presenter;

import android.content.Context;

/**
 * Created by zxy on 16/6/16.
 */
public interface SearchPresenter {
    void searchWeiboByKey(String key, Context context);

    void searchUserByKey(String key, Context context);

    void searchTopicByKey(String key, Context context);
}
