package com.nxt.ott.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;
import com.nxt.ott.Constant;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.activity.SoilActivity;
import com.nxt.ott.activity.WebActivity;
import com.nxt.ott.activity.doctor.DoctorMenuActivity;
import com.nxt.ott.activity.expertadvise.ExperterAdviseActivity;
import com.nxt.ott.activity.scan.ProductScanActivity;
import com.nxt.ott.activity.titlebottom.AreaSelectActivity;
import com.nxt.ott.activity.titlebottom.WeatherDetailActivity;
import com.nxt.ott.adapter.HomeQuestionAdapter;
import com.nxt.ott.domain.Answer;
import com.nxt.ott.domain.ExpertAdvise;
import com.nxt.ott.domain.HotExperter;
import com.nxt.ott.domain.Soil;
import com.nxt.ott.domain.Weather;
import com.nxt.ott.domain.WeatherUpdate;
import com.nxt.ott.expertUpdate.AnswerList_Activity;
import com.nxt.ott.expertUpdate.AskActivity;
import com.nxt.ott.expertUpdate.DetailActivity;
import com.nxt.ott.expertUpdate.ExperterListActivity;
import com.nxt.ott.util.CharacterParser;
import com.nxt.ott.util.FileUtil;
import com.nxt.ott.util.UpdateManager;
import com.nxt.ott.util.UpdateUIThread;
import com.nxt.ott.util.WeatherUtil;
import com.nxt.zyl.data.volley.toolbox.HttpCallback;
import com.nxt.zyl.ui.fragment.ZBaseFragment;
import com.nxt.zyl.ui.widget.AVLoadingIndicatorView;
import com.nxt.zyl.ui.widget.roundedimageview.CustomImageView;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZPreferenceUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zhy.com.highlight.HighLight;


/**
 * Update by iwon on 2016/6/19 19:49.
 */
public class HomeFragment extends ZBaseFragment implements HttpCallback, EasyPermissions.PermissionCallbacks {
    private BroadcastReceiver broadcastReceiver;
    private TextView locationtext, weathertext, temperaturetext, percenttext, scheduletext, tv_experter_suggest, tv_answer_more;
    private boolean isregister = false;
    private AVLoadingIndicatorView loadingIndicatorView;
    private final static int REQUEST_CODE = 10;
    private ImageView weatherimg;
    private AlertDialog installDialog;
    private ProgressBar progressbar;
    private UpdateUIThread mUpdateUIThread;
    private AlertDialog downloaddialog;
    private CharacterParser characterParser;
    private List<Weather> weatherList;
    private List<WeatherUpdate> weatherList_update;
    private String weatherstr;
    private LinearLayout weatherlayout;
    private FrameLayout vlayout;
    private CustomImageView personimg;
    private RelativeLayout leftlayout;
    private View contentview;
    private String pakagename;
    private String url, noticedetail;
    private int clickCount = 0;
    private int homeCount = 0;

    //新增首页天气详情及意见
    private ImageView weather_img_update, weather_img_update2;
    private TextView weatheer_update, weather_tem_update, weatheer_update2, weather_tem_update2, more;
    private ViewFlipper viewFlipper;
    private Animation from_down_in, from_up_out;
    private HighLight mHightLight;
    private RelativeLayout rl_quick, rl_find;
    private RecyclerView rv_redian;
    private ArrayList<HotExperter> hotExperters = new ArrayList<>();
    private List<Answer.DataBean> answers = new ArrayList<>();
    private HomeQuestionAdapter homeQuestionAdapter;
    public static final int PERMISSION_CODE = 100;

