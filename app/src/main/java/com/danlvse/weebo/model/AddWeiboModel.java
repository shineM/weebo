package com.danlvse.weebo.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.Weibo;

/**
 * Created by zxy on 16/6/2.
 */
public interface AddWeiboModel {
    interface OnPostFinished {
        void successed(Weibo weibo);

        void failed(String s);
    }

    interface OnRepostFinished {
        void successed(Weibo weibo);

        void failed(String s);
    }
    interface OnCommentFinished {
        void successed(Comment comment);

        void failed(String s);
    }
    void upload(Context context, String content, Bitmap bitmap, String lat, String lon, OnPostFinished onPostFinished);

    void update(Context context, String content, String lat, String lon, OnPostFinished onPostFinished);

    void repost(Context context, String content, Weibo weibo, OnRepostFinished onRepostFinished);

    void comment(Context context, String content, Weibo weibo, OnCommentFinished onCommentFinished);
}
