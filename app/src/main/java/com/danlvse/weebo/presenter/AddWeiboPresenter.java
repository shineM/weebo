package com.danlvse.weebo.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.danlvse.weebo.model.Comment;
import com.danlvse.weebo.model.Feed;

/**
 * Created by zxy on 16/6/2.
 */
public interface AddWeiboPresenter {
    void postPicWeibo(Context context, String content, Bitmap bitmap, String lat, String lon);

    void postWeibo(Context context, String content, String lat, String lon);

    void repostWeibo(Context context, String content, Feed feed);

    void commentWeibo(Context context,String content, Feed feed);

    void reply(Context context, String content, Comment comment, Feed feed);
}
