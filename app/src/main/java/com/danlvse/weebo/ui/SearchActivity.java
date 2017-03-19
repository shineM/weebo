package com.danlvse.weebo.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.danlvse.weebo.R;
import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.presenter.SearchPresenter;
import com.danlvse.weebo.presenter.imp.SearchPresenterImp;
import com.danlvse.weebo.ui.adapter.TimelineAdapter;
import com.danlvse.weebo.ui.view.SearchView;
import com.danlvse.weebo.utils.ToastUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import static com.danlvse.weebo.R.id.search_result_empty_remainder;

public class SearchActivity extends AppCompatActivity implements SearchView {
    private Toolbar mToolbar;
    private RecyclerView searchResultList;
    private ProgressWheel loadingIcon;
    private TimelineAdapter adapter;
    private List<Feed> feeds = new ArrayList<>();
    private TextView emptyRemainder;
    private SearchPresenter presenter;
    private android.support.v7.widget.SearchView searchView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;
        loadingIcon = (ProgressWheel) findViewById(R.id.loading_progress);
        emptyRemainder = (TextView) findViewById(search_result_empty_remainder);
        presenter = new SearchPresenterImp(this);
        initToolBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        searchResultList = (RecyclerView) findViewById(R.id.search_result_list);
        adapter = new TimelineAdapter(this, feeds);
        searchResultList.setLayoutManager(new LinearLayoutManager(this));
        searchResultList.setAdapter(adapter);
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setTitle("搜索");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchView = (android.support.v7.widget.SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchWeiboByKey(query, context);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void showLoadingIcon() {
        if (loadingIcon.getVisibility() != View.VISIBLE) {
            loadingIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoadingIcon() {
        if (loadingIcon.getVisibility() == View.VISIBLE) {
            loadingIcon.setVisibility(View.GONE);
        }
        if (emptyRemainder.getVisibility() == View.VISIBLE) {
            emptyRemainder.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showEmptyInfo() {
        if (emptyRemainder.getVisibility() != View.VISIBLE) {
            emptyRemainder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showErrorInfo() {
        ToastUtil.showShort(this, "网络出错啦");
    }

    @Override
    public void updateResultList(List<Feed> results) {
        feeds = results;
        adapter.setNewData(results);
    }
}
