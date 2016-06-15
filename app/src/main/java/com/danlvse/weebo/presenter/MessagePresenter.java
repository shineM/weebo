package com.danlvse.weebo.presenter;

import android.content.Context;

/**
 * Created by zxy on 16/6/14.
 */
public interface MessagePresenter {
    void getMessages(Context context);

    void getMentions(Context context);
}
