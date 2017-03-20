package com.danlvse.weebo.data;

import android.content.Context;

import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.model.api.StatusesAPI;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.danlvse.weebo.utils.weibo.WeiboParseUtil;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxy on 17/3/19.
 */

public class FeedsRepository {
    private static final int START_SINCE_ID = 0;
    private static final int DEFAULT_MAX_ID = 0;
    private static final int DEFAULT_PAGE_COUNT = 20;
    private static final int ONE_PAGE = 1;

    /**
     * 加载全部类型微博
     *
     * @param context  Context
     * @param callBack 回调
     */
    public void loadAllFeeds(Context context, final DataCallBack<List<Feed>> callBack) {
        StatusesAPI mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY,
                AccessTokenKeeper.readAccessToken(context));
        mStatusesAPI.friendsTimeline(
                START_SINCE_ID, DEFAULT_MAX_ID, DEFAULT_PAGE_COUNT, ONE_PAGE,
                false, StatusesAPI.FEATURE_ALL, false, new RequestListener() {
                    @Override
                    public void onComplete(String s) {
                        ArrayList<Feed> feeds = WeiboParseUtil.parseWeiboList(s);
                        if (feeds != null && feeds.size() > 0) {
                            callBack.onSuccess(feeds);
                        } else {
                            callBack.onEmpty();
                        }
                    }

                    @Override
                    public void onWeiboException(WeiboException e) {
                        callBack.onException(e);
                    }
                });
    }
}
