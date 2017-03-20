package com.danlvse.weebo.utils.weibo;

import android.text.TextUtils;

import com.danlvse.weebo.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zxy on 16/5/27.
 */
public class CommentParseUtil {
    public static ArrayList<Comment> parse(String s){
        ArrayList<Comment> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(s)){
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.optJSONArray("comments");
                if (array!=null&&array.length()>0){
                    for (int i=0;i<array.length();i++){
                        Comment comment = Comment.parse(array.getJSONObject(i));
                        arrayList.add(comment);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        return arrayList;
    }
}
