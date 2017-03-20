package com.danlvse.weebo.ui.view;

import com.danlvse.weebo.model.Comment;

import java.util.List;

/**
 * Created by zxy on 16/6/13.
 */
public interface MessageView {
    void showLoadingIcon();

    void hideLoadingIcon();

    void showErrorInfo(String s);

    void updateCommentList(List<Comment> comments);

    void updateMentionList(List<Comment> mentions);
}
