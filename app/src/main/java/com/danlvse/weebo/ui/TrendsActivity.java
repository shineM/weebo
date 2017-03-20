package com.danlvse.weebo.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.danlvse.weebo.R;
import com.danlvse.weebo.model.Topic;
import com.danlvse.weebo.presenter.TrendsPresenter;
import com.danlvse.weebo.presenter.imp.TrendPresenterImp;
import com.danlvse.weebo.ui.adapter.TrendsAdapter;
import com.danlvse.weebo.ui.view.TrendsView;
import com.danlvse.weebo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class TrendsActivity extends AppCompatActivity implements TrendsView{
    private Toolbar mToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView topicList;
    private TrendsAdapter adapter;
    private List<Topic> mTopicList = new ArrayList<>();
    private TrendsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);
        presenter = new TrendPresenterImp(this);
        initToolbar();
        initRecyclerView();
        initRefreshLayout();
        presenter.getDailyTrends(this);
    }

    private void initRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getDailyTrends(TrendsActivity.this);
            }
        });
    }

    private void initRecyclerView() {
        topicList = (RecyclerView) findViewById(R.id.topic_list);
        adapter = new TrendsAdapter(mTopicList,this);
        topicList.setLayoutManager(new GridLayoutManager(this,3));
        topicList.setAdapter(adapter);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setTitle("热门话题");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        ToastUtil.showShort(this, "网络出问题啦！");
    }

    @Override
    public void updateList(List<Topic> topics) {
        ToastUtil.showShort(this, "upadated!");
        mTopicList = topics;
        adapter.setDatas(topics);
        adapter.notifyDataSetChanged();

    }
}
