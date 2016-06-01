package com.danlvse.weebo.model.imp;

import android.content.Context;

import com.danlvse.weebo.data.User;
import com.danlvse.weebo.data.api.UserAPI;
import com.danlvse.weebo.model.ProfileModel;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 * Created by zxy on 16/6/1.
 */
public class ProfileModelImp implements ProfileModel {
    @Override
    public void getUser(Context context, String userName, final OnDataFinished onDataFinished) {

        UserAPI userAPI = new UserAPI(context, Constants.APP_KEY,AccessTokenKeeper.readAccessToken(context));
        userAPI.show(userName, new RequestListener() {
            @Override
            public void onComplete(String s) {
                User user = User.parse(s);
                onDataFinished.onComplete(user);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onDataFinished.onError(e.toString());
            }
        });
    }
}
