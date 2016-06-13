package com.danlvse.weebo.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.ui.view.MessageView;

import java.util.List;

public class MessageActivity extends AppCompatActivity implements MessageView{
    private RecyclerView commentsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Comment> comments;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("我的消息");
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

    }

    @Override
    public void hideLoadingIcon() {

    }

    @Override
    public void showErrorInfo(String s) {

    }

    @Override
    public void updateCommentList(List<Comment> comments) {

    }

    @Override
    public void updateMentionList(List<Comment> mentions) {

    }
}
