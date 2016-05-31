package com.danlvse.weebo.model;

import android.content.Context;

import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.Weibo;

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
    void getComments(Context context, Weibo weibo, OnCommentLoadListener loadListener);
    void getNextPageComments(Context context, OnCommentLoadListener loadListener);
}
