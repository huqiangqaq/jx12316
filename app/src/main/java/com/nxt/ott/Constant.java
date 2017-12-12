package com.nxt.ott;

/**
 * Created by zhangyonglu on 2016/2/25 0025.
 */
public class Constant {
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public final static String LOCATION_CITY = "loaction_city";
    public final static String LOCATION_DIS = "loaction_dis";
    public final static String LOCATION_AREA = "loaction_area";
    public final static String IS_LOGIN = "is_login";
    public final static String NOW_AREA = "now_area";
    public final static String USERNAME = "username";
    public final static String NICKNAME = "nickname";
    public final static String AVATOR = "avator";
    public final static String IS_FIRST_CLICK ="is_first_click";
    public final static String IS_FIRST_HOME = "is_first_home";
    public static final String WEASER_URL = "http://api.map.baidu.com/telematics/v3/weather?location=%s&output=json&ak=Y88H672eRS9Yx09b8rt8LXP7";
    public static final String JIANGXI_NET_URL = "http://www.jxagri.gov.cn/Index.shtml";
    public static final String NQSD_PAKAGENAME = "com.nongqingsudi";
    public static final String NQSD_DOWNLOAD_URL = "http://182.116.57.248:88/apk/nqsd.apk";
    public static final String IS_OPEN_GUIDE_HOME = "is_open_guide_home";
    public static final String IS_OPEN_GUDEI_EXPER = "is_open_guide_exper";
    public static final String PID = "ztypeid";//专家的pid
    public static final String UID = "uid";
    public static final String TEL = "tel";
    public static final String WXREGISTER = "wxregister";//是否已注册过
    public static final String WXPHONE = "wxphone"; //微信接口返回的专家的电话号码

    public static final String URL_SMS = "http://http.yunsms.cn/tx/?uid=55088&pwd=c777049c24d28e9ed5d6a954613efaca&mobile=%s&content=%s";
    private final static String BASE_URL = "http://182.116.57.248:8120";
    public static final String IMAGE_URL = BASE_URL + "/images/upload/portrait/%s";
    public static final String JIANGXI_SCAN_URL = "http://zs.yn15.com/cpt/api/retrospectQuery/toResult.jhtml?retrospectCode=%s";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";

    /**
     * 用户字段
     */
    public static final String USER_TYPE = "user_type";

    public static final String URL_IMG = "http://jx.12316.zq.yn15.com/";
    public static final String CASHFILENAME = "jx12316";
    public static final String YWZC = "yewuzhuangchang";
    /**
     * 检测版本更新
     */
    public static final String JX12316_VERSION_URL = "http://182.116.57.248:88/apk/jx12316_version.txt";
    public static final String JX12316_APKURL = "http://182.116.57.248:88/apk/jx12316.apk";
    /**
     * 农业新闻
     */
    public static final String AGRICULTURE_NEWS_URL = "http://sntt.yq.yn15.com/app/appNews/getByIndex?indexId=318,720&currentPage=%s&pageSize=%s";
    /**
     * 政策法规
     */
    public static final String POLICY_NEWS_PROVINCE_URL = "http://sntt.yq.yn15.com/app/appNews/getByIndex?indexId=313,314,317,319,320,344&currentPage=%s&pageSize=%s";
    public static final String POLICY_NEWS_COUNTRY_URL = "http://sntt.yq.yn15.com/app/appNews/getByIndex?indexId=74,75,76,77,71,72,73,10,707&currentPage=%s&pageSize=%s";
    public static final String POLICY_NEWS_OTHER_URL = "http://sntt.yq.yn15.com/app/appNews/getByIndex?indexId=311,314,343&currentPage=%s&pageSize=%s";

    /**
     * 农时农事
     */
    public static final String AGRICULTURETIME_AGRICULTURETHING = "http://sntt.yq.yn15.com/app/appNews/getByIndex?indexId=11,43,289,290,422,708&currentPage=%s&pageSize=%s";

