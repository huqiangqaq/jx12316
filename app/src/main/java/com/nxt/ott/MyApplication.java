package com.nxt.ott;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chatuidemo.DemoHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nxt.ott.domain.ExperterInfo;
import com.nxt.ott.domain.Soil;
import com.nxt.ott.receiver.MyReceiver;
import com.nxt.ott.server.LongRunningService;
import com.nxt.ott.util.Utils;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.util.ZPreferenceUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/27 0027.
 */
public class MyApplication extends Application {
    private static MyApplication mMyApplication;
    private LocationClient locationClient;
    private MyLocationListener locationListener = new MyLocationListener();
    private List<Activity> activityList = new LinkedList<Activity>();
    public static Context applicationContext;
    private MyReceiver receiver = new MyReceiver();
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";
    private final static int CWJ_HEAP_SIZE = 6 * 1024 * 1024;
    private String[] types;
    private List<ExperterInfo.RowsBean> info;

    public List<ExperterInfo.RowsBean> getInfo() {
        return info;
    }

    public void setInfo(List<ExperterInfo.RowsBean> info) {
        this.info = info;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    //设置最小heap内存为6MB大小
    @Override
    public void onCreate() {
        super.onCreate();
        //Android 7.0 FileUriExposedException 解决
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //初始化首选项工具
        ZPreferenceUtils.init(getApplicationContext());
        //百度定位sdk定位
        SDKInitializer.initialize(this);
        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(locationListener);//

        mMyApplication = this;
        MultiDex.install(this);
        applicationContext = this;
        //init demo helper
        DemoHelper.getInstance().init(applicationContext);

        Utils.init(this);
        initImageLoader(this);
        super.onCreate();
    }

    public static synchronized MyApplication getInstance() {
        return mMyApplication;
    }

    public ZDataTask getZDataTask() {
        return ZDataTask.getInstance(mMyApplication);
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    private void initlocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);//获取地址
        option.setScanSpan(1000 * 60);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationClient.setLocOption(option);
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Soil soil = new Soil();
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            String province = location.getProvince();
            String city = location.getCity();
            String dis = location.getDistrict();
            String street = location.getStreet();
            String streetNumber = location.getStreetNumber();
            float radius = location.getRadius();
            soil.setLatitude(lat);
            soil.setLongitude(lon);
            soil.setProvince(province);
            soil.setCity(city);
            soil.setDistrict(dis);
            soil.setStreet(street);
            soil.setStreetNumber(streetNumber);
            soil.setRadius(radius);
            getLocationToSend(soil);
//            System.out.println("city-------->" + city + "dis------>" + dis);
//            if (!TextUtils.isEmpty(dis) && !TextUtils.isEmpty(city)) {
//                if (dis.contains(getString(R.string.city)) || dis.contains(getString(R.string.xian))) {
//                    sendBroadCast(dis);
//                } else {
//                    sendBroadCast(city);
//                }
//            } else {
//                sendBroadCast(getString(R.string.nanchang));
//
//            }
        }



    }

    private void getLocationToSend(Soil soil) {
        stopLocationClient();
        Intent intent = new Intent(Constant.REQUEST_LOACTION);
        intent.putExtra(Constant.LOCATION_RESULT,soil);
        sendBroadcast(intent);
    }

    /**
     * 获取当前登陆用户名
     *
     * @return
     */
    public String getUserName() {
//        return EMClient.getInstance().getCurrentUser();
        return "";
    }

    /**
     * 得到发送广播
     *
     * @param
     */
    public void sendBroadCast(String city) {
        stopLocationClient();
        Intent intent = new Intent(Constant.REQUEST_LOACTION);
        intent.putExtra(Constant.LOCATION_AREA, city);
        sendBroadcast(intent);
    }

    /**
     * 停止定位
     */
    public void stopLocationClient() {
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
        }
    }

    /**
     * 发起定位
     */
    public void requestLocationInfo() {
        initlocation();

        if (locationClient != null && !locationClient.isStarted()) {
            locationClient.start();
        }

        if (locationClient != null && locationClient.isStarted()) {
            locationClient.requestLocation();
        }
    }

    private void initImageLoader(Context context) {
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(getCacheDir()))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
//                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(10 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.test)
                        .showImageForEmptyUri(R.mipmap.test)
                        .showImageOnFail(R.mipmap.test)
                        .resetViewBeforeLoading(true)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build())
                .build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
        MultiDex.install(this);
    }

    public void openService() {
        Intent intent = new Intent(this, LongRunningService.class);
        startService(intent);
    }

    public void openReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.broadcast.RECEIVER_ACTION");
        registerReceiver(receiver, filter);
    }

}
