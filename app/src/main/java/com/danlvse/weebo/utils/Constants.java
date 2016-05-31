package com.danlvse.weebo.utils;

import android.text.TextUtils;

/**
 * Created by zxy on 16/5/24.
 */
public class Constants {
    public static final String APP_KEY = "211160679";
    public static final String REDIRECT_URL = "http://oauth.weico.cc";
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";

    public static final String AppSecret = "1e6e33db08f9192306c4afa0a61ad56c";
    public static final String PackageName = "com.eico.weico";
    public static final String authurl = "https://open.weibo.cn/oauth2/authorize" + "?" + "client_id=" + Constants.APP_KEY
            + "&response_type=token&redirect_uri=" + Constants.REDIRECT_URL
            + "&key_hash=" + Constants.AppSecret + (TextUtils.isEmpty(Constants.PackageName) ? "" : "&packagename=" + Constants.PackageName)
            + "&display=mobile"+ "&scope=" + Constants.SCOPE;

}
