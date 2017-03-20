package com.danlvse.weebo.presenter;

import android.content.Context;

import com.danlvse.weebo.model.Feed;

/**
 * Created by zxy on 16/5/27.
 */
public interface CommentsFragmentPresenter {
    void loadComment(Context context, Feed feed);
    void loadMoreComment(Context context);
}
