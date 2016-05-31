package com.danlvse.weebo.model.imp;

import android.content.Context;
import android.renderscript.Sampler;
import android.text.TextUtils;

import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.data.api.StatusesAPI;
import com.danlvse.weebo.model.WeiboListModel;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.FileUtil;
import com.danlvse.weebo.utils.weibo.WeiboParseUtil;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxy on 16/5/25.
 */
public class WeiboListModelImp implements WeiboListModel {

    private ArrayList<Weibo> mCurrentWeibos = new ArrayList<>();

    private boolean refreshAll = true;

    @Override
    public void clearFriendsTimelineCache(Context context) {
        FileUtil.clear(context, FileUtil.getSDCardPath() + "/weebo/", "timeline_cache.txt");
    }

    @Override
    public void friendsTimeline(final Context context, final OnDataFinishedListener onDataFinishedListener) {
        long sinceId = 0;
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        if (!refreshAll && mCurrentWeibos.size() > 0) {
            sinceId = Long.valueOf(mCurrentWeibos.get(0).id);
        }
        if (refreshAll) {
            sinceId = 0;

        }
        mStatusesAPI.friendsTimeline(sinceId, 0, 5, 1, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ArrayList<Weibo> weibos = WeiboParseUtil.parseWeiboList(s);
                //TODO:重登APP间隔时间太长貌似会漏掉中间的微博？
                if (weibos.size() > 0) {
                    if (refreshAll) {
                        mCurrentWeibos = weibos;
                    } else {
                        mCurrentWeibos.addAll(0, weibos);
//                    friendsTimelineCacheSave(context,s);

                    }
                    onDataFinishedListener.onComplete(mCurrentWeibos);
                    friendsTimelineParceCacheSave(context, mCurrentWeibos);


                } else {
                    onDataFinishedListener.noMoreData();
                }
                refreshAll = false;
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onDataFinishedListener.onError(e.getMessage());
            }
        });
    }

    @Override
    public void friendsTimelineNextPage(Context context, final OnDataFinishedListener onDataFinishedListener) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mStatusesAPI.friendsTimeline(0, Long.valueOf(mCurrentWeibos.get(mCurrentWeibos.size() - 1).id), 5, 1, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ArrayList<Weibo> weibos = WeiboParseUtil.parseWeiboList(s);
                if (weibos.size() > 1) {
                    weibos.remove(0);
                    mCurrentWeibos.addAll(weibos);
                    onDataFinishedListener.onComplete(weibos);
                } else {
                    onDataFinishedListener.noMoreData();
                }

            }

            @Override
            public void onWeiboException(WeiboException e) {
                onDataFinishedListener.onError(e.getLocalizedMessage()
                );
            }
        });
    }

    @Override
    public void friendsTimelineCacheLoad(Context context, OnDataFinishedListener onDataFinishedListener) {
        String cache = FileUtil.get(context, FileUtil.getSDCardPath() + "/weebo/", "timeline_cache.txt");
        if (!TextUtils.isEmpty(cache)) {
            ArrayList<Weibo> weibos = WeiboParseUtil.parseWeiboList(cache);
            if (weibos.size() > 0) {
                mCurrentWeibos = weibos;
                onDataFinishedListener.onComplete(weibos);
            } else {
                friendsTimeline(context, onDataFinishedListener);
                onDataFinishedListener.noMoreData();
            }
        } else {
            friendsTimeline(context, onDataFinishedListener);
        }
    }

    @Override
    public void friendsTimelineCacheSave(Context context, String response) {
        FileUtil.put(context, FileUtil.getSDCardPath() + "/weebo/", "timeline_cache.txt", response);
    }

    @Override
    public void friendsTimelineParceCacheLoad(Context context, OnDataFinishedListener onDataFinishedListener) {
        ArrayList<Weibo> weibos = FileUtil.getCache(context, FileUtil.getSDCardPath() + "/weebo/", "weibos_cache.txt");
        if (null == weibos || weibos.size() == 0) {
            friendsTimeline(context, onDataFinishedListener);
        } else {
            onDataFinishedListener.onComplete(weibos);
        }
    }

    @Override
    public void friendsTimelineParceCacheSave(Context context, List list) {
        FileUtil.put(context, FileUtil.getSDCardPath() + "/weebo/", "weibos_cache.txt", list);
    }

    @Override
    public void bilateralTimeline(Context context, OnDataFinishedListener onDataFinishedListener) {

    }

    @Override
    public void bilateralTimelineNextPage(Context context, OnDataFinishedListener onDataFinishedListener) {

    }

    @Override
    public void timeline(long groundId, Context context, OnDataFinishedListener onDataFinishedListener) {


    }

    @Override
    public void timelineNextPage(long groundId, Context context, OnDataFinishedListener onDataFinishedListener) {

    }
    //同步请求方法,暂时弃用
    @Override
    public ArrayList friendsTimelineNext(Context context) {
        ArrayList<Weibo> weibos;
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        weibos = WeiboParseUtil.parseWeiboList(mStatusesAPI.friendsTimeline(0, Long.valueOf(mCurrentWeibos.get(mCurrentWeibos.size() - 1).id), 5, 1, false, 0, false));
        weibos.remove(0);
        mCurrentWeibos.addAll(weibos);
        System.out.println("-------next------微博数量：" + weibos.size() + "-------当前------微博数量：" + mCurrentWeibos.size());

        return weibos;

    }

    @Override
    public ArrayList<Weibo> refreshTimeline(Context context) {
        ArrayList<Weibo> weibos;
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        weibos = WeiboParseUtil.parseWeiboList(mStatusesAPI.friendsTimeline(0, 0, 5, 1, false, 0, false));
        mCurrentWeibos.addAll(weibos);
        return weibos;
    }
}
