package com.danlvse.weebo.presenter.imp;

import android.content.Context;

import com.danlvse.weebo.model.Comment;
import com.danlvse.weebo.mvpmodel.MessageModel;
import com.danlvse.weebo.mvpmodel.imp.MessageModelImp;
import com.danlvse.weebo.presenter.MessagePresenter;
import com.danlvse.weebo.ui.view.MessageView;

import java.util.List;

/**
 * Created by zxy on 16/6/14.
 */
public class MessagePresenterImp implements MessagePresenter {
    private MessageView messageView;
    private MessageModel messageModel;
    private MessageModel.OnDataFinished listener = new MessageModel.OnDataFinished() {
        @Override
        public void OnSuccess(List<Comment> comments) {
            messageView.hideLoadingIcon();
            messageView.updateCommentList(comments);
        }

        @Override
        public void OnError(String s) {
            messageView.hideLoadingIcon();
            messageView.showErrorInfo(s);
        }
    };
    public MessagePresenterImp(MessageView messageView) {
        this.messageView = messageView;
        this.messageModel = new MessageModelImp();
    }

    @Override
    public void getMessages(Context context) {
        messageView.showLoadingIcon();
        messageModel.getMessage(context,listener);
    }

    @Override
    public void getMentions(Context context) {
        messageView.showLoadingIcon();
        messageModel.getMention(context,listener);
    }
}
