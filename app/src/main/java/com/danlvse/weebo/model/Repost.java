package com.danlvse.weebo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sina.weibo.sdk.openapi.models.Geo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zxy on 16/5/31.
 * 微博转发实体
 */
public class Repost implements Parcelable {

    private static Pattern mpattern;
    private static Matcher mmatcher;

    private String createdAt;

    private Integer id;

    private String text;

    private String source;

    private Boolean favorited;

    private Boolean truncated;

    private String inReplyToStatusId;

    private String inReplyToUserId;

    private String inReplyToScreenName;

    private Geo geo;

    private String mid;

    private Integer repostsCount;

    private Integer commentsCount;

    private List<Object> annotations = new ArrayList<Object>();

    private User user;

    private Feed retweetedStatus;


    protected Repost(Parcel in) {
        this.createdAt = in.readString();
        this.id = in.readInt();
        this.mid = in.readString();
        this.text = in.readString();
        this.source = in.readString();
        this.favorited = in.readByte() != 0;
        this.truncated = in.readByte() != 0;
        this.inReplyToStatusId = in.readString();
        this.inReplyToUserId = in.readString();
        this.inReplyToScreenName = in.readString();
        this.geo = in.readParcelable(Geo.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.retweetedStatus = in.readParcelable(Feed.class.getClassLoader());
        this.repostsCount = in.readInt();
        this.commentsCount = in.readInt();
        this.annotations = in.readArrayList(Object.class.getClassLoader());

    }

    public Repost() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createdAt);
        dest.writeInt(this.id);
        dest.writeString(this.mid);
        dest.writeString(this.text);
        dest.writeString(this.source);
        dest.writeByte(favorited ? (byte) 1 : (byte) 0);
        dest.writeByte(truncated ? (byte) 1 : (byte) 0);
        dest.writeString(this.inReplyToStatusId);
        dest.writeString(this.inReplyToUserId);
        dest.writeString(this.inReplyToScreenName);
        dest.writeList(this.annotations);
        dest.writeParcelable(this.geo, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.retweetedStatus, flags);
        dest.writeInt(this.repostsCount);
        dest.writeInt(this.commentsCount);

    }

    public static Repost parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        Repost repost = new Repost();
        repost.createdAt = jsonObject.optString("created_at");
        repost.id = jsonObject.optInt("id");
        repost.text = jsonObject.optString("text");
        repost.source = getSource(jsonObject.optString("source"));
        repost.mid = jsonObject.optString("mid");
        repost.favorited = jsonObject.optBoolean("favorited");
        repost.truncated = jsonObject.optBoolean("truncated");
        repost.retweetedStatus = Feed.parse(jsonObject.optJSONObject("retweeted_status"));
        repost.commentsCount = jsonObject.optInt("reply_comment");
        repost.geo = Geo.parse(jsonObject.optString("geo"));
        repost.repostsCount = jsonObject.optInt("reposts_count");
        repost.user = User.parse(jsonObject.optString("user"));
        repost.inReplyToScreenName = jsonObject.optString("in_reply_to_screen_name");
        repost.inReplyToUserId = jsonObject.optString("in_reply_to_user_id");
        repost.inReplyToStatusId = jsonObject.optString("in_reply_to_status_id");

        return repost;
    }

    public static Repost parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return Repost.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static String getSource(String string) {
        mpattern = Pattern.compile("<(.*?)>(.*?)</a>");
        mmatcher = mpattern.matcher(string);
        if (mmatcher.find()) {
            return mmatcher.group(2);
        } else {
            return string;
        }
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The favorited
     */
    public Boolean getFavorited() {
        return favorited;
    }

    /**
     * @param favorited The favorited
     */
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    /**
     * @return The truncated
     */
    public Boolean getTruncated() {
        return truncated;
    }

    /**
     * @param truncated The truncated
     */
    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    /**
     * @return The inReplyToStatusId
     */
    public String getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     * @param inReplyToStatusId The in_reply_to_status_id
     */
    public void setInReplyToStatusId(String inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    /**
     * @return The inReplyToUserId
     */
    public String getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     * @param inReplyToUserId The in_reply_to_user_id
     */
    public void setInReplyToUserId(String inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    /**
     * @return The inReplyToScreenName
     */
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    /**
     * @param inReplyToScreenName The in_reply_to_screen_name
     */
    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    /**
     * @return The geo
     */
    public Object getGeo() {
        return geo;
    }

    /**
     * @param geo The geo
     */
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    /**
     * @return The mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param mid The mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * @return The repostsCount
     */
    public Integer getRepostsCount() {
        return repostsCount;
    }

    /**
     * @param repostsCount The reposts_count
     */
    public void setRepostsCount(Integer repostsCount) {
        this.repostsCount = repostsCount;
    }

    /**
     * @return The commentsCount
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     * @param commentsCount The comments_count
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     * @return The annotations
     */
    public List<Object> getAnnotations() {
        return annotations;
    }

    /**
     * @param annotations The annotations
     */
    public void setAnnotations(List<Object> annotations) {
        this.annotations = annotations;
    }

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return The retweetedStatus
     */
    public Feed getRetweetedStatus() {
        return retweetedStatus;
    }

    /**
     * @param retweetedStatus The retweeted_status
     */
    public void setRetweetedStatus(Feed retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Repost> CREATOR = new Parcelable.Creator<Repost>() {
        @Override
        public Repost createFromParcel(Parcel source) {
            return new Repost(source);
        }

        @Override
        public Repost[] newArray(int size) {
            return new Repost[size];
        }
    };

}
