package com.danlvse.weebo.utils.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.User;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.model.AddWeiboModel;
import com.danlvse.weebo.ui.AddWeiboActivity;
import com.danlvse.weebo.ui.ProfileActivity;
import com.danlvse.weebo.ui.TopicActivity;
import com.danlvse.weebo.ui.adapter.ImageListAdapter;
import com.danlvse.weebo.utils.BitmapClipUtil;
import com.danlvse.weebo.utils.DateUtils;
import com.danlvse.weebo.utils.OnContentClickListener;
import com.danlvse.weebo.utils.OnKeyClick;
import com.squareup.okhttp.OkHttpClient;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by zxy on 16/5/25.
 */
public class BindViewUtil {
    public static final int IMAGE_TYPE_LONG_TEXT = 1;//长微博
    public static final int IMAGE_TYPE_LONG_PIC = 2;//比较长的微博（但是不至于像长微博那么长）
    public static final int IMAGE_TYPE_WIDTH_PIC = 3;//比较宽的微博
    public static final int IMAGE_TYPE_GIF = 4;
    private static OnKeyClick onKeyClick = new OnKeyClick() {
        @Override
        public void onUsernameClick(String s, Context context) {
            viewProfile(s, context);
        }

        @Override
        public void onTopicClick(String s, Context context) {
            viewTopic(s, context);
        }

    };
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

    public static void bindContent(TextView content, Context activity, String text, OnContentClickListener listener) {
        content.setText(WeiboContentKeyUtil.getWeiBoContent(text, activity, content, listener,onKeyClick));
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
        Glide glide = Glide.get(mContext);
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(client);
        glide.register(GlideUrl.class, InputStream.class, factory);
        if (isSingle) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imageView.getLayoutParams();
            lp.height = (int) mContext.getResources().getDimension(R.dimen.home_weiboitem_imagesize_horizontal_rectangle_height);
            lp.width = (int) mContext.getResources().getDimension(R.dimen.home_weiboitem_imagesize_horizontal_rectangle_width);
        }
        if (url.endsWith(".gif")) {
            glide.with(mContext).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
            imageType.setText("GIF");
            imageType.setVisibility(View.VISIBLE);
        } else {
            glide.with(mContext).load(url).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
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

    public static void setClick(View actionTab, final Weibo weibo, final Activity mActivity) {
        ImageView repostIcon = (ImageView) actionTab.findViewById(R.id.repost_icon);
        ImageView commentIcon = (ImageView) actionTab.findViewById(R.id.comment_icon);
        TextView repostCount = (TextView) actionTab.findViewById(R.id.repost_count);
        final AddWeiboModel.OnRepostFinished listener = new AddWeiboModel.OnRepostFinished() {
            @Override
            public void successed(Weibo weibo) {

            }

            @Override
            public void failed(String s) {

            }
        };
        repostIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddWeiboModelImp.repostWeibo(mActivity,weibo,"",0,listener);
                Intent intent = new Intent(mActivity, AddWeiboActivity.class);
                intent.putExtra(AddWeiboActivity.IS_REPOST,true);
                intent.putExtra(AddWeiboActivity.REPOST_CONTENT,"//@"+weibo.user.name+":"+weibo.text);
                intent.putExtra(AddWeiboActivity.ORIGIN_WEIBO,(Parcelable) weibo);
                mActivity.startActivity(intent);
            }
        });
        commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AddWeiboActivity.class);
                intent.putExtra(AddWeiboActivity.IS_COMMENT,true);
                intent.putExtra(AddWeiboActivity.REPOST_CONTENT,"@"+weibo.user.name+":"+weibo.text);
                intent.putExtra(AddWeiboActivity.ORIGIN_WEIBO,(Parcelable) weibo);
                mActivity.startActivity(intent);
            }
        });
    }

    /**
     * 绑定用户头像和昵称
     * @param mActivity Context
     * @param user  User
     * @param avatar ImageView
     * @param username  用户名
     */
    public static void bindUser(final Context mActivity, final User user, ImageView avatar, TextView username) {
        Glide.with(mActivity).load(user.avatar_large).bitmapTransform(new CropCircleTransformation(mActivity)).placeholder(R.mipmap.ic_person_white_24dp).into(avatar);
        username.setText(user.name);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProfile(user, mActivity);
            }
        };
        avatar.setOnClickListener(listener);
        username.setOnClickListener(listener);
    }


    private static void viewProfile(String username, Context mContext){
        Intent intent = new Intent(mContext, ProfileActivity.class);

        intent.putExtra("user",username);

        mContext.startActivity(intent);
    }
    private static void viewProfile(User user, Context mContext){
        Intent intent = new Intent(mContext, ProfileActivity.class);

        intent.putExtra("users",(Parcelable)user);

        mContext.startActivity(intent);
    }
    private static void viewTopic(String topic, Context mContext){
        Intent intent = new Intent(mContext, TopicActivity.class);

        intent.putExtra("topic",topic);

        mContext.startActivity(intent);
    }

}