    /**
     * 致富项目
     */
    public static final String MAKEMONEY_NEWS_POLICY_URL = "http://sntt.yq.yn15.com/app/appNews/getByIndex?indexId=394,395,396&currentPage=%s&pageSize=%s";
    public static final String MAKEMONEY_NEWS_PROJECT_URL = "http://sntt.yq.yn15.com/app/appNews/getByIndex?indexId=397&currentPage=%s&pageSize=%s";
    public static final String MAKEMONEY_NEWS_BUSINESS_URL = "http://sntt.yq.yn15.com/app/appNews/getByIndex?indexId=398&currentPage=%s&pageSize=%s";

    /**
     * 农技百科
     */
    public static final String EXTENSION_ENCYCLOPEDIA_URL = "http://sntt.yq.yn15.com/app/appNews/getByIndex?indexId=709,12,283,702,703,704&currentPage=%s&pageSize=%s";


    /**
     * 获取文章内容
     */
    public static final String NEWS_DETAIL_URL = "http://jilinyqjc.yn15.com/app/appNews/getInfo?id=%s&name=country";

    /**
     * 农药查询
     */
    public static final String PESTICIDE_SEARCH_COMPANY = "http://smart.chinaasv.com/api/Pesticides.aspx?Companyname=%s&start=%s&strip=%s";//添加会议通知
    public static final String PESTICIDE_SEARCH_CARD = "http://smart.chinaasv.com/api/Pesticides.aspx?Registrationnumber=%s&start=%s&strip=10";

    /**
     * 便民服务网站（益农社）
     */
    public static final String MALL_MATERIALS_URL = "http://www.yns12316.com/";//益农社农资商城
//    http://www.365960.cn/mall/index/sid/1
    public static final String MALL_DEPARTMENT_URL = "http://www.yns12316.com/";//百货商城
//    http://www.365960.cn/mall/

    public static final String SERVICE_WATER_AND_ELECTRICITY_CN_URL = "http://www.95598.cn/person/index.shtml";//国家电网
    public static final String SERVICE_WATER_AND_ELECTRICITY_YNS_URL = "https://jiaofei.alipay.com/jiaofei.htm?_pdType=aecfbbfgeabbifdfdieh&_scType=bacfajegafddadijhagd";//益农社水电费

    public static final String SERVICE_PHONE_BILL_MOBILE_URL = "http://www.10086.cn/jx/index_791_791.html";//移动
    public static final String SERVICE_PHONE_BILL_UNICOM_URL = "http://wap.10010.com/t/home.htm";//联通
    public static final String SERVICE_PHONE_BILL_TELECOM_URL = "http://www.189.cn/jx/service/";//电信
    public static final String SERVICE_PHONE_BILL_YNS_URL = "http://www.yns12316.com/";//益农社话费充值
//    http://www.365960.cn/passport/login.html
    public static final String SERVICE_RAILWAY_TICKET_URL = "http://www.114piaowu.com/";//车票购买
    public static final String SERVICE_SEE_DOCTOR_URL = "http://www.91160.com/";//挂号就医
    public static final String SERVICE_BREAK_RULES_SEARCH_URL = "http://m.weizhang8.cn/";//违章查询
    public static final String WEATHER_DATA = "weather_data";
    public static final String WEATHER_AREA = "weather_area";
    public static final String PROVINCE = "location_provice";
    public static final String CITY = "location_city";
    public static final String DISTRICT = "location_district";
    /**
     * 涉农app地址下载链接
     */
    public static final String URL_APPS = "http://182.116.57.246:16002/nyb/service/app/list";



    /**
     * 登录/注册/忘记密码/验证注册
     */
    public static String IS_REGISTER_URL = BASE_URL + "/Data/hr_employee.ashx?action=phoneExist&tel=%s";
    public static String REGISTER_URL = BASE_URL + "/Data/hr_employee.ashx?action=jkreguser&tel=%s&pwd=%s&nickname=%s&usertype=%s";
    public static final String LOGIN_URL = BASE_URL + "/Data/hr_employee.ashx?action=jkinfo&uname=%s&upwd=%s";
    public static final String CHANGE_PWD_URL = BASE_URL + "/Data/hr_employee.ashx?action=changebyphone&tel=%s&T_newpwd=%s";
    public static final String UID_USER_URL = BASE_URL + "/data/hr_employee.ashx?action=form&uid=%s";


