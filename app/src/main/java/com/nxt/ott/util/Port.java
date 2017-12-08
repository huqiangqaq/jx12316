package com.nxt.ott.util;

/**
 * Created by Administrator on 2014-11-04.
 */
public class Port {
    //百度定位接口
    public static final String GECODE_URL= "http://api.map.baidu.com/geocoder/v2/?ak=Y88H672eRS9Yx09b8rt8LXP7&callback=renderReverse&location=";//百度定位接口
    public static final String GECODE_URLTWO= "&output=json&pois=0";
    //百度天气
    public static final String BASEURL ="http://182.116.57.248:8108";//气象为农天气预报接口

    public static final String WEATHERURL = "http://weather.6636.net/Weather/getWeather.php?area=";//气象为农天气预报接口
    public static final String WEASER_URL = "http://api.map.baidu.com/telematics/v3/weather?location=";
    public static final String WEASER_URLTWO = "&output=json&ak=Y88H672eRS9Yx09b8rt8LXP7";
    public static final String WEASER_URLTHREE = "&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
    public static final String SNZXNEWS= BASEURL+"/Data1/News_Handle.ashx?action=grid";//三农资讯头部
    public static final String HOMEPORT= BASEURL+"/Data1/News_Handle.ashx";//主接口
    public static final String NOTICE= BASEURL+"/Data/public_notice.ashx";//通知公告
    public static final String MYTASK= BASEURL+"/Data1/NZD_WorkTask.ashx";//我的任务
    public static final String MYLOG= BASEURL+"/Data1/NZD_Worklog.ashx";//我的日志
    public static final String CHECKUPDATE="http://182.116.57.248:8010/apk/nongjitong.txt";//检查更新
    public static final String DOENLOADAPP="http://182.116.57.248:8010/apk/nongjitong.apk";//下载app
    public static final String NOTICELIST=BASEURL+"/Data1/pushmessage.ashx";//通知公告列表
    public static final String FEEDBACK="http://182.116.57.248:8018/Data1/NJB_Feedback.ashx";//获取好友
    public static final String ADDNOTICE=BASEURL+"/Data1/pushmessage.ashx";//添加会议通知
    public static final String GROUPMSG="http://182.116.57.248:8061/HuanXin/CommonServlet";//添加会议通知
    /*    public static final String FBGQLIST="http://182.116.57.248:8016/API/SupplyAll.aspx";//发布供求列表
    */    public static final String FBGQUPLOAD=BASEURL+"/Data1/NJBBussinessHandler.ashx";//发布供求列表
    public static final String PRICE="http://yinong.6636.net/meilisannong/server/index.php/Buy_sell_information/getAllPrice";//发布供求列表
    public static final String JXPRICE=BASEURL+"/data1/productinfolist.ashx";//发布供求列表
    public static final String PRICEDATE="http://yinong.6636.net/meilisannong/server/index.php/Buy_sell_information/getAllPrice";//发布供求列表
    public static final String FBGQLIST=BASEURL+"/Data1/NJBBussinessHandler.ashx?Action=grid";//发布供求列表
    public static final String HR_EMPLOYEE=BASEURL+"/data/hr_employee.ashx";//发布供求列表
    public static final String WORKTASK=BASEURL+"/Data1/NZD_WorkTask.ashx?Action=resave&nid=";//发布供求列表

    public static final String PRICE_UPDATE = "http://jx.12316.zq.yn15.com/Data1/AgriPrice.ashx";


}
