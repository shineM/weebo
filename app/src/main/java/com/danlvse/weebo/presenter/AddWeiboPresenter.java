package com.danlvse.weebo.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.Weibo;

/**
 * Created by zxy on 16/6/2.
 */
public interface AddWeiboPresenter {
    void postPicWeibo(Context context, String content, Bitmap bitmap, String lat, String lon);

    void postWeibo(Context context, String content, String lat, String lon);

    void repostWeibo(Context context, String content, Weibo weibo);

    void commentWeibo(Context context,String content, Weibo weibo);

    void reply(Context context, String content, Comment comment, Weibo weibo);
}
