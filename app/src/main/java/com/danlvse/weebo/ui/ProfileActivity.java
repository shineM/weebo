package com.danlvse.weebo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.danlvse.weebo.R;
import com.danlvse.weebo.model.TokenModel;
import com.danlvse.weebo.model.imp.TokenModelImp;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;

/**
 * Created by zxy on 16/5/25.
 */
public class ProfileActivity extends BaseActivity {
    Button logoutButton;
    TextView accountText;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;
        logoutButton = (Button) findViewById(R.id.logout_btn);
        accountText = (TextView) findViewById(R.id.account_text);
        accountText.setText(AccessTokenKeeper.readAccessToken(this).getUid());
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenModel tokenModelImp = new TokenModelImp();
                tokenModelImp.deleteToken(context);
                Intent intent = new Intent(ProfileActivity.this, WebViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initToolbar() {

    }
}