    @Override
    protected void initView(final View view) {

        loadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.loading);
//        String[] tarray = new String[]{"小麦冬季防寒", "玉米丰收", "2015展望未来", "小麦冬季防寒", "小麦冬季防寒"};
//        int[] imgarray = new int[]{//轮播图片
//                R.mipmap.banner_02,
//                R.mipmap.banner,
//                R.mipmap.head_img_three,
//                R.mipmap.head_img_four,
//                R.mipmap.head_img_five,
//        };
//        headlayout = (LinearLayout) view.findViewById(R.id.layout_main_viewpager);
//        vlayout = (FrameLayout) getActivity().getLayoutInflater().inflate(R.layout.layout_viewpager, null);
//        pb_image = (ProgressBar) vlayout.findViewById(R.id.pb_image);
//        headlayout.addView(vlayout);
        //获取轮播图图片
//        final ListDataSave imgSave = new ListDataSave(getActivity(),"homeImage");
//        List<HomeImage.RowsBean> ss =imgSave.getDataList("homeImage",HomeImage.RowsBean.class);
//        if (ss==null){
//            OkhttpHelper.getInstance().Get(Constant.HOME_IMAGE, new OkhttpHelper.MyCallBack() {
//                @Override
//                public void onSucces(String response, int tag) {
//                    if (pb_image.isShown()){
//                        pb_image.setVisibility(View.GONE);
//                    }
//                    homeImage = new Gson().fromJson(response,HomeImage.class);
//                    imageList = homeImage.getRows();
//                    imgSave.setDataList("homeImage",imageList);
//                    new ViewPagerUtil().initVeiwPager(vlayout, imageList, getActivity());//ViewPager
//                }
//                @Override
//                public void onFaile(Exception errorResponse, int tag) {
//
//                }
//            },1);
//        }else {
//            pb_image.setVisibility(View.GONE);
//            new ViewPagerUtil().initVeiwPager(vlayout, imgSave.getDataList("homeImage",HomeImage.RowsBean.class),getActivity());
//
////            new ViewPagerUtil().initVeiwPager(vlayout,imgSave.getDataListHomeImage("homeImage"),getActivity());
//        }


        locationtext = (TextView) view.findViewById(R.id.tv_home_title);
        weathertext = (TextView) view.findViewById(R.id.tv_home_weather);
        temperaturetext = (TextView) view.findViewById(R.id.tv_home_temperature);
        weatherimg = (ImageView) view.findViewById(R.id.img_weather);
        weatherlayout = (LinearLayout) view.findViewById(R.id.ll_agriculture_weather);
        characterParser = CharacterParser.getInstance();

        weather_img_update = (ImageView) view.findViewById(R.id.weather_img_update);
        weatheer_update = (TextView) view.findViewById(R.id.weatheer_update);
        weather_tem_update = (TextView) view.findViewById(R.id.weather_tem_update);

        weather_img_update2 = (ImageView) view.findViewById(R.id.weather_img_update2);
        weatheer_update2 = (TextView) view.findViewById(R.id.weatheer_update2);
        weather_tem_update2 = (TextView) view.findViewById(R.id.weather_tem_update2);
        tv_experter_suggest = (TextView) view.findViewById(R.id.tv_experter_suggest);
        rl_quick = (RelativeLayout) view.findViewById(R.id.rl_quick);
        rl_find = (RelativeLayout) view.findViewById(R.id.rl_find);
        more = (TextView) view.findViewById(R.id.more);
        setTouchDelegate(more, 200);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        rv_redian = (RecyclerView) view.findViewById(R.id.rv_redian);
        rv_redian.setLayoutManager(manager);
        rv_redian.setHasFixedSize(false);
        rv_redian.setNestedScrollingEnabled(false);


        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFilpper);
        from_down_in = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_from_down_in);
        from_up_out = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_from_up_out);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setInAnimation(from_down_in);
        viewFlipper.setOutAnimation(from_up_out);
        viewFlipper.startFlipping();
        viewFlipper.setAutoStart(true);

        locationtext.setOnClickListener(this);
        weathertext.setOnClickListener(this);
        weatherlayout.setOnClickListener(this);
        tv_experter_suggest.setOnClickListener(this);
        rl_quick.setOnClickListener(this);
        rl_find.setOnClickListener(this);
        more.setOnClickListener(this);

        view.findViewById(R.id.ll_experts).setOnClickListener(this);
        view.findViewById(R.id.tv_county_play).setOnClickListener(this);
        view.findViewById(R.id.ib_soil).setOnClickListener(this);
        view.findViewById(R.id.ib_scan).setOnClickListener(this);
        methodRequiresTwoPermission();
        getExpertAdvise();
