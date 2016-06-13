package com.danlvse.weebo.presenter.imp;

import android.content.Context;
import android.graphics.Bitmap;

import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.model.AddWeiboModel;
import com.danlvse.weebo.model.imp.AddWeiboModelImp;
import com.danlvse.weebo.presenter.AddWeiboPresenter;
import com.danlvse.weebo.ui.view.AddWeiboView;

/**
 * Created by zxy on 16/6/2.
 */
public class AddWeiboPresenterImp implements AddWeiboPresenter {
    private AddWeiboView addWeiboView;
    private AddWeiboModel addWeiboModel;

    public AddWeiboPresenterImp(AddWeiboView addWeiboView) {
        this.addWeiboView = addWeiboView;
        addWeiboModel = new AddWeiboModelImp();
    }

    @Override
    public void postPicWeibo(Context context, String content, Bitmap bitmap, String lat, String lon) {
        addWeiboView.showLoadingIcon();
        addWeiboModel.upload(context,content,bitmap,lat,lon,listener);
    }

    @Override
    public void postWeibo(Context context, String content, String lat, String lon) {
        addWeiboView.showLoadingIcon();
        addWeiboModel.update(context,content,lat,lon,listener);
    }

    @Override
    public void repostWeibo(Context context, String content, Weibo weibo) {
        addWeiboView.showLoadingIcon();
        addWeiboModel.repost(context, content,weibo,onRepostFinished);
    }

    @Override
    public void commentWeibo(Context context, String content, Weibo weibo) {
        addWeiboView.showLoadingIcon();
        addWeiboModel.comment(context,content,weibo,onCommentFinished);
    }

    private AddWeiboModel.OnCommentFinished onCommentFinished = new AddWeiboModel.OnCommentFinished() {
        @Override
        public void successed(Comment comment) {
            addWeiboView.hideLoadingIcon();
            addWeiboView.showSuccessInfo(comment.status);
        }

        @Override
        public void failed(String s) {
            addWeiboView.hideLoadingIcon();
            addWeiboView.showErrorInfo();
        }
    };
    private AddWeiboModel.OnPostFinished listener = new AddWeiboModel.OnPostFinished() {
        @Override
        public void successed(Weibo weibo) {
            addWeiboView.hideLoadingIcon();
            addWeiboView.showSuccessInfo(weibo);
        }

        @Override
        public void failed(String s) {
            addWeiboView.hideLoadingIcon();
            addWeiboView.showErrorInfo();
        }
    };
    private AddWeiboModel.OnRepostFinished onRepostFinished = new AddWeiboModel.OnRepostFinished() {
        @Override
        public void successed(Weibo weibo) {
            addWeiboView.hideLoadingIcon();
            addWeiboView.showSuccessInfo(weibo);
        }

        @Override
        public void failed(String s) {
            addWeiboView.hideLoadingIcon();
            addWeiboView.showErrorInfo();
        }
    };
}
