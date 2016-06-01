package com.danlvse.weebo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.danlvse.weebo.ui.ImagesDetailActivity;

/**
 * Created by zxy on 16/6/1.
 */
public class ActivityUtils {
    public static void startActivity(Activity activity, Intent intent, View shareView, String transition){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, shareView, transition);
        activity.startActivity(intent,options.toBundle());
    }
}