//        getHotExperter();
        getHotQuestions();
        //自动检测更新并安装
        new UpdateManager(getActivity(), true).checkUpdate();
//        if (ZPreferenceUtils.getPrefBoolean(Constant.IS_OPEN_GUIDE_HOME,false)||ZPreferenceUtils.getPrefInt(Constant.IS_FIRST_HOME,0)==0){
//            //判断Activity完全加载完布局后，显示高亮图层
//            getActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    getActivity().getWindow().getDecorView().getViewTreeObserver()
//                            .removeOnGlobalLayoutListener(this);
//                    // do your code here
//                    mHightLight = new HighLight(getActivity())
//                            .autoRemove(false)
//                            .setClickCallback(new HighLightInterface.OnClickCallback() {
//                                @Override
//                                public void onClick() {
////                                    Toast.makeText(getActivity(), "clicked and remove HightLight view by yourself", Toast.LENGTH_SHORT).show();
//                                    remove(null);
//                                }
//                            })
//                            .anchor(getActivity().findViewById(R.id.drawer_lauout))
//                            .addHighLight(home_expert_ask1,R.layout.info_known,new OnRightPosCallback(45),new RectLightShape());
//                    mHightLight.show();
//                    homeCount++;
//                    ZPreferenceUtils.setPrefInt(Constant.IS_FIRST_HOME,homeCount);
//                }
//            });
//        }

    }

    private void getHotQuestions() {
        String user = ZPreferenceUtils.getPrefString(Constant.USERNAME, "");
        OkGo.post(Constant.GET_LOADISSUELIST)
                .params("pageNum", 1)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Answer answer = new Gson().fromJson(s, Answer.class);
                        answers = answer.getData();
                        homeQuestionAdapter = new HomeQuestionAdapter(answers);

                        if (answers != null) {
                            if (answers.size() == 0) {
                                View view = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) rv_redian.getParent(), false);
                                TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
                                tv_message.setText("暂时还没有问题!");
                                homeQuestionAdapter.setEmptyView(view);
                            }
                            rv_redian.setAdapter(homeQuestionAdapter);
                            homeQuestionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                                    intent.putExtra("id", answers.get(position).getId());
                                    startActivity(intent);
                                }
                            });
                            View foot = LayoutInflater.from(getActivity()).inflate(R.layout.more_foot, (ViewGroup) rv_redian.getParent(), false);
                            TextView tv_more = (TextView) foot.findViewById(R.id.tv_more);
                            tv_more.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getActivity(), AnswerList_Activity.class));
                                }
                            });
                            homeQuestionAdapter.addFooterView(foot);
