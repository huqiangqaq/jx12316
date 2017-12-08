package com.nxt.ott.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.domain.HomeImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiang on 2017/4/9 14:44.
 */

public class ListDataSave {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();
    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public String[] getDataArray(String tag) {
        List<String> datalist;
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return null;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());
        String[] kinds = new String[datalist.size()];
        for (int i=0;i<datalist.size();i++){
           kinds[i] = datalist.get(i);
        }
        return kinds;
    }
    public List<String> getDataList(String tag){
        List<String> datalist;
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return null;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());

        return datalist;
    }
    /**
     * 获取List
     * @param tag
     * @return
     */
    @Deprecated
    public List<HomeImage.RowsBean> getDataListHomeImage(String tag) {
        List<HomeImage.RowsBean> datalist=new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<HomeImage.RowsBean>>() {
        }.getType());
        return datalist;
    }

    public <T> List<T> getDataList(String tag,Class<T> cls) {
        List<T> datalist = new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return null;
        }
        try {
            JSONArray array = new JSONArray(strJson);
            Gson gson = new Gson();
            for (int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                datalist.add(gson.fromJson(object.toString(),cls));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datalist;

    }

}
