package com.danlvse.weebo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danlvse.weebo.R;
import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.ui.adapter.CommentPagerAdapter;
import com.danlvse.weebo.ui.fragment.CommentFragment;
import com.danlvse.weebo.ui.fragment.LikeFragment;
import com.danlvse.weebo.ui.fragment.RepostFragment;
import com.danlvse.weebo.ui.widget.SlidingTabLayout;
import com.danlvse.weebo.utils.OnContentClickListener;
import com.danlvse.weebo.utils.weibo.BindViewUtil;

import java.util.ArrayList;
import java.util.List;

public class WeiboDetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private SwipeRefreshLayout mRefreshLayout;

    private Feed mFeed;
    private Feed originFeed;
    private Toolbar mToolbar;
    private ImageView avatar;
    private TextView username;
    private TextView postTime;
    private TextView postDevice;
    private TextView commentCount;
    private TextView repostCount;
    private TextView likeCount;
    private TextView content;
    private RecyclerView imgList;
    private TextView originWeiboContent;
    private LinearLayout originWeiboItem;
    private Context mContext;
    private ViewPager commentPager;
    private PagerTabStrip tabStrip;
    private CommentPagerAdapter commentPagerAdapter;
    private List<Fragment> mFragments;
    private CommentFragment commentsFragment;
    private Fragment repostFragment;
    private Fragment likesFragment;
    private SlidingTabLayout mTabLayout;
    private NestedScrollView nestedScrollView;
    private View actionTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);
        mContext = this;
        mFeed = getIntent().getParcelableExtra("weibo");

        initToolbar();
        initRefreshLayout();

        initWeiboData();
        initCommentPagerView();
    }

    private CommentFragment.OnDataRefresh myOnDataRefresh = new CommentFragment.OnDataRefresh() {
        @Override
        public void refreshing() {
            if (!mRefreshLayout.isRefreshing()) {
                mRefreshLayout.setRefreshing(true);
            }
        }

        @Override
        public void refreshed() {
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.setRefreshing(false);
            }
        }
    };

    private void initRefreshLayout() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentsFragment.refreshData(myOnDataRefresh);
            }
        });
    }

    private void initCommentPagerView() {
        mFragments = new ArrayList<Fragment>();
        Bundle bundle = new Bundle();
        bundle.putParcelable("comments", mFeed);
        commentsFragment = CommentFragment.newInstance(bundle);

        repostFragment = new RepostFragment();
        likesFragment = new LikeFragment();
        mFragments.add(commentsFragment);

        mFragments.add(repostFragment);
        mFragments.add(likesFragment);
        commentPagerAdapter = new CommentPagerAdapter(getSupportFragmentManager(), mFragments);
        commentPager = (ViewPager) findViewById(R.id.comment_pager);
        commentPager.setAdapter(commentPagerAdapter);
        commentPager.setOnPageChangeListener(this);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setDistributeEvenly(true);
        mTabLayout.setViewPager(commentPager);
        mRefreshLayout.scrollTo(0, 0);
    }

    private void initWeiboData() {
        actionTab = findViewById(R.id.action_tab);
        avatar = (ImageView) findViewById(R.id.user_avatar);
        username = (TextView) findViewById(R.id.user_name);
        postTime = (TextView) findViewById(R.id.post_time);
        postDevice = (TextView) findViewById(R.id.post_device);
        commentCount = (TextView) findViewById(R.id.comments_count);
        repostCount = (TextView) findViewById(R.id.repost_count);
        likeCount = (TextView) findViewById(R.id.like_count);
        content = (TextView) findViewById(R.id.weibo_content);
        originWeiboItem = (LinearLayout) findViewById(R.id.origin_weibo);
        BindViewUtil.setClick(actionTab, mFeed,WeiboDetailActivity.this);
        if (mFeed.retweeted_status == null) {
            imgList = (RecyclerView) findViewById(R.id.image_list);

            originWeiboItem.setVisibility(View.GONE);
            BindViewUtil.bindHeaderInf(avatar, username, postTime, postDevice, repostCount, commentCount, likeCount, this, mFeed);
            BindViewUtil.bindContent(content, this, mFeed.text, new OnContentClickListener() {
                @Override
                public void onTextClick() {
                    return;
                }
            });
            BindViewUtil.bindImages(this, imgList, mFeed);


        } else {
            BindViewUtil.bindHeaderInf(avatar, username, postTime, postDevice, repostCount, commentCount, likeCount, this, mFeed);
            BindViewUtil.bindContent(content, this, mFeed.text, new OnContentClickListener() {
                @Override
                public void onTextClick() {
                    return;
                }
            });
            String originContent = null;
            if (mFeed.retweeted_status.user != null) {
                originContent = "@" + mFeed.retweeted_status.user.name + " : " + mFeed.retweeted_status.text;
            } else {
                originContent = "抱歉，此微博已被作者删除。";
            }
            imgList = (RecyclerView) findViewById(R.id.origin_weibo_image_list);
            originWeiboContent = (TextView) findViewById(R.id.origin_weibo_content);
            BindViewUtil.bindContent(originWeiboContent, this, originContent, new OnContentClickListener() {
                @Override
                public void onTextClick() {
                    Intent intent = new Intent(mContext, WeiboDetailActivity.class);
                    intent.putExtra("weibo", (Parcelable) mFeed.retweeted_status);
                    mContext.startActivity(intent);
                }
            });
            BindViewUtil.bindImages(this, imgList, mFeed.retweeted_status);
            originWeiboItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WeiboDetailActivity.class);
                    intent.putExtra("weibo", (Parcelable) mFeed.retweeted_status);
                    mContext.startActivity(intent);
                }
            });
        }

    }


    protected void initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setBackgroundDrawableResource(R.drawable.add_weibo_background);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.weibo_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