    /**
     * 农大夫的接口
     */
    public static final String DISEASE_CONTENT = "disease_content";//选择的症状内容
    public static final String DISEASE_SELECTIONS = "disease_selections";//选择的症状内容
    public static final String DISEASE_TYPE_ENTRTY = "disease_type_enerty";//选择的症状内容
    //http://bch.xxh.lx50.com/DiagnosisSystem
    private static String DOCTOR_BASE_URL = "http://bch.yq.yn15.com/DiagnosisSystem";
    public static String DISEATE_TYPE_URL = DOCTOR_BASE_URL + "/appDiagnosisType.do?dt=%s";
    public static String DISEATE_CHOOSE_URL = DOCTOR_BASE_URL + "/appSymptom.do?symptom=%s&sections=%s";
    public static String DISEATE_CONTENT_URL = DOCTOR_BASE_URL + "/appDiagnosis.do?knowledge=%s&disease=%s&testCase=%s&testWeight=%s&symptom=%s&sections=%s&symptoms=%s";

    /**
     * 赣农宝
     */
    public static final String GNB_URL = "http://www.gnb360.com/wap/";
//    public static final String GNB_URL = "http://gan.365960.com/wap/";

    /**
     * 益农社
     */
    public static final String YNS_URL = "http://www.365960.cn/mobile";

    public static final String FBGQLIST =//发布供求列表
            "http://123.7.63.176:8016/API/SupplyAll.aspx";

    /**
     * 测土配方
     */
    public static final String CTPF_URL = "http://tufei.jxagri.gov.cn/mobile/index.asp";
//    public static final String CTPF_URL = "http://wn.6636.net/mobile/index_sd.asp";
    public static final String JIANGXI_WLWAPK_URL = "http://123.55.118.66:20066/wulianwang/resources/jiangxi-wulianwang.apk";//江西物联网

    /**
     * 课程库相关字段和接口
     */
    public final static String LESSON_DETAIL = "lesson_detail";
    public final static String LESSON_TYPE = "lesson_type";
    public static final String LESSON_TYPE_LIST_URL = "http://182.116.57.246:27210/atc-console/CurriculumWs/bylist?pageNo=1&pageSize=1000";
    public static final String LESSON_VIDEO_LIST_URL = "http://182.116.57.246:27210/atc-console/videoWs/byclassId?classifyid=%s&pageNo=%s&pageSize=10";

    /**
     * 专家
     */
    public static final String EXPERTER_LIST_URL = BASE_URL + "/data/hr_employee.ashx?action=GetUserlist&page=%s&pagesize=%s&usertype=%s";
    public static final String SEARCH_TYPE_URL = BASE_URL + "/data/hr_employee.ashx?Action=GetUserlist&usertype=%s";
    public static final String SEARCH_NAME_URL = BASE_URL + "/data/hr_employee.ashx?Action=GetUserlist&stext=%s";
    public static final String SEARCH_EXPERTER = BASE_URL+"/data/hr_employee.ashx?Action=GetUserlist";
    public static final String EXPERTER_LIST_URL_ALL = BASE_URL + "/data/hr_employee.ashx?action=GetUserlist&page=1&pagesize=1000&usertype=%s";
    public static final String EXPERT_TYPE = "expert_type";

