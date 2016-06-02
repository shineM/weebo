package com.danlvse.weebo.presenter;

import android.content.Context;

/**
 * Created by zxy on 16/6/1.
 */
public interface ProfilePresenter {
    void getUser(Context context, String username);

    void getWeibos(Context context, String username);
}
