package com.danlvse.weebo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.presenter.MainActivityPresenter;
import com.danlvse.weebo.presenter.imp.MainActivityPresenterImp;
import com.danlvse.weebo.ui.adapter.TimelineAdapter;
import com.danlvse.weebo.ui.adapter.WeiboListAdapter;
import com.danlvse.weebo.utils.ActivityUtils;
import com.danlvse.weebo.utils.ToastUtil;
import com.danlvse.weebo.utils.weibo.AccessTokenKeeper;
import com.danlvse.weebo.ui.view.MainActivityView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainActivityView, BaseQuickAdapter.RequestLoadMoreListener {
    public static final String FIRST_IN_TEXT = "first in";
    private int mCurrentCount = 0;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DrawerLayout mDrawerLayout;
    private LinearLayout categoryLayout;
    private TextView categoryText;
    private ImageView expandIcon;
    private PopupMenu popup;
    private NavigationView mNav;
    private Boolean isFirstIn;
    private View profileView;
    private Handler mHander;
    private RecyclerView mRecyclerView;
    private List<Weibo> mWeibos;
    private WeiboListAdapter mAapter;
    //重构了Adapter，支持上拉加载更多
    private TimelineAdapter newAdapter;
    private MainActivityPresenter mMainActivityPresenter;
    private Context mContext;
    private FloatingActionButton mFab;
    private ImageView headerAvatar;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.weibo_timeline);
        mMainActivityPresenter = new MainActivityPresenterImp(this);
        mHander = new Handler();
        isFirstIn = getIntent().getBooleanExtra(FIRST_IN_TEXT, false);
        System.out.println(AccessTokenKeeper.readAccessToken(this).getUid());
        mNav = (NavigationView) findViewById(R.id.nav_view);
        setUpSwipeRefresh();
        initToolbar();
        initDrawer();
        initRecyclerView();
        initFab();
        mMainActivityPresenter.loadDatas(0, mContext, isFirstIn);

    }

    private void initFab() {
        mFab = (FloatingActionButton) findViewById(R.id.main_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
                mFab.setVisibility(View.GONE);
            }
        });
    }

    private void setUpSwipeRefresh() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMainActivityPresenter.refresh(0, mContext);
            }
        });
    }

    private void initRecyclerView() {
        mWeibos = new ArrayList<Weibo>();
        mAapter = new WeiboListAdapter(mWeibos, this);
        newAdapter = new TimelineAdapter(this, mWeibos);
        newAdapter.setOnLoadMoreListener(this);
        newAdapter.openLoadMore(4, true);
        newAdapter.setLoadingView(LayoutInflater.from(mContext).inflate(R.layout.view_more_progress, null));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        newAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setAdapter(newAdapter);

    }


    @Override
    protected void initToolbar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        expandIcon = (ImageView) findViewById(R.id.category_expand_icon);
        categoryText = (TextView) findViewById(R.id.category_text);
        categoryLayout = (LinearLayout) findViewById(R.id.category_expand);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO：增加popup展开收缩动画
                popupCategoryWindow(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.new_weibo){
            Intent intent = new Intent(MainActivity.this,AddWeiboActivity.class);

            startActivity(intent);
        }
        return true;
    }

    //弹出微博分类菜单
    private void popupCategoryWindow(View view) {
        popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_category, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.category_home:
                        Toast.makeText(getApplicationContext(), "全部动态", Toast.LENGTH_SHORT).show();
                        categoryText.setText(R.string.category_home);
                        break;
                    case R.id.category_special:
                        Toast.makeText(getApplicationContext(), "特别关注", Toast.LENGTH_SHORT).show();
                        categoryText.setText(R.string.category_special);
                        break;
                    case R.id.category_friends:
                        Toast.makeText(getApplicationContext(), "朋友圈", Toast.LENGTH_SHORT).show();
                        categoryText.setText(R.string.category_friends);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popup.show();
        expandIcon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.category_icon_anim_expand));
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                expandIcon.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.category_icon_anim_coll));
            }
        });
    }

    //初始化侧滑栏
    private void initDrawer() {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        mMainActivityPresenter.loadDatas(0, mContext, isFirstIn);
                        break;
                    case R.id.nav_message:
                        Toast.makeText(getApplicationContext(), "message", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_search:
                        Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(getApplicationContext(), "全部动态", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_about:
                        Toast.makeText(getApplicationContext(), "全部动态", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer_string, R.string.open_drawer_string);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        final View headerView = mNav.getHeaderView(0);
        profileView = headerView.findViewById(R.id.profile_view);
        headerAvatar = (ImageView) headerView.findViewById(R.id.drawer_header_avatar);
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                delayStartActivity(mActivity,new Intent(MainActivity.this, ProfileActivity.class),headerView,mContext.getResources().getString(R.string.transiton_avatar));
            }
        });
    }

    //侧滑栏延时跳转
    private void delayStartActivity(final Activity activity, final Intent intent,final View view, final String transition) {
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.startActivity(activity,intent,view,transition);

            }
        }, 300);
    }

    //所有侧边Activity以当前为ParentActivity
    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNav)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    //刷新列表
    @Override
    public void refreshData(List<Weibo> weibos) {
        System.out.println("-------------微博数量：" + weibos.size());
        mWeibos = weibos;
        newAdapter.setNewData(mWeibos);
    }

    @Override
    public void hideRefreshIcon() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showRefreshIcon() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void showMoreData(List<Weibo> weibos) {
        System.out.println("-------------新增微博数量：" + weibos.size());
        newAdapter.notifyDataChangedAfterLoadMore(weibos, true);
        int newCount = weibos.size();
        int lastIndex = newAdapter.getItemCount() - 2;
        for (int i = 0; i < newCount; i++) {
            newAdapter.remove(lastIndex);
            lastIndex -= 1;
        }
        mCurrentCount = newAdapter.getData().size();
        System.out.println("-------------Adapter微博数量：" + newAdapter.getData().size());

    }

    @Override
    public void remainMoreData() {
        ToastUtil.showShort(mContext, "亲，没有最新微博啦！");
    }

    @Override
    public void showErrorInfo(String error) {
        ToastUtil.showShort(mContext, "亲，访问出错啦啦！");
    }

    //Recyclerview Adapter回调接口方法
    @Override
    public void onLoadMoreRequested() {
//        new AsyncTask<String, Integer, List>() {
//            @Override
//            protected List doInBackground(String... params) {
//                return mMainActivityPresenter.getNextPage(mContext);
//            }
//
//            @Override
//            protected void onPostExecute(List list) {
//                newAdapter.notifyDataChangedAfterLoadMore(list, true);
//                mCurrentCount = newAdapter.getData().size();
//            }
//        }.execute();
        //TODO:MVP重构标记
        mMainActivityPresenter.getNextPage(mContext);
    }
}
