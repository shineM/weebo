package com.danlvse.weebo.presenter;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by zxy on 16/6/2.
 */
public interface AddWeiboPresenter {
    void postPicWeibo(Context context,String content, Bitmap bitmap, String lat, String lon);
    void postWeibo(Context context,String content, String lat, String lon);
}
