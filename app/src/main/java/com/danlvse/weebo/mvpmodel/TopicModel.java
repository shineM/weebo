package com.danlvse.weebo.mvpmodel;

import android.content.Context;

import com.danlvse.weebo.model.Feed;

import java.util.List;

/**
 * Created by zxy on 16/6/11.
 */
public interface TopicModel {
    interface OnDataComplete {
        void onFinished(List<Feed> feeds);

        void onError(String s);

        void noMoreData();
    }

    void search(String key, Context context, OnDataComplete listener);

}
