package com.danlvse.weebo.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.utils.ToastUtil;
import com.danlvse.weebo.utils.weibo.BindViewUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagesDetailActivity extends AppCompatActivity {
    public static final String PIC_IMAGE = "picture url";
    public static final String LARGE_IMAGE = "large image";
    private Toolbar mToolbar;
    private ImageView mImage;
    private LinearLayout mBottomSheet;
    private ProgressWheel mProgressIcon;
    private String url;
    private Context mContext;
    private Weibo mWeibo;
    private ImageView avatar;
    private TextView username;
    private TextView time;
    private TextView likeCount;
    private ImageView addLike;
    private int currentCount;
    private boolean isLiked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_detail);
        mContext = this;
        url = getIntent().getStringExtra(PIC_IMAGE);

        mWeibo = (Weibo) getIntent().getBundleExtra("bundle").get("weibo");
        initToolbar();
        loadImage();
        setUpOnClick();
        bindExtraInfo();
    }

    private void bindExtraInfo() {
        avatar = (ImageView) findViewById(R.id.user_avatar);
        username = (TextView) findViewById(R.id.user_name);
        time = (TextView) findViewById(R.id.post_time);
        likeCount = (TextView) findViewById(R.id.like_count);
        addLike = (ImageView) findViewById(R.id.add_like);
        mBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        Glide.with(mContext).load(mWeibo.user.avatar_large).bitmapTransform(new CropCircleTransformation(mContext)).into(avatar);
        username.setText(mWeibo.user.name);
        time.setText(BindViewUtil.formatDate(mWeibo.created_at));
        currentCount = mWeibo.attitudes_count;
        likeCount.setText(currentCount + "");
        addLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.thumb_up_scale);
                v.startAnimation(animation);
                if (!isLiked) {
                    likeCount.setText(currentCount + 1 + "");

                    ToastUtil.showShort(mContext, "点赞成功");
                    isLiked = true;
                } else {
                    likeCount.setText(currentCount + "");

                    ToastUtil.showShort(mContext, "你已取消了赞");
                    isLiked = false;
                }
            }
        });

    }

    private void setUpOnClick() {

    }
    private RequestListener gifListener = new RequestListener() {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            ToastUtil.showShort(mContext, "图片加载失败");
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            mProgressIcon.setVisibility(View.GONE);
            return false;
        }
    };
   private RequestListener listener =  new RequestListener() {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            url = url.replace("/large/", "/bmiddle/");
            Glide.with(mContext).load(url).listener(new RequestListener() {
                @Override
                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                    ToastUtil.showShort(mContext, "图片加载失败");
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mProgressIcon.setVisibility(View.GONE);
                    return false;
                }
            }).into(mImage);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            mProgressIcon.setVisibility(View.GONE);
            return false;
        }
    };
    //TODO:GIF加载问题
    private void loadImage() {
        mProgressIcon = (ProgressWheel) findViewById(R.id.loading_progress);
        mImage = (ImageView) findViewById(R.id.image_detail);
        url = url.replace("/bmiddle/", "/large/");
        if (url.endsWith(".gif")){
            Glide.with(this).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.NONE).listener(gifListener).into(mImage);
        }else{
            Glide.with(this).load(url).asBitmap().fitCenter().listener(listener).into(mImage);

        }
        PhotoViewAttacher attacher = new PhotoViewAttacher(mImage);
        attacher.setScaleType(ImageView.ScaleType.FIT_CENTER);

        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {

            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        attacher.update();
    }

    private void initToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
