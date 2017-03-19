package com.danlvse.weebo.presenter.imp;

import android.content.Context;

import com.danlvse.weebo.mvpmodel.TokenModel;
import com.danlvse.weebo.mvpmodel.imp.TokenModelImp;
import com.danlvse.weebo.presenter.WebViewPresenter;
import com.danlvse.weebo.ui.view.WebActivityView;

/**
 * Created by zxy on 16/5/24.
 */
public class WebViewPresenterImp implements WebViewPresenter {
    private WebActivityView mWebActivityView;
    private TokenModel mTokenModel;
    public WebViewPresenterImp(WebActivityView webActivityView){
        this.mWebActivityView = webActivityView;
        mTokenModel = new TokenModelImp();
    }

    @Override
    public void handleUrl(Context mContext, String url) {
        if (!url.contains("error")) {
            int tokenIndex = url.indexOf("access_token=");
            int expiresIndex = url.indexOf("expires_in=");
            int refresh_token_Index = url.indexOf("refresh_token=");
            int uid_Index = url.indexOf("uid=");

            String token = url.substring(tokenIndex + 13, url.indexOf("&", tokenIndex));
            String expiresIn = url.substring(expiresIndex + 11, url.indexOf("&", expiresIndex));
            String refresh_token = url.substring(refresh_token_Index + 14, url.indexOf("&", refresh_token_Index));
            String uid = new String();
            if (url.contains("scope=")) {
                uid = url.substring(uid_Index + 4, url.indexOf("&", uid_Index));
            } else {
                uid = url.substring(uid_Index + 4);
            }
            System.out.println("token:"+token+",uid:"+uid);
            mTokenModel.addToken(mContext, token, expiresIn, refresh_token, uid);
            mWebActivityView.loginSuccess();
        }
    }
}
