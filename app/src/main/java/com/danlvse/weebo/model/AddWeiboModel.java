package com.danlvse.weebo.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.danlvse.weebo.data.Weibo;

/**
 * Created by zxy on 16/6/2.
 */
public interface AddWeiboModel {
    interface OnPostFinished {
        void successed(Weibo weibo);

        void failed(String s);
    }
    void upload(Context context,String content, Bitmap bitmap, String lat, String lon, OnPostFinished onPostFinished);
    void update(Context context,String content, String lat, String lon,OnPostFinished onPostFinished);
}
