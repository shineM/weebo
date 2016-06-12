package com.danlvse.weebo.presenter;

import android.content.Context;

/**
 * Created by zxy on 16/6/11.
 */
public interface TopicPresenter {
    void loadDatas(String key, Context context);

    void refresh(String key, Context context);

    void getNextPage(String key, Context context);

}
