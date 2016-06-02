package com.danlvse.weebo.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.User;
import com.danlvse.weebo.model.TokenModel;
import com.danlvse.weebo.model.imp.TokenModelImp;
import com.danlvse.weebo.presenter.ProfilePresenter;
import com.danlvse.weebo.presenter.imp.ProfilePresenterImp;
import com.danlvse.weebo.ui.view.ProfileView;
import com.danlvse.weebo.utils.ToastUtil;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.pnikosis.materialishprogress.ProgressWheel;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by zxy on 16/5/25.
 */
public class ProfileActivity extends BaseActivity implements ProfileView {

    private Context context;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView avatar;
    private TextView followCount;
    private TextView followerCount;
    private TextView sign;
    private ImageView background;
    private ProfilePresenter profilePresenter;
    private String username;
    private ProgressWheel progressWheel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;
        bindView();
        profilePresenter = new ProfilePresenterImp(this);
        String s = getIntent().getStringExtra("user");
        User user = getIntent().getParcelableExtra("users");
        if (!TextUtils.isEmpty(s)) {
            username = s.substring(1);
            profilePresenter.getUser(this, username);
            collapsingToolbarLayout.setTitle(username);
        } else if (user != null) {
            bindProfile(user);
            hideLoadingIcon();
            collapsingToolbarLayout.setTitle(user.name);
        }
        initMaterialStyle();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.profile_page_username_size);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL);
        collapsingToolbarLayout.setExpandedTitleMarginTop((int) getResources().getDimension(R.dimen.profile_page_username_margin_top));

    }

    private void initMaterialStyle() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setBackgroundDrawableResource(R.drawable.add_weibo_background);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void bindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        avatar = (ImageView) findViewById(R.id.profile_activity_avatar);
        followCount = (TextView) findViewById(R.id.profile_follow_count);
        followerCount = (TextView) findViewById(R.id.profile_follower_count_count);
        sign = (TextView) findViewById(R.id.profile_sign);
        background = (ImageView) findViewById(R.id.profile_user_background);
        progressWheel = (ProgressWheel) findViewById(R.id.loading_progress);

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void bindProfile(User user) {
        Glide.with(this).load(user.avatar_large).placeholder(R.mipmap.ic_person_white_24dp).bitmapTransform(new CropCircleTransformation(this)).into(avatar);
        followerCount.setText("粉丝  " + user.followers_count + "");
        followCount.setText("关注  " + user.friends_count);
        sign.setText(user.description);

    }

    @Override
    public void showErrorInfo() {
        ToastUtil.showShort(this, "访问出错啦，请重试");
    }

    @Override
    public void showLoadingIcon() {
        if (progressWheel.getVisibility() != View.VISIBLE) {
            progressWheel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoadingIcon() {
        if (progressWheel.getVisibility() == View.VISIBLE) {
            progressWheel.setVisibility(View.GONE);
        }
    }
}
