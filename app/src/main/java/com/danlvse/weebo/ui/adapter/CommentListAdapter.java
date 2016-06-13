package com.danlvse.weebo.ui.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.utils.OnContentClickListener;
import com.danlvse.weebo.utils.ToastUtil;
import com.danlvse.weebo.utils.weibo.BindViewUtil;

import java.util.ArrayList;

/**
 * Created by zxy on 16/5/27.
 */
public class CommentListAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private ArrayList<Comment> mComments;
    private Context mContext;

    public CommentListAdapter(ArrayList<Comment> mComments, Context mContext) {
        this.mComments = mComments;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
        return new CommentItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        CommentItemViewHolder v = (CommentItemViewHolder) holder;
        BindViewUtil.bindUser(mContext, comment.user,v.avatar, v.username);
        BindViewUtil.bindContent(v.content, mContext, comment.text, new OnContentClickListener() {
            @Override
            public void onTextClick() {
//                ToastUtil.showShort(mContext,"回复评论");
            }
        });
        v.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(mContext,"回复评论");
            }
        });
        v.time.setText(BindViewUtil.formatDate(comment.created_at));
        showItemAnim(v.content,position);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void setList(ArrayList<Comment> list) {
        mComments = list;
    }

    private static class CommentItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private ImageView avatar;
        private TextView username;
        private TextView time;
        private TextView content;

        public CommentItemViewHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.item_view);
            avatar = (ImageView) itemView.findViewById(R.id.commemt_user_avatar);
            username = (TextView) itemView.findViewById(R.id.commemt_user_name);
            time = (TextView) itemView.findViewById(R.id.commemt_post_time);
            content = (TextView) itemView.findViewById(R.id.commemt_content);
        }
    }
}
