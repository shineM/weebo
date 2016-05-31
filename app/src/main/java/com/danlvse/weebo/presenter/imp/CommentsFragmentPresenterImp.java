package com.danlvse.weebo.presenter.imp;

import android.content.Context;

import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.model.CommentsModel;
import com.danlvse.weebo.model.imp.CommentsModelImp;
import com.danlvse.weebo.presenter.CommentsFragmentPresenter;
import com.danlvse.weebo.ui.view.CommentsFragmentView;

import java.util.ArrayList;

/**
 * Created by zxy on 16/5/27.
 */
public class CommentsFragmentPresenterImp implements CommentsFragmentPresenter {
    private CommentsFragmentView mView;
    private Context mContext;
    private CommentsModel commentsModel;

    public CommentsFragmentPresenterImp(CommentsFragmentView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
        commentsModel = new CommentsModelImp();
    }

    @Override
    public void loadComment(Context context, Weibo weibo) {
        mView.showLoadingIcon();
        commentsModel.getComments(context, weibo, new CommentsModel.OnCommentLoadListener() {
            @Override
            public void onDataFinish(ArrayList<Comment> list) {
                mView.hideLoadingIcon();
                mView.updataList(list);
                mView.showErrorInfo("结果条数："+list.size());
            }

            @Override
            public void onError() {
                mView.showErrorInfo("加载出错");
            }

            @Override
            public void onNoMoreData() {
                mView.showErrorInfo("没有更多信息");
            }
        });
    }

    @Override
    public void loadMoreComment(Context context) {

    }
}
