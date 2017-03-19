package com.danlvse.weebo.mvpmodel;

import android.content.Context;
import android.graphics.Bitmap;

import com.danlvse.weebo.model.Comment;
import com.danlvse.weebo.model.Feed;

/**
 * Created by zxy on 16/6/2.
 */
public interface AddWeiboModel {
    interface OnPostFinished {
        void successed(Feed feed);

        void failed(String s);
    }

    interface OnRepostFinished {
        void successed(Feed feed);

        void failed(String s);
    }
    interface OnCommentFinished {
        void successed(Comment comment);

        void failed(String s);
    }
    void upload(Context context, String content, Bitmap bitmap, String lat, String lon, OnPostFinished onPostFinished);

    void update(Context context, String content, String lat, String lon, OnPostFinished onPostFinished);

    void repost(Context context, String content, Feed feed, OnRepostFinished onRepostFinished);

    void comment(Context context, String content, Feed feed, OnCommentFinished onCommentFinished);

    void reply(Context context, String content, Comment comment, Feed feed, OnCommentFinished onCommentFinished);
}
