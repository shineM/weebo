package com.danlvse.weebo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danlvse.weebo.R;
import com.danlvse.weebo.data.User;
import com.danlvse.weebo.ui.ProfileActivity;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by zxy on 16/6/10.
 */
public class UserListAdapter extends RecyclerView.Adapter{
    private Activity mContext;
    private List<User> users;

    public UserListAdapter(Activity mContext, List<User> users) {
        this.mContext = mContext;
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.user_item,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final UserViewHolder mHolder = (UserViewHolder) holder;
        mHolder.userItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("user",users.get(position).name);
                mContext.startActivity(intent);
            }
        });
        Glide.with(mContext).load(users.get(position).avatar_large).bitmapTransform(new CropCircleTransformation(mContext)).placeholder(R.mipmap.ic_person_white_24dp).into(mHolder.avatar);
        mHolder.username.setText(users.get(position).name);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setDatas(List<User> list) {
        users.clear();
        users.addAll(list);
    }

    private class UserViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout userItem;
        private ImageView avatar;
        private TextView username;
        public UserViewHolder(View itemView) {
            super(itemView);
            this.avatar = (ImageView) itemView.findViewById(R.id.user_avatar);
            this.username = (TextView) itemView.findViewById(R.id.user_name);
            this.userItem = (RelativeLayout) itemView.findViewById(R.id.user_item);
        }
    }
}
