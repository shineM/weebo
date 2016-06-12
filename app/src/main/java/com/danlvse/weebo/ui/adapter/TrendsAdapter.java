package com.danlvse.weebo.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danlvse.weebo.R;
import com.danlvse.weebo.data.Topic;
import com.danlvse.weebo.ui.TopicActivity;

import java.util.List;

/**
 * Created by zxy on 16/6/12.
 */
public class TrendsAdapter extends RecyclerView.Adapter {
    private List<Topic> topics;
    private Activity mActicity;

    public TrendsAdapter(List<Topic> topics, Activity mActicity) {
        this.topics = topics;
        this.mActicity = mActicity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActicity.getApplicationContext()).inflate(R.layout.topic_item, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TopicViewHolder viewHolder = (TopicViewHolder) holder;
        viewHolder.topicText.setText(topics.get(position).getName());
        viewHolder.topicText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActicity, TopicActivity.class);
                intent.putExtra("topic",topics.get(position).getName());
                mActicity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public void setDatas(List<Topic> list) {
        topics = list;
    }

    private static class TopicViewHolder extends RecyclerView.ViewHolder {
        private TextView topicText;

        public TopicViewHolder(View itemView) {
            super(itemView);
            this.topicText = (TextView) itemView.findViewById(R.id.topic_text);
        }
    }
}
