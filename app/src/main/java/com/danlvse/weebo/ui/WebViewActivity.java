package com.danlvse.weebo.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.danlvse.weebo.R;
import com.danlvse.weebo.activity.main.MainActivity;
import com.danlvse.weebo.presenter.WebViewPresenter;
import com.danlvse.weebo.presenter.imp.WebViewPresenterImp;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.ui.view.WebActivityView;

public class WebViewActivity extends AppCompatActivity implements WebActivityView{
    private WebView mWebView;
    private String mUrl;
    private WebViewPresenter mWebViewPresenter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebViewPresenter = new WebViewPresenterImp(this);
        mContext = this;
        mUrl = Constants.authurl;
        mWebView = (WebView) findViewById(R.id.login_webview);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new MyWebViewClient());


        mWebView.loadUrl(mUrl);
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                    if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                        if (mWebView.canGoBack()) {
                            mWebView.goBack();
                        } else {
                            finish();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(WebViewActivity.this,MainActivity.class);
        intent.putExtra(MainActivity.FIRST_IN_TEXT, true);
        startActivity(intent);
        finish();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(Constants.REDIRECT_URL)) {
                view.stopLoading();
                mWebViewPresenter.handleUrl(mContext,url);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!url.equals("about:blank") && url.startsWith(Constants.REDIRECT_URL)) {
                view.stopLoading();
                mWebViewPresenter.handleUrl(mContext,url);
                return;
            }
            super.onPageStarted(view, url, favicon);
        }
    }
}
