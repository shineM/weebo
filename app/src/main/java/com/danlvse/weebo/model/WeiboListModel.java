package com.danlvse.weebo.model;

import android.content.Context;

import com.danlvse.weebo.data.Weibo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxy on 16/5/25.
 */
public interface WeiboListModel {
    interface OnDataFinishedListener {
        void noMoreData();

        void onComplete(ArrayList<Weibo> weibos);

        void onError(String error);
    }


    public  void clearFriendsTimelineCache(Context context);
    public void friendsTimeline(Context context, OnDataFinishedListener onDataFinishedListener);

    public void friendsTimelineNextPage(Context context, OnDataFinishedListener onDataFinishedListener);

    public void friendsTimelineCacheLoad(Context context, OnDataFinishedListener onDataFinishedListener);

    public void friendsTimelineCacheSave(Context context, String response);

    public void friendsTimelineParceCacheLoad(Context context, OnDataFinishedListener onDataFinishedListener);

    public void friendsTimelineParceCacheSave(Context context, List list);

    public void bilateralTimeline(Context context, OnDataFinishedListener onDataFinishedListener);

    public void bilateralTimelineNextPage(Context context, OnDataFinishedListener onDataFinishedListener);

    public void timeline(long groundId, Context context, OnDataFinishedListener onDataFinishedListener);

    public void timelineNextPage(long groundId, Context context, OnDataFinishedListener onDataFinishedListener);

    public ArrayList friendsTimelineNext(Context context);

    public ArrayList<Weibo> refreshTimeline(Context context);
}
