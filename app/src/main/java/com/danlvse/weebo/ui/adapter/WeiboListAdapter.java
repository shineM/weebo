package com.danlvse.weebo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Weibo;
import com.danlvse.weebo.ui.MainActivity;
import com.danlvse.weebo.ui.WeiboDetailActivity;
import com.danlvse.weebo.utils.OnWeiboContentListener;
import com.danlvse.weebo.utils.weibo.BindViewUtil;

import java.util.List;

/**
 * Created by zxy on 16/5/25.
 */
public class WeiboListAdapter extends RecyclerView.Adapter {
    private static final int TYPE_ORINAL = 0;
    private static final int TYPE_REPOST = 1;
    private List<Weibo> mWeibos;
    private Context mContext;
    private View mView;

    public WeiboListAdapter(List<Weibo> mWeibos, Context context) {
        this.mWeibos = mWeibos;
        this.mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ORINAL) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.weibo_item, parent, false);
            return new WeiboViewHolder(mView);
        } else if (viewType == TYPE_REPOST) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.weibo_item_repost, parent, false);
            return new RepostWeiboViewHolder(mView);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWeibos.get(position).retweeted_status != null && mWeibos.get(position).retweeted_status.user != null) {
            return TYPE_REPOST;
        } else {
            return TYPE_ORINAL;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Weibo weibo = mWeibos.get(position);
        System.out.println("position:" + position + "imageList:" + weibo.origin_pic_urls);
        //原创微博
        if (holder instanceof WeiboViewHolder) {
            WeiboViewHolder viewHolder = (WeiboViewHolder) holder;
            BindViewUtil.bindHeaderInf(viewHolder.avatar, viewHolder.username, viewHolder.postTime, viewHolder.postDevice, viewHolder.repostCount, viewHolder.commentCount, viewHolder.likeCount, mContext, weibo);
            BindViewUtil.bindContent(viewHolder.content, mContext, weibo.text, new OnWeiboContentListener() {
                @Override
                public void onTextClick() {
                    viewDetail(weibo);
                }
            });
//            BindViewUtil.bindImages(mContext, viewHolder.imgList, weibo.origin_pic_urls);
//            viewHolder.weibo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    viewDetail(weibo);
//                }
//            });
        //转发微博
        } else if (holder instanceof RepostWeiboViewHolder) {
            RepostWeiboViewHolder viewHolder = (RepostWeiboViewHolder) holder;
            //绑定转发内容
            BindViewUtil.bindHeaderInf(viewHolder.avatar, viewHolder.username, viewHolder.postTime, viewHolder.postDevice, viewHolder.repostCount, viewHolder.commentCount, viewHolder.likeCount, mContext, weibo);
            BindViewUtil.bindContent(viewHolder.content, mContext, weibo.text, new OnWeiboContentListener() {
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
            BindViewUtil.bindContent(viewHolder.originContent, mContext, originContent, new OnWeiboContentListener() {
                @Override
                public void onTextClick() {
                    viewDetail(weibo.retweeted_status);
                }
            });
//            BindViewUtil.bindImages(mContext, viewHolder.imgList, weibo.retweeted_status.origin_pic_urls);
            viewHolder.originWeibo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   viewDetail(weibo.retweeted_status);
                }
            });
            viewHolder.repostWeibo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

    @Override
    public int getItemCount() {
        return mWeibos.size();
    }

    public void setWeibos(List<Weibo> weibos) {
        this.mWeibos = weibos;
    }

    public static class WeiboViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout weibo;
        private ImageView avatar;
        private TextView username;
        private TextView postTime;
        private TextView postDevice;
        private TextView commentCount;
        private TextView repostCount;
        private TextView likeCount;
        private TextView content;
        private RecyclerView imgList;

        public WeiboViewHolder(View itemView) {
            super(itemView);
            weibo = (LinearLayout) itemView.findViewById(R.id.weibo_item);
            avatar = (ImageView) itemView.findViewById(R.id.user_avatar);
            username = (TextView) itemView.findViewById(R.id.user_name);
            postTime = (TextView) itemView.findViewById(R.id.post_time);
            postDevice = (TextView) itemView.findViewById(R.id.post_device);
            commentCount = (TextView) itemView.findViewById(R.id.comments_count);
            repostCount = (TextView) itemView.findViewById(R.id.repost_count);
            likeCount = (TextView) itemView.findViewById(R.id.like_count);
            content = (TextView) itemView.findViewById(R.id.weibo_content);
            imgList = (RecyclerView) itemView.findViewById(R.id.weibo_images);

        }
    }

    private class RepostWeiboViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout repostWeibo;
        private LinearLayout originWeibo;
        private ImageView avatar;
        private TextView username;
        private TextView postTime;
        private TextView postDevice;
        private TextView commentCount;
        private TextView repostCount;
        private TextView likeCount;
        private TextView content;
        private TextView originContent;
        private RecyclerView imgList;

        public RepostWeiboViewHolder(View itemView) {
            super(itemView);
            repostWeibo = (LinearLayout) itemView.findViewById(R.id.repost_weibo_item);
            originWeibo = (LinearLayout) itemView.findViewById(R.id.origin_weibo_item);
            avatar = (ImageView) itemView.findViewById(R.id.user_avatar);
            username = (TextView) itemView.findViewById(R.id.user_name);
            postTime = (TextView) itemView.findViewById(R.id.post_time);
            postDevice = (TextView) itemView.findViewById(R.id.post_device);
            commentCount = (TextView) itemView.findViewById(R.id.comments_count);
            repostCount = (TextView) itemView.findViewById(R.id.repost_count);
            likeCount = (TextView) itemView.findViewById(R.id.like_count);
            content = (TextView) itemView.findViewById(R.id.repost_weibo_content);
            originContent = (TextView) itemView.findViewById(R.id.origin_weibo_content);
            imgList = (RecyclerView) itemView.findViewById(R.id.origin_weibo_image_list);
        }
    }
}
