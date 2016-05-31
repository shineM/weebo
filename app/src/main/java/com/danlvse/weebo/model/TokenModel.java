package com.danlvse.weebo.model;

import android.content.Context;

/**
 * Created by zxy on 16/5/24.
 */
public interface TokenModel {
    void addToken(Context context,String token,String expires, String token_refresh,String uid);
    void deleteToken(Context context);
}
