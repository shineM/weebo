package com.danlvse.weebo.model;

import android.content.Context;

import com.danlvse.weebo.data.User;

import java.util.List;

/**
 * Created by zxy on 16/6/11.
 */
public interface FollowersModel {
    interface OnDatasComplete{
        void onComplete(List<User> users);

        void onError(String error);
    }

    void getFollowers(Context context, String username, OnDatasComplete listener);

    void nextPageFollowers(Context context, String username, OnDatasComplete listener);
}
