package com.nxt.ott.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.nxt.ott.Constant;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.Soil;
import com.nxt.ott.util.DialogHelper;
import com.nxt.ott.util.LocationUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;


public class SoilActivity extends BaseTitleActivity {

    @BindView(R.id.ll_shoudong)
    LinearLayout ll_shoudong;
    @BindView(R.id.ll_gps)
    LinearLayout ll_gps;
    @BindView(R.id.mapview)
    MapView mapView;
    private BroadcastReceiver receiver;
    private Soil locationResult;//百度定位的结果
    private BaiduMap mBaiduMap;
    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this, "江西省测土配方施肥");
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        ll_shoudong.setOnClickListener(this);
        ll_gps.setOnClickListener(this);
        getLocation();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_soil;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_shoudong:
                startActivity(new Intent(SoilActivity.this, WebActivity.class).putExtra("title", "手动选择地块配方施肥")
                        .putExtra("url", Constant.SOIL_URL));
                break;
            case R.id.ll_gps:
                if (!LocationUtils.isGpsEnabled() | !LocationUtils.isLocationEnabled()) {
                    DialogHelper.showDialog(this, "提示", "当前GPS定位不可用,请在设置中打开GPS定位选项", new DialogHelper.OnOkClickListener() {
                        @Override
                        public void onOkClick() {
                            LocationUtils.openGpsSettings();
                        }
                    }, new DialogHelper.OnCancelClickListener() {
                        @Override
                        public void onCancelClick() {

                        }
                    });
                    return;
                }
                mapView.setVisibility(View.VISIBLE);
                showLoadingDialog(R.string.location);
                MyApplication.getInstance().requestLocationInfo();
                break;
            default:
                break;
        }
    }

    private void getLocation() {
        registerBroadCastReceiver();
    }

    private void registerBroadCastReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                locationResult = intent.getParcelableExtra(Constant.LOCATION_RESULT);
                if (locationResult != null) {
                    mBaiduMap.setMyLocationEnabled(true);  //开启定位图层
                    //构造定位数据
                    MyLocationData locationData = new MyLocationData.Builder()
                            .accuracy(locationResult.getRadius())
                            .direction(100).latitude(locationResult.getLatitude())
                            .longitude(locationResult.getLongitude()).build();
                    //设置定位数据
                    mBaiduMap.setMyLocationData(locationData);
                    BitmapDescriptor marker = null;
                    MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,marker);
                    mBaiduMap.setMyLocationConfigeration(config);
                LatLng latLng = new LatLng(locationResult.getLatitude(),locationResult.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(18f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    try {
                        String map_jd = URLEncoder.encode(locationResult.getLongitude() + "", "GBK");
                        String map_wd = URLEncoder.encode(locationResult.getLatitude() + "", "GBK");
                        String map_city = URLEncoder.encode(locationResult.getCity(), "GBK");
                        String map_district = URLEncoder.encode(locationResult.getDistrict(), "GBK");
                        String map_street = URLEncoder.encode(locationResult.getStreet(), "GBK");
                        String map_streetNumber = URLEncoder.encode(locationResult.getStreetNumber(), "GBK");
                        final String url = "http://tufei.jxagri.gov.cn/mobile/index_zd.asp?"
                                + "map_jd=" + map_jd
                                + "&map_wd=" + map_wd
                                + "&map_city=" + map_city
                                + "&map_district=" + map_district
                                + "&map_street=" + map_street
                                + "&map_streetNumber=" + map_streetNumber;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissLoadingDialog();
                                        startActivity(new Intent(SoilActivity.this, WebActivity.class).putExtra("title", "GPS定位附近地块配方施肥")
                                                .putExtra("url", url).putExtra("isSoil", true));
                                        mapView.setVisibility(View.GONE);
                                    }
                                });
                            }
                        },3000);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.REQUEST_LOACTION);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
