package com.danlvse.weebo.model.imp;

import android.content.Context;
import android.text.TextUtils;

import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.data.api.CommentsAPI;
import com.danlvse.weebo.model.CommentsModel;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.FileUtil;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.danlvse.weebo.utils.weibo.CommentParseUtil;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 * Created by zxy on 16/5/27.
 */
public class CommentsModelImp implements CommentsModel {
    @Override
    public void getComments(final Context context, Weibo weibo, final OnCommentLoadListener loadListener) {
        CommentsAPI api = new CommentsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        api.show(Long.valueOf(weibo.id), 0, 0, 30, 1, 0, new RequestListener() {
            @Override
            public void onComplete(String s) {
                if (!TextUtils.isEmpty(s)){
//                    FileUtil.put(context,FileUtil.getSDCardPath()+"/weebo/","comment_cache.text",s);
                    loadListener.onDataFinish(CommentParseUtil.parse(s));
                }
            }


            @Override
            public void onWeiboException(WeiboException e) {

            }
        });
    }

    @Override
    public void getNextPageComments(Context context, OnCommentLoadListener loadListener) {

    }
}
