package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.data.Comment;

import java.util.ArrayList;

/**
 * Created by zxy on 16/5/27.
 */
public interface CommentsFragmentView {
    void showLoadingIcon();
    void hideLoadingIcon();
    void showErrorInfo(String string);
    void showLoadingMoreIcon();
    void updataList(ArrayList<Comment> list);
}