//                            if (ZPreferenceUtils.getPrefBoolean(Constant.IS_OPEN_GUIDE_HOME,false)||ZPreferenceUtils.getPrefInt(Constant.IS_FIRST_CLICK,0)==0){
//                                getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                                    @Override
//                                    public void onGlobalLayout() {
//                                        getWindow().getDecorView().getViewTreeObserver()
//                                                .removeOnGlobalLayoutListener(this);
//                                        showNextKnownTipView();
//                                        ZPreferenceUtils.setPrefInt(Constant.IS_FIRST_CLICK,1);
//                                    }
//                                });
//                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        getExpertAdvise();
    }

    private void getExpertAdvise() {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "1");
        params.put("pageNum", "1");
        params.put("count", "10");
        params.put("sort", "desc");
        params.put("province", ZPreferenceUtils.getPrefString(Constant.PROVINCE, ""));
        params.put("city", ZPreferenceUtils.getPrefString(Constant.CITY, ""));
        params.put("county", ZPreferenceUtils.getPrefString(Constant.DISTRICT, ""));
        JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Constant.EXPERT_ADVISE)
                .upJson(jsonObject)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        loadingIndicatorView.setVisibility(View.GONE);
                        ExpertAdvise expertAdvise = new Gson().fromJson(s, ExpertAdvise.class);
                        List<ExpertAdvise.DataBean> list = expertAdvise.getData();
                        if (list.size() > 0) {
                            String content = list.get(0).getContent();
                            if (content.contains("<")) {
                                tv_experter_suggest.setText(Html.fromHtml(content));
                            } else {
                                tv_experter_suggest.setText(content);
                            }

                        }
                    }

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        loadingIndicatorView.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void getlocation() {
        if (TextUtils.isEmpty(ZPreferenceUtils.getPrefString(Constant.NOW_AREA, ""))) {
            loadingIndicatorView.setVisibility(View.VISIBLE);
            registerBroadCastReceiver();
            isregister = true;
            MyApplication.getInstance().requestLocationInfo();//请求开始定位
        } else {
            locationtext.setText(ZPreferenceUtils.getPrefString(Constant.NOW_AREA, ""));
            getweather();
        }

    }

    /**
     * 申请权限
     */
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            //已经获得权限
            getlocation();
        } else {
            EasyPermissions.requestPermissions(this, "需要定位、照相、录音以及拨打电话的权限", PERMISSION_CODE, perms);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    /**
     * 注册一个广播，监听定位结果
     */
    private void registerBroadCastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Soil soil = intent.getParcelableExtra(Constant.LOCATION_RESULT);
                String dis = soil.getDistrict();
                String city = soil.getCity();
                /*存储定位的省、市、县定位数据*/
                ZPreferenceUtils.setPrefString(Constant.PROVINCE, soil.getProvince());
                ZPreferenceUtils.setPrefString(Constant.CITY, soil.getCity());
                ZPreferenceUtils.setPrefString(Constant.DISTRICT, soil.getDistrict());
                String address;
                if (!TextUtils.isEmpty(dis) && !TextUtils.isEmpty(city)) {
                    if (dis.contains(getString(R.string.city)) || dis.contains(getString(R.string.xian))) {
                        address = dis;
                    } else {
                        address = city;
                    }
                } else {
                    address = getString(R.string.nanchang);

                }
                if (TextUtils.isEmpty(address)) {
                    locationtext.setText(getActivity().getString(R.string.nanchang));
                } else {
                    locationtext.setText(address);
//                    weather_location_update.setText(address);
                }
                getweather();
            }
        };
        ZPreferenceUtils.setPrefString(Constant.NOW_AREA, locationtext.getText().toString());
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction(Constant.REQUEST_LOACTION);
        getActivity().registerReceiver(broadcastReceiver, intentToReceiveFilter);
    }

    @Override
    public void onDestroy() {
        if (isregister) {
            getActivity().unregisterReceiver(broadcastReceiver);
            isregister = false;
        }
        ZPreferenceUtils.setPrefString(Constant.NOW_AREA, locationtext.getText().toString());
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home_title://地区定位
                startActivityForResult(new Intent(getActivity(), AreaSelectActivity.class).putExtra("area", locationtext.getText()), REQUEST_CODE);
                break;

            case R.id.ll_agriculture_weather://天气详情
                startActivity(new Intent(getActivity(), WeatherDetailActivity.class)
                        .putExtra("data", weatherstr)
                        .putExtra("area", locationtext.getText().toString()));
                break;
            case R.id.tv_home_weather://天气详情
                startActivity(new Intent(getActivity(), WeatherDetailActivity.class)
                        .putExtra("data", weatherstr)
                        .putExtra("area", locationtext.getText().toString()));
                break;
            case R.id.ll_experts://农大夫
                startActivity(new Intent(getActivity(), DoctorMenuActivity.class));
                break;
            case R.id.rl_quick:
                startActivity(new Intent(getActivity(), AskActivity.class).putExtra("isExperter", false));
                break;
            case R.id.rl_find:
                startActivity(new Intent(getActivity(), ExperterListActivity.class));
                break;
            case R.id.more:
                startActivity(new Intent(getActivity(), AnswerList_Activity.class));
                break;
            case R.id.tv_county_play://12316广播
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title", "12316广播").putExtra("url", "http://mp.weixin.qq.com/mp/homepage?__biz=MzAwOTAzMjYxNg==&hid=13&sn=5436bab3c5f5706f40c2658db1bc7893&uin=MTUyNTQwMDczMw%3D%3D&key=011ea695823842f6c6168ac8547ac5ec5859df2ce396699eebaf2e136ec255d02769687d92d8c7c2511177c884bd99e0be5b0d597708aab970654037a24ea62cdc70f930abf5667439370dda81bc472d&devicetype=android-19&version=26050433&lang=zh_CN&nettype=WIFI&pass_ticket=QGTOAwneNyMkciwStfSUT2ilzgMbGwb9xa7qUyyYR9Qq287gnNloZzinUw%2BKG%2B87&wx_header=1&scene=1"));
//                startActivity(new Intent(getActivity(), FarmLessonActivity.class));
                break;
            case R.id.img_close:
                downloaddialog.dismiss();
                mUpdateUIThread.interrupt();
                break;
            case R.id.tv_experter_tel:
                Uri uri = Uri.parse("tel:" + "12316");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_experter_suggest: //农事提醒
                startActivity(new Intent(getActivity(), ExperterAdviseActivity.class));
                break;
            case R.id.ib_soil:    //测土配方
                startActivity(new Intent(getActivity(), SoilActivity.class));
                break;
            case R.id.ib_scan: //追溯
                startActivity(new Intent(getActivity(), ProductScanActivity.class));
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    private void download() {
        if (FileUtil.checkInstall(getActivity(), pakagename)) {
            Intent LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(pakagename);
            startActivity(LaunchIntent);
            return;
        }
        showInstallDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                locationtext.setText(data.getStringExtra("area"));
                loadingIndicatorView.setVisibility(View.VISIBLE);
                getweather();
            }
        }
    }

    private void getweather() {
        try {
            String city = URLEncoder.encode(locationtext.getText().toString(), "utf-8");
            if (CommonUtils.isNetWorkConnected(getActivity())) {
                MyApplication.getInstance().getZDataTask().get(String.format(Constant.WEASER_URL, city), null, null, this);
            } else {
                ZToastUtils.showShort(getActivity(), R.string.net_error);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestFinish() {

    }

    @Override
    public void onRequestResult(String string) {//天气情况的获取
        Log.e("weather-288", "result----------->" + string);
        if (string.contains("success")) {
            JSONObject js = null;
            try {
                js = new JSONObject(string);
                String results = js.getString("results");
                JSONArray array = new JSONArray(results);
                JSONObject object = (JSONObject) array.get(0);
                weatherstr = object.getString("weather_data");
                weatherList = new Gson().fromJson(weatherstr, new TypeToken<List<Weather>>() {
                }.getType());
                weatherList_update = new Gson().fromJson(weatherstr, new TypeToken<List<WeatherUpdate>>() {
                }.getType());
                Weather weather = weatherList.get(0);
                WeatherUpdate update = weatherList_update.get(0);
                weathertext.setText(weather.getWeather());
                String str = weatherList.get(0).getDate();
                String spStr[] = str.split("\\(");
                String temperature = spStr[1].trim();
                String spStr1[] = temperature.split("\\)");
                String temperature1 = spStr1[0].trim();
                String spStr2[] = temperature1.split("：");
                String temperature2 = spStr2[1].trim();
                temperaturetext.setText(temperature2);
                Log.e("temperature", "temperature------->" + temperature2);
                weatherimg.setImageResource(WeatherUtil.getImageresource(weather.getWeather(), "biz_plugin_weather_"));

                ZPreferenceUtils.setPrefString(Constant.WEATHER_DATA, weatherstr);
                ZPreferenceUtils.setPrefString(Constant.WEATHER_AREA, locationtext.getText().toString());

                Glide.with(getActivity())
                        .load(WeatherUtil.getImageresource(update.getWeather(), "biz_plugin_weather_"))
                        .into(weather_img_update);
                Glide.with(getActivity())
                        .load(WeatherUtil.getImageresource(update.getWeather(), "biz_plugin_weather_"))
                        .into(weather_img_update2);
                weatheer_update.setText(update.getWeather());
                weatheer_update2.setText(update.getWeather());
                weather_tem_update.setText(temperature2);
                weather_tem_update2.setText(temperature2);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        loadingIndicatorView.setVisibility(View.GONE);
    }

    @Override
    public void onRequestError(Exception e) {
        loadingIndicatorView.setVisibility(View.GONE);


    }

    @Override
    public void onRequestCancelled() {

    }

    @Override
    public void onRequestLoading(long count, long current) {

    }

    private void showInstallDialog() {
        installDialog = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.reminder))
                .setMessage(noticedetail)
                .setPositiveButton(getString(R.string.goon), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDownloadDialog();
                        installDialog.dismiss();

                    }
                }).setNegativeButton(getString(R.string.cancel), null).create();

        if (!installDialog.isShowing()) {
            installDialog.show();
        }

    }

    private void showDownloadDialog() {
        mUpdateUIThread = new UpdateUIThread(handler, url, FileUtil.setMkdir(getActivity()) + File.separator, FileUtil.getFileName(Constant.JIANGXI_WLWAPK_URL));

        downloaddialog = new AlertDialog.Builder(getActivity()).create();

        View downloadview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_download_dialog, null);
        downloadview.findViewById(R.id.img_close).setOnClickListener(this);
        percenttext = (TextView) downloadview.findViewById(R.id.tv_percent);
        scheduletext = (TextView) downloadview.findViewById(R.id.tv_download_number);
        progressbar = (ProgressBar) downloadview.findViewById(R.id.pb_download);
        downloaddialog.setView(downloadview);
        downloaddialog.show();
        mUpdateUIThread.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FileUtil.startDownloadMeg:
                    progressbar.setMax(mUpdateUIThread.getFileSize());   //开始
                    break;
                case FileUtil.updateDownloadMeg:
                    if (!mUpdateUIThread.isCompleted())   //下载
                    {
                        progressbar.setProgress(mUpdateUIThread.getDownloadSize());
                        percenttext.setText(mUpdateUIThread.getDownloadSpeed() + "k/秒");
                        scheduletext.setText(mUpdateUIThread.getDownloadPercent() + "%");
                    } else {
                        percenttext.setText("下载完成");
                        scheduletext.setVisibility(View.GONE);
                    }
                    break;
                case FileUtil.endDownloadMeg:

                    downloaddialog.dismiss();
//                    mUpdateUIThread.stop();
                    Toast.makeText(getActivity(), "下载完成,马上安装", Toast.LENGTH_SHORT).show();

				/*apk安装界面跳转*/
                    String filename = FileUtil.getFileName(Constant.JIANGXI_WLWAPK_URL);
                    String str = "/myfile/" + filename;
                    String fileName = Environment.getExternalStorageDirectory() + str;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                    startActivity(intent);

                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void remove(View view) {
        mHightLight.remove();
    }

    /**
     * 提升点击范围
     *
     * @param view
     * @param expandTouchWidth
     */
    private void setTouchDelegate(final View view, final int expandTouchWidth) {
        final View parentView = (View) view.getParent();
        parentView.post(new Runnable() {
            @Override
            public void run() {
                final Rect rect = new Rect();
                view.getHitRect(rect);
                rect.top -= expandTouchWidth;
                rect.bottom += expandTouchWidth;
                rect.left -= expandTouchWidth;
                rect.right += expandTouchWidth;
                TouchDelegate touchDelegate = new TouchDelegate(rect, view);
                parentView.setTouchDelegate(touchDelegate);
            }
        });
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        getlocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //这里需要重新设置Rationale和title，否则默认是英文格式
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                    .setTitle("必需权限")
                    .build()
                    .show();
        }
    }
}
