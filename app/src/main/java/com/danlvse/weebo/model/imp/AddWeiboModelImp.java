package com.danlvse.weebo.model.imp;

import android.content.Context;
import android.graphics.Bitmap;

import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.data.api.StatusesAPI;
import com.danlvse.weebo.model.AddWeiboModel;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 * Created by zxy on 16/6/2.
 */
public class AddWeiboModelImp implements AddWeiboModel {

    @Override
    public void upload(Context context, String content, Bitmap bitmap, String lat, String lon, final OnPostFinished onPostFinished) {
        StatusesAPI statusesAPI = new StatusesAPI(context, Constants.APP_KEY,AccessTokenKeeper.readAccessToken(context));
        statusesAPI.upload(content,bitmap,lat,lon,new RequestListener(){

            @Override
            public void onComplete(String s) {
                onPostFinished.successed(Weibo.parse(s));
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onPostFinished.failed(e.toString());
            }
        });

    }

    @Override
    public void update(Context context, String content, String lat, String lon, final OnPostFinished onPostFinished) {
        StatusesAPI statusesAPI = new StatusesAPI(context, Constants.APP_KEY,AccessTokenKeeper.readAccessToken(context));
        statusesAPI.update(content,lat,lon,new RequestListener(){

            @Override
            public void onComplete(String s) {
                onPostFinished.successed(Weibo.parse(s));
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onPostFinished.failed(e.toString());
            }
        });
    }

    @Override
    public void repost(Context context, String content, Weibo weibo, final OnRepostFinished onRepostFinished) {
        StatusesAPI api = new StatusesAPI(context,Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        api.repost(Long.valueOf(weibo.id), content, 0, new RequestListener() {
            @Override
            public void onComplete(String s) {
                onRepostFinished.successed(Weibo.parse(s));
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onRepostFinished.failed(e.toString());
            }
        });
    }
}
