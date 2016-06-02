package com.danlvse.weebo.presenter.imp;

import android.content.Context;

import com.danlvse.weebo.data.User;
import com.danlvse.weebo.model.ProfileModel;
import com.danlvse.weebo.model.imp.ProfileModelImp;
import com.danlvse.weebo.presenter.ProfilePresenter;
import com.danlvse.weebo.ui.view.ProfileView;

/**
 * Created by zxy on 16/6/1.
 */
public class ProfilePresenterImp implements ProfilePresenter {
    private ProfileView profileView;
    private ProfileModel profileModel;
    private ProfileModel.OnDataFinished listener = new ProfileModel.OnDataFinished() {

        @Override
        public void onComplete(User user) {
            profileView.bindProfile(user);
            profileView.hideLoadingIcon();
        }

        @Override
        public void onError(String s) {
            profileView.showErrorInfo();
            profileView.hideLoadingIcon();
        }
    };

    public ProfilePresenterImp(ProfileView profileView) {
        this.profileView = profileView;
        profileModel = new ProfileModelImp();
    }

    @Override
    public void getUser(Context context, String username) {
        profileView.showLoadingIcon();
        profileModel.getUser(context,username,listener);
    }

    @Override
    public void getWeibos(Context context, String username) {

    }

}
