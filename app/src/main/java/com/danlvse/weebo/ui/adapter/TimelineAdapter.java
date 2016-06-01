package com.danlvse.weebo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.ui.ProfileActivity;
import com.danlvse.weebo.ui.WeiboDetailActivity;
import com.danlvse.weebo.utils.ActivityUtils;
import com.danlvse.weebo.utils.OnKeyClick;
import com.danlvse.weebo.utils.OnWeiboContentListener;
import com.danlvse.weebo.utils.weibo.BindViewUtil;

import java.util.List;

/**
 * Created by zxy on 16/5/29.
 */
public class TimelineAdapter extends BaseMultiItemQuickAdapter<Weibo> {
    private Activity mActivity;
    private static final String transition = "weibo item transition";


    public TimelineAdapter(Activity activity, List data) {
        super(activity, data);
        this.mActivity = activity;
        addItmeType(Weibo.ORIGIN, R.layout.weibo_item);
        addItmeType(Weibo.REPOST, R.layout.weibo_item_repost);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final Weibo weibo) {
        final View view;
        if (weibo.getItemType() == Weibo.ORIGIN) {
            view = baseViewHolder.getConvertView();
            final LinearLayout weiboItem = (LinearLayout) view.findViewById(R.id.weibo_item);
            BindViewUtil.bindHeaderInf((ImageView) view.findViewById(R.id.user_avatar), (TextView) view.findViewById(R.id.user_name), (TextView) view.findViewById(R.id.post_time), (TextView) view.findViewById(R.id.post_device), (TextView) view.findViewById(R.id.repost_count), (TextView) view.findViewById(R.id.comments_count), (TextView) view.findViewById(R.id.like_count), mActivity, weibo);
            BindViewUtil.bindContent((TextView) view.findViewById(R.id.weibo_content), mActivity, weibo.text, new OnWeiboContentListener() {
                @Override
                public void onTextClick() {
                    viewDetail(weibo, weiboItem, transition);
                }
            }, new OnKeyClick() {
                @Override
                public void onUsernameClick(String s) {
                    viewProfile(s);
                }

                @Override
                public void onTopicClick(String s) {
                    viewTopic(s);
                }
            });
            BindViewUtil.bindImages(mActivity, (RecyclerView) view.findViewById(R.id.weibo_images), weibo);
            view.findViewById(R.id.weibo_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDetail(weibo,weiboItem,transition);
                }
            });
        } else {
            view = baseViewHolder.getConvertView();
            final LinearLayout origin = (LinearLayout) view.findViewById(R.id.origin_weibo_item);
            final LinearLayout repost = (LinearLayout) view.findViewById(R.id.repost_weibo_item);

            //绑定转发内容
            BindViewUtil.bindHeaderInf((ImageView) view.findViewById(R.id.user_avatar), (TextView) view.findViewById(R.id.user_name), (TextView) view.findViewById(R.id.post_time), (TextView) view.findViewById(R.id.post_device), (TextView) view.findViewById(R.id.repost_count), (TextView) view.findViewById(R.id.comments_count), (TextView) view.findViewById(R.id.like_count), mActivity, weibo);
            BindViewUtil.bindContent((TextView) view.findViewById(R.id.repost_weibo_content), mActivity, weibo.text, new OnWeiboContentListener() {
                @Override
                public void onTextClick() {
                    viewDetail(weibo, repost, transition);
                }
            }, new OnKeyClick() {
                @Override
                public void onUsernameClick(String s) {
                    viewProfile(s);
                }

                @Override
                public void onTopicClick(String s) {
                    viewTopic(s);
                }
            });
            String originContent;
            if (weibo.retweeted_status.user != null) {
                originContent = "@" + weibo.retweeted_status.user.name + " : " + weibo.retweeted_status.text;
            } else {
                originContent = "抱歉，此微博已被作者删除。";
            }
            //绑定原微博内容
            BindViewUtil.bindContent((TextView) view.findViewById(R.id.origin_weibo_content), mActivity, originContent, new OnWeiboContentListener() {
                @Override
                public void onTextClick() {
                    viewDetail(weibo.retweeted_status, origin, transition);
                }
            }, new OnKeyClick() {
                @Override
                public void onUsernameClick(String s) {
                    viewProfile(s);
                }

                @Override
                public void onTopicClick(String s) {
                    viewTopic(s);
                }
            });
            BindViewUtil.bindImages(mActivity, (RecyclerView) view.findViewById(R.id.origin_weibo_image_list), weibo.retweeted_status);
            origin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, "点击了原微博：", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, WeiboDetailActivity.class);
                    intent.putExtra("weibo", (Parcelable) weibo.retweeted_status);
                    mContext.startActivity(intent);
                }
            });

            repost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, "点击了转发微博：", Toast.LENGTH_SHORT).show();
                    viewDetail(weibo,repost,transition);
                }
            });

        }
    }
    private void viewProfile(String username){
        Intent intent = new Intent(mContext, ProfileActivity.class);

        intent.putExtra("user",username);

        mActivity.startActivity(intent);
    }
    private void viewTopic(String topic){
        Intent intent = new Intent(mContext, ProfileActivity.class);

        intent.putExtra("topic",topic);

        mActivity.startActivity(intent);
    }
    private void viewDetail(Weibo weibo,View shareView,String s) {
        Intent intent = new Intent(mContext, WeiboDetailActivity.class);

        intent.putExtra("weibo", (Parcelable) weibo);

        ActivityUtils.startActivity(mActivity,intent,shareView,s);
    }
}
