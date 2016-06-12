package com.danlvse.weebo.presenter;

import android.content.Context;

/**
 * Created by zxy on 16/6/12.
 */
public interface TrendsPresenter {
    void getDailyTrends(Context context);

    void getWeeklyTrends(Context context);
}
