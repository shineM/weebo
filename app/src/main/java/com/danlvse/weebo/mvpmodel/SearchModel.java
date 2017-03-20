package com.danlvse.weebo.mvpmodel;

import android.content.Context;

import com.danlvse.weebo.model.Feed;

import java.util.List;

/**
 * Created by zxy on 16/6/16.
 */
public interface SearchModel {
    interface OnSearchCompleted{
        void onSuccess(List<Feed> list);

        void onError(String error);

        void onEmpty();
    }
    void searchWeibo(String key, Context context,OnSearchCompleted onSearchCompleted);

    void searchUser(String key,Context context,OnSearchCompleted onSearchCompleted);
}
