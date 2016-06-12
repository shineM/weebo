package com.danlvse.weebo.presenter;

import android.content.Context;

/**
 * Created by zxy on 16/6/11.
 */
public interface FollowersListPresenter {
    void getFollowers(Context context, String username);
    void nextPageFollowers(Context context, String username);
}
