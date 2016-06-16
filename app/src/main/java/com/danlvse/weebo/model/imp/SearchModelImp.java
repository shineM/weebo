package com.danlvse.weebo.model.imp;

import android.content.Context;

import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.data.api.SearchAPI;
import com.danlvse.weebo.model.SearchModel;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.danlvse.weebo.utils.weibo.WeiboParseUtil;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;

/**
 * Created by zxy on 16/6/16.
 */
public class SearchModelImp implements SearchModel {

    @Override
    public void searchWeibo(String key, Context context, final OnSearchCompleted onSearchCompleted) {
        SearchAPI api = new SearchAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        api.topic(key, 20, new RequestListener() {
            @Override
            public void onComplete(String s) {
                System.out.println(s);
                ArrayList<Weibo> weibos = WeiboParseUtil.parseWeiboList(s);
                if (weibos.size()>1){
                    onSearchCompleted.onSuccess(weibos);
                }else {
                    onSearchCompleted.onEmpty();
                }

            }

            @Override
            public void onWeiboException(WeiboException e) {
                onSearchCompleted.onError(e.toString());
            }
        });
    }

    @Override
    public void searchUser(String key, Context context, OnSearchCompleted onSearchCompleted) {

    }
}
