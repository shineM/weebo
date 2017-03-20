package com.danlvse.weebo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danlvse.weebo.R;
import com.danlvse.weebo.model.Feed;
import com.danlvse.weebo.ui.ImagesDetailActivity;
import com.danlvse.weebo.utils.ActivityUtils;
import com.danlvse.weebo.utils.weibo.BindViewUtil;

import java.util.ArrayList;

/**
 * Created by zxy on 16/5/25.
 */
public class ImageListAdapter extends RecyclerView.Adapter {
    private ArrayList<String> imageList;
    private Context mContext;
    private Boolean isSingle;
    private Activity mActivity;
    private Feed mFeed;

//    public ImageListAdapter(ArrayList<String> imageList, Context mContext) {
//        this.imageList = imageList;
//        this.mContext = mContext;
//        if (imageList.size() == 1) {
//            isSingle = true;
//        } else {
//            isSingle = false;
//        }
//    }
    public ImageListAdapter(Feed feed, ArrayList<String> imageList, Activity activity) {
        this.imageList = imageList;
        this.mActivity = activity;
        this.mFeed = feed;
        if (imageList.size() == 1) {
            isSingle = true;
        } else {
            isSingle = false;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.image_item, parent, false);

        return new ItemImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final String url = imageList.get(position);
        final ItemImageViewHolder viewHolder = (ItemImageViewHolder) holder;
        BindViewUtil.loadImages(mActivity, url, viewHolder.imageView, viewHolder.imageType, isSingle);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ImagesDetailActivity.class);
                intent.putExtra(ImagesDetailActivity.PIC_IMAGE,url);
                Bundle bundle = new Bundle();
                bundle.putParcelable("weibo", mFeed);
                intent.putExtra("bundle",bundle);
                ActivityUtils.startActivity(mActivity,intent,viewHolder.imageView,ImagesDetailActivity.LARGE_IMAGE);
            }
        });
    }
    private void startShareViewActivity(Intent intent, View view) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, ImagesDetailActivity.LARGE_IMAGE);
            mActivity.startActivity(intent, options.toBundle());

    }



    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void setData(ArrayList<String> origin_pic_urls) {
        imageList = origin_pic_urls;
    }

    private static class ItemImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView imageType;

        public ItemImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageItem);
            imageType = (TextView) itemView.findViewById(R.id.imageType);

        }
    }
}
