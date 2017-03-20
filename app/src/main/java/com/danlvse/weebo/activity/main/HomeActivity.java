package com.danlvse.weebo.activity.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danlvse.weebo.R;
import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.ui.BaseActivity;

import java.util.List;

/**
 * Created by zxy on 17/3/19.
 */

public class HomeActivity extends AppCompatActivity implements HomeContract.View{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void updateFeedList(List<Feed> feedList) {

    }

    @Override
    public void appendFeeds(List<Feed> feeds) {

    }

    @Override
    public void showErrorView(boolean shouldShow) {

    }

    @Override
    public void showException(String exception) {

    }

    @Override
    public void showLoadingMore(boolean shouldShow) {

    }

    @Override
    public void showLoadingMoreError(boolean shouldShow) {

    }

    @Override
    public void showLoadingMoreEnd(boolean shouldShow) {

    }
}
