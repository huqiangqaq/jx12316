package com.nxt.ott.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huqiang on 2017/3/31 8:59.
 */

public class JsonUtils {

    public static String getServerResult(String jsonStr){
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            return jsonObject.getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getServerMsg(String jsonStr){
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            return jsonObject.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getUnreadCount(String jsonStr,String user){
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            return jsonObject.getInt(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
