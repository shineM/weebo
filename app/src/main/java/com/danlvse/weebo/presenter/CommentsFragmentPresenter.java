package com.danlvse.weebo.presenter;

import android.content.Context;

import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.Weibo;

import java.util.ArrayList;

/**
 * Created by zxy on 16/5/27.
 */
public interface CommentsFragmentPresenter {
    void loadComment(Context context, Weibo weibo);
    void loadMoreComment(Context context);
}
