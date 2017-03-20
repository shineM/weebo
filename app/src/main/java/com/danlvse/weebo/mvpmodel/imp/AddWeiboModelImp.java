package com.danlvse.weebo.mvpmodel.imp;

import android.content.Context;
import android.graphics.Bitmap;

import com.danlvse.weebo.model.Comment;
import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.model.api.CommentsAPI;
import com.danlvse.weebo.model.api.StatusesAPI;
import com.danlvse.weebo.mvpmodel.AddWeiboModel;
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
                onPostFinished.successed(Feed.parse(s));
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
                onPostFinished.successed(Feed.parse(s));
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onPostFinished.failed(e.toString());
            }
        });
    }

    @Override
    public void repost(Context context, String content, Feed feed, final OnRepostFinished onRepostFinished) {
        StatusesAPI api = new StatusesAPI(context,Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        api.repost(Long.valueOf(feed.id), content, 0, new RequestListener() {
            @Override
            public void onComplete(String s) {
                onRepostFinished.successed(Feed.parse(s));
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onRepostFinished.failed(e.toString());
            }
        });
    }

    @Override
    public void comment(Context context, String content, Feed feed, final OnCommentFinished onCommentFinished) {
        CommentsAPI api = new CommentsAPI(context,Constants.APP_KEY,AccessTokenKeeper.readAccessToken(context));
        api.create(content, Long.valueOf(feed.id), false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                onCommentFinished.successed(Comment.parse(s));
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onCommentFinished.failed(e.toString());
            }
        });
    }

    @Override
    public void reply(Context context, String content, Comment comment, Feed feed, final OnCommentFinished onCommentFinished) {
        CommentsAPI api = new CommentsAPI(context,Constants.APP_KEY,AccessTokenKeeper.readAccessToken(context));
        api.reply(Long.valueOf(comment.id),Long.valueOf(feed.id),content,false, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                onCommentFinished.successed(Comment.parse(s));
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onCommentFinished.failed(e.toString());
            }
        });
    }
}
