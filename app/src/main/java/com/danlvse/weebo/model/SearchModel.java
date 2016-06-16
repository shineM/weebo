package com.danlvse.weebo.model;

import android.content.Context;

import com.danlvse.weebo.data.Weibo;

import java.util.List;

/**
 * Created by zxy on 16/6/16.
 */
public interface SearchModel {
    interface OnSearchCompleted{
        void onSuccess(List<Weibo> list);

        void onError(String error);

        void onEmpty();
    }
    void searchWeibo(String key, Context context,OnSearchCompleted onSearchCompleted);

    void searchUser(String key,Context context,OnSearchCompleted onSearchCompleted);
}
