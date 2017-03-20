package com.danlvse.weebo.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zxy on 16/6/12.
 */
public class Topic implements Parcelable {

    private String name;

    private String query;

    private int amount;

    private int delta;

    public Topic() {
    }

    public static Topic parse(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            return Topic.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Topic parse(JSONObject object) {
        if (null == object) {
            return null;
        }
        Topic topic = new Topic();
        topic.name = object.optString("name");
        topic.name = object.optString("query");
        topic.amount = object.optInt("amount");
        topic.delta = object.optInt("delta");

        return topic;
    }

    protected Topic(Parcel in) {
        this.name = in.readString();
        this.query = in.readString();
        this.amount = in.readInt();
        this.delta = in.readInt();

    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.query);
        dest.writeInt(this.amount);
        dest.writeInt(this.delta);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }
}
