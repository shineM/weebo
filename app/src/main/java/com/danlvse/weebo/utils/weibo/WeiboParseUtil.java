package com.danlvse.weebo.utils.weibo;

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
    public static ArrayList<Weibo> parseWeiboList(String response){
        ArrayList<Weibo> mWeiboList = new ArrayList<Weibo>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("statuses");
            if (jsonArray!=null&&jsonArray.length()>0){
                int len = jsonArray.length();
                for (int i = 0;i<len;i++){
                    mWeiboList.add(Weibo.parse(jsonArray.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mWeiboList;
    }
}
