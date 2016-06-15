package com.danlvse.weebo.model;

import android.content.Context;

import com.danlvse.weebo.data.Comment;

import java.util.List;

/**
 * Created by zxy on 16/6/14.
 */
public interface MessageModel {
    interface OnDataFinished{
        void OnSuccess(List<Comment> comments);

        void OnError(String s);
    }
    void getMessage(Context context, OnDataFinished onDataFinished);

    void getMention(Context context, OnDataFinished onDataFinished);
}
