package com.danlvse.weebo.utils.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.cesards.cropimageview.CropImageView;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.ui.ProfileActivity;
import com.danlvse.weebo.ui.adapter.ImageListAdapter;
import com.danlvse.weebo.utils.BitmapClipUtil;
import com.danlvse.weebo.utils.DateUtils;
import com.danlvse.weebo.utils.OnKeyClick;
import com.danlvse.weebo.utils.OnWeiboContentListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by zxy on 16/5/25.
 */
public class BindViewUtil {
    public static final int IMAGE_TYPE_LONG_TEXT = 1;//长微博
    public static final int IMAGE_TYPE_LONG_PIC = 2;//比较长的微博（但是不至于像长微博那么长）
    public static final int IMAGE_TYPE_WIDTH_PIC = 3;//比较宽的微博
    public static final int IMAGE_TYPE_GIF = 4;

    public static void bindHeaderInf(ImageView avatar, TextView username, TextView postTime, TextView postDevice, TextView repostCount, TextView commentCount, TextView likeCount, final Activity activity,final Weibo weibo) {
        Glide.with(activity).load(weibo.user.avatar_large).bitmapTransform(new CropCircleTransformation(activity)).placeholder(R.mipmap.ic_person_white_24dp).into(avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add shareElements transition
                Intent intent = new Intent(activity, ProfileActivity.class);
                intent.putExtra("users",(Parcelable) weibo.user);
                activity.startActivity(intent);
            }
        });
        username.setText(weibo.user.name);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add shareElements transition
                Intent intent = new Intent(activity, ProfileActivity.class);
                intent.putExtra("users",(Parcelable) weibo.user);
                activity.startActivity(intent);
            }
        });
        postTime.setText(formatDate(weibo.created_at));
        postDevice.setText(weibo.source);
        commentCount.setText(weibo.comments_count + "");
        likeCount.setText(weibo.attitudes_count + "");
        repostCount.setText(weibo.reposts_count + "");

    }

    public static void bindCommentHeader(ImageView avatar, TextView username, TextView postTime, TextView content, Comment comment, Context mContext) {
        Glide.with(mContext).load(comment.user.avatar_large).bitmapTransform(new CropCircleTransformation(mContext)).placeholder(R.mipmap.ic_person_white_24dp).into(avatar);
        username.setText(comment.user.name);
        postTime.setText(formatDate(comment.created_at));
        content.setText(WeiboContentKeyUtil.getWeiBoContent(comment.text, mContext, content));
    }

    public static String formatDate(String created_at) {
        Date date = DateUtils.parseDate(created_at, DateUtils.WeiBo_ITEM_DATE_FORMAT);
        String s = new SimpleDateFormat("MM-dd HH:mm").format(date);
        return s;
    }

    public static void bindContent(TextView content, Context mContext, String text, OnWeiboContentListener listener, OnKeyClick onKeyClick) {
        content.setText(WeiboContentKeyUtil.getWeiBoContent(text, mContext, content, listener,onKeyClick));
    }

    public static void bindImages(Activity mContext, RecyclerView imgList, Weibo weibo) {
        imgList.setVisibility(View.GONE);
        imgList.setVisibility(View.VISIBLE);
        ArrayList<String> origin_pic_urls = weibo.origin_pic_urls;
        if (origin_pic_urls == null || origin_pic_urls.size() == 0) {
            imgList.setVisibility(View.GONE);
        } else {
            GridLayoutManager gridLayoutManager = initGridLayoutManager(origin_pic_urls, mContext);
            ImageListAdapter adapter = new ImageListAdapter(weibo, origin_pic_urls, mContext);
            imgList.setLayoutManager(gridLayoutManager);
            imgList.setAdapter(adapter);
            adapter.setData(origin_pic_urls);
        }

    }

    private static GridLayoutManager initGridLayoutManager(ArrayList<String> urls, Context mContext) {
        GridLayoutManager gm;
        if (urls != null) {
            switch (urls.size()) {
                case 1:
                    gm = new GridLayoutManager(mContext, 1);
                    break;
                case 2:
                    gm = new GridLayoutManager(mContext, 2);
                    break;
                case 3:
                    gm = new GridLayoutManager(mContext, 3);
                    break;
                case 4:
                    gm = new GridLayoutManager(mContext, 2);
                    break;
                default:
                    gm = new GridLayoutManager(mContext, 3);
                    break;

            }
        } else {
            gm = new GridLayoutManager(mContext, 3);
        }
        return gm;
    }

    public static void loadImages(final Activity mContext, String url, final ImageView imageView, final TextView imageType, final Boolean isSingle) {
        if (isSingle) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imageView.getLayoutParams();
            lp.height = (int) mContext.getResources().getDimension(R.dimen.home_weiboitem_imagesize_horizontal_rectangle_height);
            lp.width = (int) mContext.getResources().getDimension(R.dimen.home_weiboitem_imagesize_horizontal_rectangle_width);
        }
        if (url.endsWith(".gif")) {
            Glide.with(mContext).load(url).asBitmap().into(imageView);
            imageType.setText("GIF");
            imageType.setVisibility(View.VISIBLE);
        } else {
            Glide.with(mContext).load(url).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int width = resource.getWidth();
                    int height = resource.getHeight();
                    //TODO:NOTE:显示之前获取原图参数，动态加载;centerCrop()避免长时间加载未响应
                    //TODO:？高清长图的加载
                    if (height < width * 3) {
                        imageView.setImageBitmap(resource);
                    } else {
                        imageType.setText("长");
                        imageType.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(BitmapClipUtil.centerSquareScaleBitmap(resource, (int) mContext.getResources().getDimension(R.dimen.home_weiboitem_imagesize_horizontal_rectangle_height)));


                    }

                }
            });
        }

    }
}
