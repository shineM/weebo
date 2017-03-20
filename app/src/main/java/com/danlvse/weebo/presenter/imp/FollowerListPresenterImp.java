package com.danlvse.weebo.presenter.imp;

import android.content.Context;

import com.danlvse.weebo.model.User;
import com.danlvse.weebo.mvpmodel.FollowersModel;
import com.danlvse.weebo.mvpmodel.imp.FollowersModelImp;
import com.danlvse.weebo.presenter.FollowersListPresenter;
import com.danlvse.weebo.ui.view.FollowersView;

import java.util.List;

/**
 * Created by zxy on 16/6/11.
 */
public class FollowerListPresenterImp implements FollowersListPresenter {
    private FollowersModel followersModel;
    private FollowersView followersView;
    private Context mContext;

    private FollowersModel.OnDatasComplete listener = new FollowersModel.OnDatasComplete() {
        @Override
        public void onComplete(List<User> users) {
            followersView.hideLoadingIcon();
            followersView.updateList(users);
        }

        @Override
        public void onError(String error) {
            followersView.hideLoadingIcon();
            followersView.showErrorInfo(error);
        }
    };
    public FollowerListPresenterImp(FollowersView followersView, Context context) {
        this.followersView = followersView;
        this.mContext = context;
        followersModel = new FollowersModelImp();
    }

    @Override
    public void getFollowers(Context context, String username) {
        followersView.showLoadingIcon();
        followersModel.getFollowers(context, username, listener);
    }

    @Override
    public void nextPageFollowers(Context context, String username) {

    }
}
