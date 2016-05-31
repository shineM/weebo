package com.danlvse.weebo.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.danlvse.weebo.R;
import com.danlvse.weebo.utils.Constants;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //判断是否登录
        if (!AccessTokenKeeper.readAccessToken(this).isSessionValid()){

            mIntent = new Intent(WelcomeActivity.this,WebViewActivity.class);
        }else{
            mIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(mIntent);
                finish();
            }
        },1500);
    }

}
