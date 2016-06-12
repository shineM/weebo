package com.danlvse.weebo.model;

import android.content.Context;

import com.danlvse.weebo.data.Weibo;

import java.util.List;

/**
 * Created by zxy on 16/6/11.
 */
public interface TopicModel {
    interface OnDataComplete {
        void onFinished(List<Weibo> weibos);

        void onError(String s);

        void noMoreData();
    }

    void search(String key, Context context, OnDataComplete listener);

}
