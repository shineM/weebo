package com.danlvse.weebo.presenter;

import android.content.Context;

import java.util.List;

/**
 * Created by zxy on 16/5/24.
 */
public interface MainActivityPresenter {


    void loadDatas(long groupId, Context context, Boolean isFirstIn);

    void refresh(long groupId, Context context);

    void getNextPage(Context context);
}
