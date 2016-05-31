package com.danlvse.weebo.model.imp;

import android.content.Context;

import com.danlvse.weebo.data.Token;
import com.danlvse.weebo.model.TokenModel;
import com.danlvse.weebo.utils.FileUtil;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by zxy on 16/5/24.
 */
public class TokenModelImp implements TokenModel {
    @Override
    public void addToken(Context context, String token, String expires, String token_refresh, String uid) {

        Token t = Token.parse(FileUtil.get(context,FileUtil.getSDCardPath()+"/weebo/","token_cache.txt"));
        if (t!=null&&"uid".equals(t.getUid())){
            return;
        }
        Gson gson = new Gson();
        t = new Token(token, expires, token_refresh, uid);
        FileUtil.put(context, FileUtil.getSDCardPath()+"/weebo/","token_cache.txt",gson.toJson(t));
        updateAccessToken(context, token, expires, token_refresh, uid);
    }
    public void updateAccessToken(Context context, String token, String expiresIn, String refresh_token, String uid) {
        Oauth2AccessToken mAccessToken = new Oauth2AccessToken();
        mAccessToken.setToken(token);
        mAccessToken.setExpiresIn(expiresIn);
        mAccessToken.setRefreshToken(refresh_token);
        mAccessToken.setUid(uid);
        AccessTokenKeeper.writeAccessToken(context, mAccessToken);
    }

    @Override
    public void deleteToken(Context context) {
        AccessTokenKeeper.clear(context);
    }
}
