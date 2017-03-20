package com.danlvse.weebo.mvpmodel;

import android.content.Context;

import com.danlvse.weebo.model.Topic;

import java.util.List;

/**
 * Created by zxy on 16/6/12.
 */
public interface TrendsModel {
    interface OnDatasFinish{
        void onSuccess(List<Topic> topics);

        void onError(String error);
    }
    void getTrends(Context context,OnDatasFinish onDatasFinish);
}
