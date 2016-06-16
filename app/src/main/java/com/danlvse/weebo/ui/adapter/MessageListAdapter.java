package com.danlvse.weebo.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.ui.AddWeiboActivity;
import com.danlvse.weebo.utils.OnContentClickListener;
import com.danlvse.weebo.utils.weibo.BindViewUtil;

import java.util.List;

/**
 * Created by zxy on 16/6/13.
 */
public class MessageListAdapter extends RecyclerView.Adapter {
    private Activity mActivity;
    private List<Comment> comments;

    public MessageListAdapter(Activity mActivity, List<Comment> comments) {
        this.mActivity = mActivity;
        this.comments = comments;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder viewHolder = (MessageViewHolder) holder;
        final Comment comment = comments.get(position);
        viewHolder.replyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWeiboActivity.goToAdd(mActivity,comment.status,AddWeiboActivity.REPLY,comment.user.name+":"+comment.text);
            }
        });
        viewHolder.postTime.setText(BindViewUtil.formatDate(comment.created_at));
        BindViewUtil.bindUser(mActivity, comment.user, viewHolder.avatar, viewHolder.username);
        BindViewUtil.bindContent(viewHolder.content, mActivity, comment.text, new OnContentClickListener() {
            @Override
            public void onTextClick() {
                AddWeiboActivity.goToAdd(mActivity,comment.status,AddWeiboActivity.REPLY,comment.user.name+":"+comment.text);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setDatas(List<Comment> list) {
        comments = list;
    }

    private static class MessageViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView username;
        private TextView postTime;
        private TextView content;
        private ImageView replyIcon;
        public MessageViewHolder(View itemView) {
            super(itemView);
            this.avatar = (ImageView) itemView.findViewById(R.id.commemt_user_avatar);
            this.username = (TextView) itemView.findViewById(R.id.commemt_user_name);
            this.postTime = (TextView) itemView.findViewById(R.id.commemt_post_time);
            this.content = (TextView) itemView.findViewById(R.id.commemt_content);
            this.replyIcon = (ImageView) itemView.findViewById(R.id.reply_icon);
        }
    }
}
