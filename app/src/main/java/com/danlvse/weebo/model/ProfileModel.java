package com.danlvse.weebo.model;

import android.content.Context;

import com.danlvse.weebo.data.User;
import com.danlvse.weebo.data.Weibo;

import java.util.List;

/**
 * Created by zxy on 16/6/1.
 */
public interface ProfileModel {
    interface OnDataFinished{
        void onComplete(User user);
        void onError(String s);
    }
    interface OnWeiboListLoaded
    {
        void onComplete(List<Weibo> weibos);
        void onError(String s);
    }
    void getUser(Context context,String userName, OnDataFinished onDataFinished);
    void getUserWeibos(Context context,String userName,OnWeiboListLoaded onWeiboListLoaded);
}