    /**
     * 专家信息筛选字段接口
     */
    public static final String PROVINCE_URL = "http://jx.12316.zq.yn15.com/Data1/Sql_Ssx.ashx?Action=QPro";
    public static final String CITY_URL = "http://jx.12316.zq.yn15.com/Data1/Sql_Ssx.ashx?Action=QCbyP&Province_val=360000";
    public static final String COUNTRY_URL = "http://jx.12316.zq.yn15.com/Data1/Sql_Ssx.ashx?Action=QXbyC&city_val=%s";
    public static final String EXPERT_TYPE_URL = "http://jx.12316.zq.yn15.com/Data1/Sql_Ssx.ashx?Action=ywzc";
    public static final String EXPERT_TYPE_RESULT_URL = BASE_URL + "/data/hr_employee.ashx?action=GetUserlist&page=%s&pagesize=%s&usertype=%s&professional=%s&areacode=%s&areatype=%s";
    public static final String EXPERT_TYPE_RESULT_URL_1 = BASE_URL + "/data/hr_employee.ashx?action=GetUserlist&page=%s&pagesize=%s&usertype=%s&professional=%s";
    public static final String EXPERT_TYPE_RESULT_URL_2 = BASE_URL + "/data/hr_employee.ashx?action=GetUserlist&page=%s&pagesize=%s&usertype=%s&areacode=%s&areatype=%s";
    public static final String EXPERT_TYPE_RESULT = BASE_URL+"/data/hr_employee.ashx?action=GetUserlist&usertype=专家";
    /**
     * 智慧农业介绍
     */
    public static final String WISDOM_CLOUD_URL = "http://zs-jiangxi.ltj.xnb365.com/zhny/ysj.html";//一云
    public static final String WISDOM_CENTER_ONE_URL = "http://zs-jiangxi.ltj.xnb365.com/zhny/center1.html";//指挥调度中心
    public static final String WISDOM_CENTER_TWO_URL = "http://zs-jiangxi.ltj.xnb365.com/zhny/center2.html";//12316服务中心
    public static final String WISDOM_PLATFORM_ONE_URL = "http://zhny.jxagri.gov.cn/jxzhny/index_nywlw006.html";//物联网平台
    public static final String WISDOM_PLATFORM_TWO_URL = "http://zs-jiangxi.ltj.xnb365.com/zhny/zlzs.html";//追溯平台
    public static final String WISDOM_PLATFORM_THREE_URL = "http://zs-jiangxi.ltj.xnb365.com/zhny/ds.html";//电商平台
    public static final String WISDOM_SYSTEM_N_URL = "http://zs-jiangxi.ltj.xnb365.com/zhny/n-pt.html";//N系统

    public final static String FEED_BACK_URL=//意见反馈
            "http://pigdoctor.ns51.yn15.com/Data1/NJB_Feedback.ashx?Action=save&FD_name=%s&T_content=%s&tel=%s";

    /*
    * 病虫害图谱
    * */
    private static final String BCH_BASE_URL="http://jx.12316.zq.yn15.com";
    public static final String BCH_TYPE_URL=BASE_URL+"/Data/Param_SysParam.ashx?Action=combo&parentid=10";
    public static final String BCH_PEST_URL=BASE_URL+"/Data1/DispestMap.ashx?Action=typegrid";
    public static final String BCH_DETAIL_URL=BASE_URL+"/Data1/DispestMap.ashx?Action=appform&nid=%s&viewNews=true&rnd=0.11111942031142685";
    public static final String BCH_CONTENT_URL=BCH_BASE_URL+"/NJB/PestDisMap/content.html?nid=%s";


    public static final String BCH_PEST_DETAIL="bch_pest_detail";
    public static final String BCH_PEST_ID="bch_pest_id";
    public static final String BCH_PEST_IMG="bch_pest_img";


    //首页专家建议和提醒
    private static final String EXPERT_BASE_URL="http://jx.12316.zq.yn15.com/";
    public static final String EXPERT_ADVISE = "http://218.65.88.24/wx_weinong/farming/api/farming";
    //原鹤壁地址：http://jx.12316.zq.yn15.com/Data1/ExpertSu.ashx?Action=grid&pagesize=
    //专家问答
    public static final String EXPERT_ANSWER = EXPERT_BASE_URL+"Data1/ExpertAnswer.ashx?Action=grid&rnd=0.27661610755332977&page=1&pagesize=";
    //市场价格
    public static final String PRICE_UPDATE = "http://jx.12316.zq.yn15.com/Data1/AgriPrice.ashx?Action=grid&rnd=0.2510426598172536&page=1&pagesize=50";
    //首页轮播图
    public static final String HOME_IMAGE = "http://jx.12316.zq.yn15.com/Data1/HomeImg.ashx?Action=grid&rnd=0.8527040916812537";
    //轮播图详情页
    public static final String HOME_IMAGE_DETAIL = "http://jx.12316.zq.yn15.com/NJB/HomeImg/htmlContent.html?nid=";


