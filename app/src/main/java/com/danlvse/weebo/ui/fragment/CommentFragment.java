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
import android.widget.LinearLayout;

import com.danlvse.weebo.R;
import com.danlvse.weebo.model.Comment;
import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.presenter.CommentsFragmentPresenter;
import com.danlvse.weebo.presenter.imp.CommentsFragmentPresenterImp;
import com.danlvse.weebo.ui.adapter.CommentListAdapter;
import com.danlvse.weebo.ui.widget.SlidingTabLayout;
import com.danlvse.weebo.ui.view.CommentsFragmentView;

import java.util.ArrayList;

/**
 * Created by zxy on 16/5/27.
 */
public class CommentFragment extends Fragment implements CommentsFragmentView {
    private SwipeRefreshLayout mRefreshLayout;

    private SlidingTabLayout mTabLayout;
    private LinearLayout emptyComment;
    private Feed feed;
    private RecyclerView mRecyclerview;
    private Context mContext;
    private CommentsFragmentPresenter presenter;
    private CommentListAdapter adapter;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private OnDataRefresh onDataRefresh;

    public CommentFragment() {
    }

    public interface OnDataRefresh {
        void refreshing();

        void refreshed();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty_comment, container, false);
        feed = getArguments().getParcelable("comments");
        mContext = getActivity();
        mRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh);
        mTabLayout = (SlidingTabLayout) getActivity().findViewById(R.id.sliding_tabs);
        presenter = new CommentsFragmentPresenterImp(this, mContext);

        initRecyclerView(view);
        presenter.loadComment(mContext, feed);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void initRecyclerView(View view) {
        emptyComment = (LinearLayout) view.findViewById(R.id.commment_empty_remainder);
        adapter = new CommentListAdapter(comments, mContext);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.comment_list);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerview.setAdapter(adapter);

    }
    public static CommentFragment newInstance(Bundle bundle) {
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void refreshData(OnDataRefresh onDataRefresh) {
        this.onDataRefresh = onDataRefresh;
        presenter.loadComment(mContext, feed);
    }

    @Override
    public void showLoadingIcon() {
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoadingIcon() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorInfo(String string) {
        System.out.println(string);
    }

    @Override
    public void showLoadingMoreIcon() {

    }

    @Override
    public void updataList(ArrayList<Comment> list) {

        comments = list;
        if (comments.size() == 0) {
            emptyComment.setVisibility(View.VISIBLE);
        } else {
            emptyComment.setVisibility(View.GONE);
        }
        adapter.setList(comments);
        adapter.notifyDataSetChanged();
    }
}
