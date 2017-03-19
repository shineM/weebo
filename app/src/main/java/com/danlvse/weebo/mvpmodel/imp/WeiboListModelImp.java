package com.danlvse.weebo.mvpmodel.imp;

import android.content.Context;
import android.text.TextUtils;

import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.model.api.StatusesAPI;
import com.danlvse.weebo.mvpmodel.WeiboListModel;
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

    private ArrayList<Feed> mCurrentFeeds = new ArrayList<>();

    private boolean refreshAll = true;

    @Override
    public void clearFriendsTimelineCache(Context context) {
        FileUtil.clear(context, FileUtil.getSDCardPath() + "/weebo/", "timeline_cache.txt");
    }

    @Override
    public void friendsTimeline(final Context context, final OnDataFinishedListener onDataFinishedListener) {
        long sinceId = 0;
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        if (!refreshAll && mCurrentFeeds.size() > 0) {
            sinceId = Long.valueOf(mCurrentFeeds.get(0).id);
        }
        if (refreshAll) {
            sinceId = 0;

        }
        mStatusesAPI.friendsTimeline(sinceId, 0, 5, 1, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ArrayList<Feed> feeds = WeiboParseUtil.parseWeiboList(s);
                //TODO:重登APP间隔时间太长貌似会漏掉中间的微博？
                if (feeds.size() > 0) {
                    if (refreshAll) {
                        mCurrentFeeds = feeds;
                    } else {
                        mCurrentFeeds.addAll(0, feeds);
//                    friendsTimelineCacheSave(context,s);

                    }
                    onDataFinishedListener.onComplete(mCurrentFeeds);
                    friendsTimelineParceCacheSave(context, mCurrentFeeds);


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
        mStatusesAPI.friendsTimeline(0, Long.valueOf(mCurrentFeeds.get(mCurrentFeeds.size() - 1).id), 5, 1, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ArrayList<Feed> feeds = WeiboParseUtil.parseWeiboList(s);
                if (feeds.size() > 1) {
                    feeds.remove(0);
                    mCurrentFeeds.addAll(feeds);
                    onDataFinishedListener.onComplete(feeds);
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
            ArrayList<Feed> feeds = WeiboParseUtil.parseWeiboList(cache);
            if (feeds.size() > 0) {
                mCurrentFeeds = feeds;
                onDataFinishedListener.onComplete(feeds);
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
        ArrayList<Feed> feeds = FileUtil.getCache(context, FileUtil.getSDCardPath() + "/weebo/", "weibos_cache.txt");
        if (null == feeds || feeds.size() == 0) {
            friendsTimeline(context, onDataFinishedListener);
        } else {
            onDataFinishedListener.onComplete(feeds);
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
        ArrayList<Feed> feeds;
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        feeds = WeiboParseUtil.parseWeiboList(mStatusesAPI.friendsTimeline(0, Long.valueOf(mCurrentFeeds.get(mCurrentFeeds.size() - 1).id), 5, 1, false, 0, false));
        feeds.remove(0);
        mCurrentFeeds.addAll(feeds);
        System.out.println("-------next------微博数量：" + feeds.size() + "-------当前------微博数量：" + mCurrentFeeds.size());

        return feeds;

    }

    @Override
    public ArrayList<Feed> refreshTimeline(Context context) {
        ArrayList<Feed> feeds;
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        feeds = WeiboParseUtil.parseWeiboList(mStatusesAPI.friendsTimeline(0, 0, 5, 1, false, 0, false));
        mCurrentFeeds.addAll(feeds);
        return feeds;
    }
}
