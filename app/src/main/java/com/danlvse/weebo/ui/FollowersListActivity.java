package com.danlvse.weebo.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.danlvse.weebo.R;
import com.danlvse.weebo.data.User;
import com.danlvse.weebo.presenter.FollowersListPresenter;
import com.danlvse.weebo.presenter.imp.FollowerListPresenterImp;
import com.danlvse.weebo.ui.adapter.UserListAdapter;
import com.danlvse.weebo.ui.view.FollowersView;
import com.danlvse.weebo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxy on 16/6/10.
 */
public class FollowersListActivity extends AppCompatActivity implements FollowersView{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView followersList;
    private String mUsername;
    private Toolbar mToolbar;
    private UserListAdapter mUserListAdapter;
    private List<User> followers = new ArrayList<>();
    private FollowersListPresenter presenter;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile_followers);
        super.onCreate(savedInstanceState);
        presenter = new FollowerListPresenterImp(this, this);
        mUsername = getIntent().getStringExtra("user");
        initRecyclerView();
        initToolbar();
        initRefreshLayout();
        presenter.getFollowers(this, mUsername);
    }

    private void initRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getFollowers(FollowersListActivity.this, mUsername);
            }
        });
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initRecyclerView() {
        followersList = (RecyclerView) findViewById(R.id.followers_list);
        mUserListAdapter = new UserListAdapter(this, followers);
        followersList.setLayoutManager(new LinearLayoutManager(this));
        followersList.setAdapter(mUserListAdapter);
    }

    @Override
    public void showLoadingIcon() {
        if (!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoadingIcon() {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorInfo(String s) {
        ToastUtil.showShort(this, "网络出错啦！");
    }

    @Override
    public void updateList(List<User> users) {
        followers = users;
        mUserListAdapter.setDatas(users);
        mUserListAdapter.notifyDataSetChanged();

    }
}
