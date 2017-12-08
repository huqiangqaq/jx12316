package com.nxt.ott.activity.convenience;import android.content.Intent;import android.view.LayoutInflater;import android.view.View;import com.nxt.ott.R;import com.nxt.ott.activity.WebActivity;import com.nxt.ott.base.BaseZoomTitleActivity;import com.nxt.ott.Constant;/** * Created by iwon on 2016/6/24. */public class WaterAndeElectricityActivity extends BaseZoomTitleActivity {    private String url,title;    private View contentview;    @Override    protected void initView() {        application.addActivity(this);        initTopbar(this, getString(R.string.waterorele));        contentview= LayoutInflater.from(this).inflate(R.layout.activity_water_electricity_choose, null);        parentlayout.addView(contentview);        contentview.findViewById(R.id.layout_pay_cn).setOnClickListener(this);        contentview.findViewById(R.id.layout_pay_yns).setOnClickListener(this);    }    @Override    protected int getLayout() {        return R.layout.common_scrollview;    }    @Override    public void onClick(View v) {        switch (v.getId()){            case R.id.layout_pay_cn:                url= Constant.SERVICE_WATER_AND_ELECTRICITY_CN_URL;                title=getString(R.string.pay_cn);                break;            case R.id.layout_pay_yns:                url= Constant.SERVICE_WATER_AND_ELECTRICITY_YNS_URL;                title=getString(R.string.yns);                break;        }        startActivity(new Intent(this,WebActivity.class).putExtra("title",title).putExtra("url",url));        super.onClick(v);    }}