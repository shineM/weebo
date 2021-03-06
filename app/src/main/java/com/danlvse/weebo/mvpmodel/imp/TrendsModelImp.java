package com.danlvse.weebo.mvpmodel.imp;

import android.content.Context;

import com.danlvse.weebo.model.api.TrendsAPI;
import com.danlvse.weebo.mvpmodel.TrendsModel;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 * Created by zxy on 16/6/12.
 */
public class TrendsModelImp implements TrendsModel {
    @Override
    public void getTrends(Context context, final OnDatasFinish onDatasFinish) {
        TrendsAPI api = new TrendsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        api.weekly(false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                System.out.println(s);
//                ArrayList<Topic> topics = WeiboParseUtil.parseTopicList(s);
//
//                if (topics.size()>0){
//                    onDatasFinish.onSuccess(topics);
//
//                }else{
//                    onDatasFinish.onError("数据为空");
//                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                System.out.println(e.toString());
                onDatasFinish.onError(e.toString());
            }
        });
    }
}
