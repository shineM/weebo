package com.danlvse.weebo.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.presenter.TopicPresenter;
import com.danlvse.weebo.presenter.imp.TopicPresenterImp;
import com.danlvse.weebo.ui.adapter.TimelineAdapter;
import com.danlvse.weebo.ui.view.TopicView;
import com.danlvse.weebo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends AppCompatActivity implements TopicView {

    private Toolbar mToolbar;
    private RecyclerView weiboList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TimelineAdapter adapter;
    private List<Weibo> weibos = new ArrayList<>();
    private TopicPresenter presenter;
    private String topicKey;
    private String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        extra = getIntent().getStringExtra("topic");
        topicKey = extra.substring(1,extra.length()-1);
        presenter = new TopicPresenterImp(this, this);
        initToolbar();
        initRecyclerView();
        initSwipeRefreshLayout();
        presenter.loadDatas(topicKey, this);
    }

    private void initRecyclerView() {
        weiboList = (RecyclerView) findViewById(R.id.topic_weibo_list);
        adapter = new TimelineAdapter(this, weibos);
        weiboList.setLayoutManager(new LinearLayoutManager(this));
        weiboList.setAdapter(adapter);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadDatas(topicKey,TopicActivity.this);
            }
        });
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(extra);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void showLoadingIcon() {
        if (!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoadingIcon() {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorInfo(String s) {
        ToastUtil.showShort(this,"网络出错啦！");
    }

    @Override
    public void upadteList(List<Weibo> list) {
        weibos = list;
        adapter.setNewData(list);
    }
}
