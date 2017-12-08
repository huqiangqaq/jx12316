package com.nxt.ott.util;


import android.text.TextUtils;

import com.nxt.ott.R;

import java.lang.reflect.Field;



/**
 * Created by zhangyonglu on 2016/2/26 0026.
 */
public class WeatherUtil {
    private static CharacterParser characterParser;
//    public static Weather parseweather(String result){
//        Weather weather=new Weather();
//        JSONObject js = null;
//        try {
//            js = new JSONObject(result);
//            String results = js.getString("results");
//            JSONArray array = new JSONArray(results);
//            JSONObject object = (JSONObject) array.get(0);
//            String weatherstr = object.getString("weather_data");
//            JSONArray array2 = new JSONArray(weatherstr);
//            JSONObject object2 = (JSONObject) array2.get(0);
//            String nowweatherstr = object2.getString("weather");
//
//                weather.setPicurl(object2.getString("dayPictureUrl"));
//
//            String temperature = object2 .getString("temperature");
//            weather.setWeather(nowweatherstr+"\n"+temperature);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return weather;
//    }
//

    //指数数据
    public static String getImage(String weather) {
        characterParser = CharacterParser.getInstance();
        if (!TextUtils.isEmpty(weather)) {
            if (weather.contains("转")) {
                return characterParser.getSelling(weather.split("转")[0]);
            } else {
                return characterParser.getSelling(weather);
            }
        }
        return null;
    }

    public static int getImageresource(String weather, String header) {
        String pic = getImage(weather);

        if (pic == null || "".equals(pic.trim())) {
            getIconOrBg(header);
        }
        //biz_plugin_weather_
        pic = header + pic;
        Class draw = R.mipmap.class;
        try {
            Field field = draw.getDeclaredField(pic);
            return field.getInt(pic);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            getIconOrBg(header);
        }

        return 0;
    }

    private static int getIconOrBg(String header) {
        if (header.contains("biz_plugin_weather")) {
            return R.mipmap.test;
        } else {
            //R.mipmap.bg_01_update;
            return R.mipmap.ic_launcher;
        }
    }

}
