package com.danlvse.weebo.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.danlvse.weebo.R;
import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.ui.ProfileActivity;
import com.danlvse.weebo.ui.TopicActivity;
import com.danlvse.weebo.ui.WeiboDetailActivity;
import com.danlvse.weebo.utils.ActivityUtils;
import com.danlvse.weebo.utils.OnContentClickListener;
import com.danlvse.weebo.utils.weibo.BindViewUtil;

import java.util.List;

/**
 * Created by zxy on 16/5/29.
 */
public class TimelineAdapter extends BaseMultiItemQuickAdapter<Feed> {
    private Activity mActivity;
    private static final String transition = "weibo item transition";


    public TimelineAdapter(Activity activity, List data) {
        super(activity, data);
        this.mActivity = activity;
        addItmeType(Feed.ORIGIN, R.layout.weibo_item);
        addItmeType(Feed.REPOST, R.layout.weibo_item_repost);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final Feed feed) {
        final View view = baseViewHolder.getConvertView();
        final LinearLayout weiboItem = (LinearLayout) view.findViewById(R.id.weibo_item);
        LinearLayout actionTab = (LinearLayout) view.findViewById(R.id.action_tab);
        BindViewUtil.setClick(actionTab, feed,mActivity);
        BindViewUtil.bindHeaderInf((ImageView) view.findViewById(R.id.user_avatar), (TextView) view.findViewById(R.id.user_name), (TextView) view.findViewById(R.id.post_time), (TextView) view.findViewById(R.id.post_device), (TextView) view.findViewById(R.id.repost_count), (TextView) view.findViewById(R.id.comments_count), (TextView) view.findViewById(R.id.like_count), mActivity, feed);
        BindViewUtil.bindContent((TextView) view.findViewById(R.id.weibo_content), mActivity, feed.text, new OnContentClickListener() {
            @Override
            public void onTextClick() {
                viewDetail(feed, weiboItem, transition);
            }
        });
        view.findViewById(R.id.weibo_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDetail(feed,weiboItem,transition);
            }
        });
        if (feed.getItemType() == Feed.ORIGIN) {


            BindViewUtil.bindImages(mActivity, (RecyclerView) view.findViewById(R.id.weibo_images), feed);

        } else {
            final LinearLayout origin = (LinearLayout) view.findViewById(R.id.origin_weibo_item);
            String originContent;
            if (feed.retweeted_status.user != null) {
                originContent = "@" + feed.retweeted_status.user.name + " : " + feed.retweeted_status.text;
            } else {
                originContent = "抱歉，此微博已被作者删除。";
            }
            //绑定原微博内容
            BindViewUtil.bindContent((TextView) view.findViewById(R.id.origin_weibo_content), mActivity, originContent, new OnContentClickListener() {
                @Override
                public void onTextClick() {
                    viewDetail(feed.retweeted_status, origin, transition);
                }
            });
            BindViewUtil.bindImages(mActivity, (RecyclerView) view.findViewById(R.id.origin_weibo_image_list), feed.retweeted_status);
            origin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, "点击了原微博：", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, WeiboDetailActivity.class);
                    intent.putExtra("feed", (Parcelable) feed.retweeted_status);
                    mContext.startActivity(intent);
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
        Intent intent = new Intent(mContext, TopicActivity.class);

        intent.putExtra("topic",topic);

        mActivity.startActivity(intent);
    }
    private void viewDetail(Feed feed, View shareView, String s) {
        Intent intent = new Intent(mContext, WeiboDetailActivity.class);

        intent.putExtra("feed", (Parcelable) feed);

        ActivityUtils.startActivity(mActivity,intent,shareView,s);
    }
}
