package com.danlvse.weebo.model.imp;

import android.content.Context;

import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.data.api.SearchAPI;
import com.danlvse.weebo.model.TopicModel;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.danlvse.weebo.utils.weibo.WeiboParseUtil;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.List;

/**
 * Created by zxy on 16/6/11.
 */
public class TopicModelImp implements TopicModel {
    @Override
    public void search(String key, Context context, final OnDataComplete listener) {
        SearchAPI api = new SearchAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        api.topic(key, 200, new RequestListener() {
            @Override
            public void onComplete(String s) {
                List<Weibo> weibos = WeiboParseUtil.parseWeiboList(s);
                listener.onFinished(weibos);
                System.out.println("size------------"+weibos.size());
            }

            @Override
            public void onWeiboException(WeiboException e) {
                listener.onError(e.toString());
                System.out.println("network wrong");
            }
        });
    }
}
