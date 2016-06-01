package com.danlvse.weebo.model;

import android.content.Context;

import com.danlvse.weebo.data.User;

/**
 * Created by zxy on 16/6/1.
 */
public interface ProfileModel {
    interface OnDataFinished{
        void onComplete(User user);
        void onError(String s);
    }
    void getUser(Context context,String userName, OnDataFinished onDataFinished);
}
