package com.danlvse.weebo.utils.weibo;

import com.danlvse.weebo.data.Comment;
import com.danlvse.weebo.data.Topic;
import com.danlvse.weebo.data.User;
import com.danlvse.weebo.data.Weibo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxy on 16/5/25.
 */
public class WeiboParseUtil {
    //解析微博列表
    public static ArrayList<Weibo> parseWeiboList(String response) {
        ArrayList<Weibo> mWeiboList = new ArrayList<Weibo>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("statuses");
            if (jsonArray != null && jsonArray.length() > 0) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    mWeiboList.add(Weibo.parse(jsonArray.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mWeiboList;
    }
    //解析用户列表
    public static ArrayList<User> parseUserList(String s) {
        ArrayList<User> users = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.optJSONArray("users");
            if (null != jsonArray && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    users.add(User.parse(jsonArray.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }
    //解析热门话题列表
    public static ArrayList<Topic> parseTopicList(String s) {
        ArrayList<Topic> topics = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(s);
            JSONObject trends = object.optJSONObject("trends");
            JSONArray array = trends.optJSONArray("2016-06-12 ");

            if (null != array && array.length() > 0) {
                int len = array.length();
                for (int i = 0; i < len; i++){
                    topics.add(Topic.parse(array.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return topics;
    }
    //解析评论列表
    public static ArrayList<Comment> parseCommentList(String s){
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.optJSONArray("comments");
            if (null!=jsonArray&&jsonArray.length()>0){
                int len = jsonArray.length();
                for (int i = 0; i < len; i++){
                    comments.add(Comment.parse(jsonArray.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comments;
    }
}
