package com.danlvse.weebo.mvpmodel;

import android.content.Context;

import com.danlvse.weebo.model.Comment;
import com.danlvse.weebo.model.Feed;

import java.util.ArrayList;

/**
 * Created by zxy on 16/5/27.
 */
public interface CommentsModel {
    interface OnCommentLoadListener{
        void onDataFinish(ArrayList<Comment> list);
        void onError();
        void onNoMoreData();
    }
    void getComments(Context context, Feed feed, OnCommentLoadListener loadListener);
    void getNextPageComments(Context context, OnCommentLoadListener loadListener);
}
