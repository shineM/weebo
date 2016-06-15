package com.danlvse.weebo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.presenter.MessagePresenter;
import com.danlvse.weebo.ui.adapter.MessageListAdapter;
import com.danlvse.weebo.ui.adapter.MessagePagerAdapter;
import com.danlvse.weebo.ui.fragment.MentionsFragment;
import com.danlvse.weebo.ui.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private RecyclerView commentsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Comment> comments = new ArrayList<>();
    private Toolbar mToolbar;
    private MessageListAdapter adapter;
    private MessagePresenter presenter;
    private Context mContext;
    private ViewPager viewPager;
    private MessagePagerAdapter pagerAdapter;
    private PagerTabStrip strip;
    private List<Fragment> fragments = new ArrayList<>();
    private MentionsFragment mentionsFragment;
    private MessageFragment messageFragment;
    private PagerSlidingTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mContext = this;
        initToolbar();
        initViewPager();

    }

    private void initViewPager() {
        mentionsFragment = new MentionsFragment();
        messageFragment = new MessageFragment();
        fragments.add(mentionsFragment);
        fragments.add(messageFragment);
        viewPager = (ViewPager) findViewById(R.id.message_viewpager);
        strip = (PagerTabStrip) findViewById(R.id.message_pager_strip);
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.slide_strip);
        pagerAdapter = new MessagePagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);
        tabStrip.setViewPager(viewPager);

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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
