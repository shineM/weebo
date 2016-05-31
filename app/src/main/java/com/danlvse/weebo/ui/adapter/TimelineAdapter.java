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
import com.danlvse.weebo.ui.WeiboDetailActivity;
import com.danlvse.weebo.utils.OnWeiboContentListener;
import com.danlvse.weebo.utils.weibo.BindViewUtil;

import java.util.List;

/**
 * Created by zxy on 16/5/29.
 */
public class TimelineAdapter extends BaseMultiItemQuickAdapter<Weibo> {
    private Activity mActivity;


    public TimelineAdapter(Activity activity, List data) {
        super(activity, data);
        this.mActivity = activity;
        addItmeType(Weibo.ORIGIN, R.layout.weibo_item);
        addItmeType(Weibo.REPOST, R.layout.weibo_item_repost);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final Weibo weibo) {
        View view;
        if (weibo.getItemType() == Weibo.ORIGIN) {
            view = baseViewHolder.getConvertView();
            BindViewUtil.bindHeaderInf((ImageView) view.findViewById(R.id.user_avatar), (TextView) view.findViewById(R.id.user_name), (TextView) view.findViewById(R.id.post_time), (TextView) view.findViewById(R.id.post_device), (TextView) view.findViewById(R.id.repost_count), (TextView) view.findViewById(R.id.comments_count), (TextView) view.findViewById(R.id.like_count), mContext, weibo);
            BindViewUtil.bindContent((TextView) view.findViewById(R.id.weibo_content), mContext, weibo.text, new OnWeiboContentListener() {
                @Override
                public void onTextClick() {
                    viewDetail(weibo);
                }
            });
            BindViewUtil.bindImages(mActivity, (RecyclerView) view.findViewById(R.id.weibo_images), weibo);
            view.findViewById(R.id.weibo_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDetail(weibo);
                }
            });
        } else {
            view = baseViewHolder.getConvertView();
            LinearLayout origin = (LinearLayout) view.findViewById(R.id.origin_weibo_item);
            LinearLayout repost = (LinearLayout) view.findViewById(R.id.repost_weibo_item);

            //绑定转发内容
            BindViewUtil.bindHeaderInf((ImageView) view.findViewById(R.id.user_avatar), (TextView) view.findViewById(R.id.user_name), (TextView) view.findViewById(R.id.post_time), (TextView) view.findViewById(R.id.post_device), (TextView) view.findViewById(R.id.repost_count), (TextView) view.findViewById(R.id.comments_count), (TextView) view.findViewById(R.id.like_count), mContext, weibo);
            BindViewUtil.bindContent((TextView) view.findViewById(R.id.repost_weibo_content), mContext, weibo.text, new OnWeiboContentListener() {
                @Override
                public void onTextClick() {
                    viewDetail(weibo);
                }
            });
            String originContent = null;
            if (weibo.retweeted_status.user != null) {
                originContent = "@" + weibo.retweeted_status.user.name + " : " + weibo.retweeted_status.text;
            } else {
                originContent = "抱歉，此微博已被作者删除。";
            }
            //绑定原微博内容
            BindViewUtil.bindContent((TextView) view.findViewById(R.id.origin_weibo_content), mContext, originContent, new OnWeiboContentListener() {
                @Override
                public void onTextClick() {
                    viewDetail(weibo.retweeted_status);
                }
            });
            BindViewUtil.bindImages(mActivity, (RecyclerView) view.findViewById(R.id.origin_weibo_image_list), weibo.retweeted_status);
            origin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "点击了原微博：", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, WeiboDetailActivity.class);
                    intent.putExtra("weibo", (Parcelable) weibo.retweeted_status);
                    mContext.startActivity(intent);
                }
            });

            repost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "点击了转发微博：", Toast.LENGTH_SHORT).show();
                    viewDetail(weibo);
                }
            });

        }
    }

    private void viewDetail(Weibo weibo) {
        Intent intent = new Intent(mContext, WeiboDetailActivity.class);

        intent.putExtra("weibo", (Parcelable) weibo);

        mContext.startActivity(intent);
    }
}
