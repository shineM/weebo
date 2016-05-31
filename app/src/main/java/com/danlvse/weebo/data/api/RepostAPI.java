package com.danlvse.weebo.data.api;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.AbsOpenAPI;

/**
 * Created by zxy on 16/5/31.
 */
public class RepostAPI extends AbsOpenAPI{
    public RepostAPI(Context context, String appKey, Oauth2AccessToken accessToken) {
        super(context, appKey, accessToken);
    }
    /**
     * 根据微博ID返回某条微博的评论列表。
     *
     * @param id         需要查询的微博ID。
     * @param since_id   若指定此参数，则返回ID比since_id大的评论（即比since_id时间晚的评论），默认为0。
     * @param max_id     若指定此参数，则返回ID小于或等于max_id的评论，默认为0。
     * @param count      单页返回的记录条数，默认为50
     * @param page       返回结果的页码，默认为1。
     * @param authorType 作者筛选类型，0：全部、1：我关注的人、2：陌生人 ,默认为0。可为以下几种 :
     *                   <li>{@link #AUTHOR_FILTER_ALL}
     *                   <li>{@link #AUTHOR_FILTER_ATTENTIONS}
     *                   <li>{@link #AUTHOR_FILTER_STRANGER}
     * @param listener   异步请求回调接口
     */

}
