package com.nxt.ott.server;

import android.text.TextUtils;

import com.nxt.ott.util.DbHelper;
import com.nxt.zyl.util.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonService {
    public static List<HashMap<String, Object>> getHotNews(String url) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        String jsonString = HttpUtils.getOriginalJSON(url);

        try {

            if (jsonString != null && !"".equals(jsonString)) {
                JSONObject object = new JSONObject(jsonString);
                String json2String = object.getString("Rows");
                int total = Integer.parseInt(object.getString("Total"));
                if (total == 0) {
                    return list;
                }
                JSONArray jsonArray = new JSONArray(json2String);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jb = (JSONObject) jsonArray.get(i);
                    map.put("News_Id", jb.getString("News_Id"));
                    map.put("News_Name", jb.getString("News_Name"));
                    map.put("News_Summary", jb.getString("News_Summary"));
                    map.put("News_Cotent", jb.getString("News_Cotent"));
                    map.put("News_Source", jb.getString("News_Source"));
                    map.put("News_Imgurl", jb.getString("News_Imgurl"));
                    map.put("News_Addtime", jb.getString("News_Addtime"));
                    map.put("News_ProtypeId", jb.getString("News_ProtypeId"));
                    map.put("News_SortsId", jb.getString("News_SortsId"));
                    map.put("News_AddUser", jb.getString("News_AddUser"));
                    map.put("SortName", jb.getString("SortName"));
                    map.put("Date", jb.getString("Date"));
                    map.put("imghttpurl", jb.getString("imghttpurl"));
                    map.put("News_ReadCount", jb.getString("News_ReadCount"));

                    list.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<HashMap<String, Object>> getNoticeList(String url) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        try {
            // {"id":1,"notice_title":"test","notice_content":"","create_id":1,"create_name":"����Ա","dep_id":1,"dep_name":"","notice_time":"\/Date(1398251007500+0800)\/","AddDate":"2014��4��23��"}],"Total":"4"}
            if (jsonString != null && !"".equals(jsonString)) {
                JSONObject object = new JSONObject(jsonString);
                String json2String = object.getString("Rows");
                int total = Integer.parseInt(object.getString("Total"));
                if (total == 0) {
                    return list;
                }
                JSONArray jsonArray = new JSONArray(json2String);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jb = (JSONObject) jsonArray.get(i);
                    map.put("notice_title", jb.getString("notice_title"));
                    map.put("notice_content", jb.getString("notice_content"));
                    map.put("create_id", jb.getString("create_id"));
                    map.put("create_name", jb.getString("create_name"));
                    map.put("dep_id", jb.getString("dep_id"));
                    map.put("dep_name", jb.getString("dep_name"));

                    map.put("AddDate", jb.getString("AddDate"));

                    list.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<HashMap<String, Object>> getScanList(String url) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        try {
            // {"id":1,"notice_title":"test","notice_content":"","create_id":1,"create_name":"����Ա","dep_id":1,"dep_name":"","notice_time":"\/Date(1398251007500+0800)\/","AddDate":"2014��4��23��"}],"Total":"4"}
            if (jsonString != null && !"".equals(jsonString)) {
                JSONObject object = new JSONObject(jsonString);
                String json2String = object.getString("Rows");
                int total = Integer.parseInt(object.getString("Total"));
                if (total == 0) {
                    return list;
                }
                JSONArray jsonArray = new JSONArray(json2String);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jb = (JSONObject) jsonArray.get(i);
                    map.put("notice_title", jb.getString("notice_title"));
                    map.put("notice_content", jb.getString("notice_content"));
                    map.put("create_id", jb.getString("create_id"));
                    map.put("create_name", jb.getString("create_name"));
                    map.put("dep_id", jb.getString("dep_id"));
                    map.put("dep_name", jb.getString("dep_name"));

                    map.put("AddDate", jb.getString("AddDate"));

                    list.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<HashMap<String, Object>> getTaskList(String url) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        try {
            if (jsonString != null && !"".equals(jsonString)) {
                JSONObject object = new JSONObject(jsonString);
                String json2String = object.getString("Rows");
                int total = Integer.parseInt(object.getString("Total"));
                if (total == 0) {
                    return list;
                }
                JSONArray jsonArray = new JSONArray(json2String);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jb = (JSONObject) jsonArray.get(i);

                    map.put("Task_title", jb.getString("Task_title"));
                    map.put("Task_Content", jb.getString("Task_Content"));
                    map.put("Task_Degree", jb.getString("Task_Degree"));
                    map.put("Task_Source", jb.getString("Task_Source"));
                    map.put("LostDate", jb.getString("LostDate"));
                    map.put("AddDate", jb.getString("AddDate"));

                    map.put("Task_SenderID", jb.getString("Task_SenderID"));
                    map.put("Task_Sender", jb.getString("Task_Sender"));
                    map.put("Task_ReceiverID", jb.getString("Task_ReceiverID"));
                    map.put("Task_Receiver", jb.getString("Task_Receiver"));
                    map.put("Task_ReContent", jb.getString("Task_ReContent"));
                    map.put("ReDate", jb.getString("ReDate"));
                    map.put("Task_id", jb.getInt("Task_id"));
                    if (jb.getString("Task_ReContent") != null
                            && !TextUtils.isEmpty(jb
                            .getString("Task_ReContent"))) {
                        map.put("Task_state", "");

                    } else {
                        map.put("Task_state", "待回复");
                    }
                    list.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<HashMap<String, Object>> getLogList(String url) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        try {
            if (jsonString != null && !"".equals(jsonString)) {
                JSONObject object = new JSONObject(jsonString);
                String json2String = object.getString("Rows");
                int total = Integer.parseInt(object.getString("Total"));
                if (total == 0) {
                    return list;
                }
                JSONArray jsonArray = new JSONArray(json2String);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jb = (JSONObject) jsonArray.get(i);
                    map.put("note_title", URLDecoder.decode(jb.getString("note_title"), "utf-8"));
                    map.put("note_content", URLDecoder.decode(jb.getString("note_content"), "utf-8"));
                    map.put("note_adduser", jb.getString("note_adduser"));
                    map.put("Date", jb.getString("Date"));
                    map.put("imghttpurl", jb.getString("imghttpurl"));
                    list.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public static List<HashMap<String, Object>> getGroupList(String url) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        try {
            String jsonString = HttpUtils.getOriginalJSON(url);

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jb = (JSONObject) jsonArray.get(i);
                map.put("gid", jb.getString("gid"));
                map.put("gname", jb.getString("gname"));
                /*
                 * map.put("note_adduser", jb.getString("note_adduser"));
				 * map.put("Date", jb.getString("Date"));
				 */
                list.add(map);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<Map<String, String>> getweatherlist(String url,
                                                           DbHelper helper) {
        // Log.i("wurl","url"+url);
        String weather = "";
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        if (jsonString != null && !"".equals(jsonString)) {
            try {
                JSONObject js = new JSONObject(jsonString);

                String wdata = js.getString("wdata");
                js = new JSONObject(wdata);
                String sevdays = js.getString("sevDays");
                JSONArray array = new JSONArray(sevdays);
                for (int i = 0; i < array.length(); i++) {
                    js = array.getJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    String wt = js.getString("weatherID");
                    map.put("date", js.getString("date"));
                    map.put("tmax", js.getString("Tmax"));
                    map.put("tmin", js.getString("Tmin"));
                    if (wt.contains("转")) {
                        String w1 = wt.split("转")[0];
                        String w2 = wt.split("转")[1];
                        weather = helper.queryWeather("1", w1) + "转"
                                + helper.queryWeather("1", w2);
                    } else {
                        weather = helper.queryWeather("1", wt);
                    }
                    map.put("weather", weather);
                    String wind = js.getString("windPower");
                    if (wind.contains("转")) {
                        map.put("wind",
                                helper.queryWeather("3", wind.split("转")[0]));
                    } else {
                        map.put("wind", helper.queryWeather("3", wind));
                    }
                    String winddir = js.getString("windDir");

                    if (winddir.contains("转")) {
                        map.put("winddir",
                                helper.queryWeather("2", winddir.split("转")[0]));
                    } else {
                        map.put("winddir", helper.queryWeather("2", winddir));
                    }
                    list.add(map);
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return list;

    }

    /*
     * private static String getweather(String wt,DbHelper helper) {
     * if(wt.contains("ת")){ String w1=wt.split("ת")[0]; String
     * w2=wt.split("ת")[1]; return helper.queryWeather("1",
     * w1)+"ת"+helper.queryWeather("1", w2); } return helper.queryWeather("1",
     * wt); }
     */
    public static List<HashMap<String, Object>> getMegNoticeList(String url) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        try {
            // {"id":1,"notice_title":"test","notice_content":"","create_id":1,"create_name":"绠＄悊鍛?,"dep_id":1,"dep_name":"","notice_time":"\/Date(1398251007500+0800)\/","AddDate":"2014骞?鏈?3鏃?}],"Total":"4"}
            if (jsonString != null && !"".equals(jsonString)) {
                JSONObject object = new JSONObject(jsonString);
                String json2String = object.getString("Rows");
                int total = Integer.parseInt(object.getString("Total"));
                if (total == 0) {
                    return list;
                }
                JSONArray jsonArray = new JSONArray(json2String);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jb = (JSONObject) jsonArray.get(i);
                    map.put("Date", jb.getString("Date"));
                    // map.put("id", jb.getString("id"));
                    map.put("title", jb.getString("title"));
                    map.put("Mcontent", jb.getString("Mcontent"));
                    map.put("notice",
                            "标题:" + jb.getString("title") + "\t\t时间:"
                                    + jb.getString("Date") + "\n内容:\t"
                                    + jb.getString("Mcontent"));
					/*
					 * map.put("dep_id", jb.getString("dep_id"));
					 * map.put("dep_name", jb.getString("dep_name"));
					 *
					 * map.put("AddDate", jb.getString("AddDate"));
					 */

                    list.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<HashMap<String, Object>> getfbgqlist(String url) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        try {
            // {"id":1,"notice_title":"test","notice_content":"","create_id":1,"create_name":"绠＄悊鍛?,"dep_id":1,"dep_name":"","notice_time":"\/Date(1398251007500+0800)\/","AddDate":"2014骞?鏈?3鏃?}],"Total":"4"}
            if (jsonString != null && !"".equals(jsonString)) {
                JSONObject object = new JSONObject(jsonString);
                String json2String = object.getString("Rows");
                int total = Integer.parseInt(object.getString("Total"));
                if (total == 0) {
                    return list;
                }
                JSONArray jsonArray = new JSONArray(json2String);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jb = (JSONObject) jsonArray.get(i);
                    map.put("Date", jb.getString("Date"));
                    map.put("ProductName", jb.getString("ProductName"));
                    map.put("Describe", jb.getString("Describe"));
                    map.put("imghttpurl", jb.getString("imghttpurl"));
                    map.put("TypeId", jb.getInt("TypeId"));

					/*
					 * map.put("dep_id", jb.getString("dep_id"));
					 * map.put("dep_name", jb.getString("dep_name"));
					 *
					 * map.put("AddDate", jb.getString("AddDate"));
					 */

                    list.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<HashMap<String, String>> getPrice2(String url) {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        try {
            // {"id":1,"notice_title":"test","notice_content":"","create_id":1,"create_name":"绠＄悊鍛?,"dep_id":1,"dep_name":"","notice_time":"\/Date(1398251007500+0800)\/","AddDate":"2014骞?鏈?3鏃?}],"Total":"4"}
            if (jsonString != null && !"".equals(jsonString)) {
                JSONObject object = new JSONObject(jsonString);
                String json2String = object.getString("Rows");
                int total = Integer.parseInt(object.getString("Total"));
                if (total == 0) {
                    return list;
                }
                JSONArray jsonArray = new JSONArray(json2String);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jb = (JSONObject) jsonArray.get(i);
                    map.put("id", jb.getString("id"));
                    map.put("Agrivarity", jb.getString("Agrivarity"));
                    map.put("guige", jb.getString("guige"));
                    map.put("coUnit", jb.getString("coUnit"));
                    map.put("farmerPrice", jb.getString("farmerPrice"));
//                    String city = jb.getString("disctrict").substring(4).replace("|", "-").split("-")[0];
//                    String xian = jb.getString("disctrict").substring(4).replace("|", "-").split("-")[1];
//                    if (xian.contains("区") || xian.equals("市辖区")) {
//                        map.put("disctrict", city);
//
//                    } else {
//                        map.put("disctrict", xian);
//                    }
                    //map.put("disctrict", jb.getString("disctrict").substring(4).replace("|", "-"));
                    list.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return list;
    }

    public static List<Map<String, String>> getprice(String url) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        if (jsonString != null) {
            if (jsonString.startsWith("\ufeff")) {
                jsonString = jsonString.substring(1);
            }

            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jb = (JSONObject) jsonArray.get(i);
                    String type = jb.getString("name");
                    String price = jb.getString("price");
                    String area = jb.getString("aname");

                    // Bitmap image=getImageBitmp(imageUrl);

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("type", type);
                    map.put("price", price);
                    map.put("area", area);

                    list.add(map);
                }
                // Log.i("list", list.toString());
                return list;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
        return null;
    }


    public static List<String> getpricedate(String url) {
        List<String> list = new ArrayList<String>();
        String jsonString = HttpUtils.getOriginalJSON(url);
        if (jsonString != null) {
            if (jsonString.startsWith("\ufeff")) {
                jsonString = jsonString.substring(1);
            }

            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jb = (JSONObject) jsonArray.get(i);
                    String date = jb.getString("date");
                    list.add(date);

                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        return null;
    }
}
