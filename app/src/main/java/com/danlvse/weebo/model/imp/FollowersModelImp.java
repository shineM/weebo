package com.danlvse.weebo.model.imp;

import android.content.Context;

import com.danlvse.weebo.data.User;
import com.danlvse.weebo.data.api.FriendshipsAPI;
import com.danlvse.weebo.model.FollowersModel;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.danlvse.weebo.utils.weibo.WeiboParseUtil;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;

/**
 * Created by zxy on 16/6/11.
 */
public class FollowersModelImp implements FollowersModel {

    @Override
    public void getFollowers(Context context, String username, final OnDatasComplete listener) {
        FriendshipsAPI api = new FriendshipsAPI(context, Constants.APP_KEY,AccessTokenKeeper.readAccessToken(context));
        api.followers(username, 30, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ArrayList<User> users = WeiboParseUtil.parseUserList(s);
                listener.onComplete(users);
                System.out.println("fans num:"+users.size());
            }

            @Override
            public void onWeiboException(WeiboException e) {
                listener.onError(e.toString());
                System.out.println("error info："+e.toString());
            }
        });
    }

    @Override
    public void nextPageFollowers(Context context, String username, OnDatasComplete listener) {

    }
}
