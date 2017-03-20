package com.danlvse.weebo.mvpmodel.imp;

import android.content.Context;

import com.danlvse.weebo.model.Comment;
import com.danlvse.weebo.model.api.CommentsAPI;
import com.danlvse.weebo.mvpmodel.MessageModel;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.danlvse.weebo.utils.weibo.WeiboParseUtil;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;

/**
 * Created by zxy on 16/6/14.
 */
public class MessageModelImp implements MessageModel {
    @Override
    public void getMessage(Context context, final OnDataFinished onDataFinished) {
        CommentsAPI api = new CommentsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        api.toME(0, 0, 50, 1, 0, 0, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ArrayList<Comment> list = WeiboParseUtil.parseCommentList(s);
                onDataFinished.OnSuccess(list);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onDataFinished.OnError(e.toString());
            }
        });
    }

    @Override
    public void getMention(Context context, final OnDataFinished onDataFinished) {
        CommentsAPI api = new CommentsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        api.mentions(0, 0, 50, 1, 0, 0, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ArrayList<Comment> list = WeiboParseUtil.parseCommentList(s);
                onDataFinished.OnSuccess(list);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onDataFinished.OnError(e.toString());
            }
        });
    }
}