    /**
     * 12316专家咨询接口
     */
    //http://192.168.0.80:8888/manage-12316
            //http://expert.jx.gnb360.cn
    public static final String BASE_URL_EXPERTER = "http://v426.nyt.bd.ej38.com/plugin/aoya/notebook";
    //提交问题
    public static final String ADD_ISSUE = BASE_URL_EXPERTER+"/addIssue";
    //查询活跃专家以及不活跃专家
    public static final String GET_RECOMMMEND_EXPERTER = BASE_URL_EXPERTER +"/loadActiveBinding";
    //问题列表
    public static final String GET_LOADISSUELIST = BASE_URL_EXPERTER+"/wentilistapi.php";
    //关注问题
    public static final String ADD_ISSUE_COLLECT = BASE_URL_EXPERTER+"/addIssueAttention";
    //取消关注
    public static final String CANCEL_ISSUE_COLLECT = BASE_URL_EXPERTER+"/cancelIssueAttention";
    //获取问题追问回复列表
    public static final String LOAD_ISSUE_ANSWER = BASE_URL_EXPERTER+"/loadIssueAnswer";
    //获取专家问题列表
    public static final String LOAD_iSSUEANSWER_BY =BASE_URL_EXPERTER+"/loadIssueAnswerByBinding";
    //追问以及回复问题
    public static final String ADD_ISSUE_ANSWER = BASE_URL_EXPERTER+"/addIssueAnswer";
    //点赞
    public static final String ADD_BINDING_LIKE = BASE_URL_EXPERTER+"/addBindingLike";
    //取消点赞
    public static final String CANCEL_BINDING_LIKE = BASE_URL_EXPERTER+"/cancelBindingLike";
    //好中差评
    public static final String USER_COMMENT = BASE_URL_EXPERTER+"/userCommentBinding";
    //消息推送
    public static final String PUSHINFO = BASE_URL_EXPERTER+"/pushInfo";
    //获取专家信息
    public static final String LOADINFO = "http://expert.jx.gnb360.cn/loadExpertInfo";
    //修改专家信息
    public static final String ALERTINFO = BASE_URL_EXPERTER+"/alertExpertInfo";
    //修改回复文本
    public static final String ALERT_ANSWER_TEXT = BASE_URL_EXPERTER+"/alertAnswerText";
    //推送的消息数
    public static final String PUSHCOUNT = "pushCount";
    //问题类型列表接口
    public static final String WTTYPE = BASE_URL_EXPERTER+"/wttypeapi.php";
    //专家详情接口
    public static final String ISREGISTER= BASE_URL_EXPERTER+"/zdapi.php";
    //根据id获取问题详情
    public static final String GETDETAILBYID = BASE_URL_EXPERTER+"/wtdapi.php";
    //专家类型列表
    public static final String EXPERTER_TYPE = BASE_URL_EXPERTER+"/ztlistapi.php";
    //获取专家列表
    public static final String EXPERTERLIST = BASE_URL_EXPERTER+"/zlistapi.php";
    //提问
    public static final String ASK = BASE_URL_EXPERTER+"/utwapi.php";
    //回答及驳回问题
    public static final String ANSWER = BASE_URL_EXPERTER+"/zhuiapi.php";
    /**
     * 江西省测土配方施肥
     */
    public static final String SOIL_URL ="http://tufei.jxagri.gov.cn/mobile/index.asp";
    public static final String REQUEST_LOACTION = "request_loaction";
    public final static String LOCATION_RESULT = "location_result";
}
