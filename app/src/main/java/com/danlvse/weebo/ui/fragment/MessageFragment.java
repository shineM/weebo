package com.danlvse.weebo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danlvse.weebo.R;
import com.danlvse.weebo.model.Comment;
import com.danlvse.weebo.presenter.MessagePresenter;
import com.danlvse.weebo.presenter.imp.MessagePresenterImp;
import com.danlvse.weebo.ui.adapter.MessageListAdapter;
import com.danlvse.weebo.ui.view.MessageView;
import com.danlvse.weebo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxy on 16/6/14.
 */
public class MessageFragment extends Fragment implements MessageView {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MessageListAdapter adapter;
    private List<Comment> comments = new ArrayList<>();
    private MessagePresenter presenter;
    private Context context = this.getContext();
    private Boolean isDatasLoaded = true;
    private Boolean isVisible = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mentions,container,false);
        context = this.getContext();
        presenter = new MessagePresenterImp(this);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.mention_list);
        initRecyclerView();
        initRefreshLayout();
        isDatasLoaded = false;
        loadData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible = true;
            loadData();
        }else {
            isVisible  = false;
        }
    }

    private void loadData() {
        if (!isDatasLoaded&&isVisible){
            presenter.getMentions(this.getContext());
            isDatasLoaded = true;
        }
    }

    private void initRefreshLayout() {

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMentions(context);
            }
        });
    }

    private void initRecyclerView() {
        adapter = new MessageListAdapter(this.getActivity(),comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
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
        isDatasLoaded = false;
        ToastUtil.showShort(context, "网络出错啦！！");
    }

    @Override
    public void updateCommentList(List<Comment> comments) {
        System.out.println("@me count:"+comments.size());
        this.comments = comments;
        adapter.setDatas(comments);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateMentionList(List<Comment> mentions) {

    }
}
