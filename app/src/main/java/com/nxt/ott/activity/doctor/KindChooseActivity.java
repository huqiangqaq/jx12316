package com.nxt.ott.activity.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.R;
import com.nxt.ott.adapter.KindChooseAdapter;
import com.nxt.ott.base.BaseListActivity;
import com.nxt.ott.domain.DiseaseType;
import com.nxt.ott.Constant;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangyonglu on 2016/3/15 0015.
 */
public class KindChooseActivity extends BaseListActivity {
    private String type, titletype, urlitem;
    private String url, kinditemstr;
    private List<DiseaseType> diseaseTypeList;
    private List<String> kindlist, diseaselist = new ArrayList<>();
    private DiseaseType diseaseType;
    private static int KINDRESULT = 0, DISEASERESULT = 1;
    private int tag;

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        Log.e("kind-44","type--------->" + type);
        titletype = getIntent().getStringExtra("titletype");
        initTopbar(this, titletype);
        if (titletype.equals(getString(R.string.disease_choose))) {
            seturlitem();
        }
        refresh();

    }

    //对应症状选择
    private void seturlitem() {
        switch (type) {
            case "猪":
                urlitem = "pig";
                break;
            case "牛":
                urlitem = "cow";
                break;
            case "羊":
                urlitem = "sheep";
                break;
            case "鸡":
                tag = 1;
                urlitem = "chicken";
                break;
            case "鸭":
                urlitem = "duck";
                break;
            case "鹅":
                urlitem = "goose";
                break;
            case "棉花":
                urlitem = "cotton";
                break;
            case "水稻":
                urlitem = "rice";
                break;
            case "玉米":
                urlitem = "corn";
                break;
            case "小麦":
                urlitem = "wheat";
                break;
            case "大豆":
                urlitem = "soybean";
                break;
            case "梨":
                urlitem = "pear";
                break;
            case "桃":
                urlitem = "peach";
                break;
            case "苹果":
                urlitem = "apple";
                break;
            case "西瓜":
                urlitem = "watermelon";
                break;
            case "黄瓜":
                urlitem = "cucumber";
                break;
            case "番茄":
                urlitem = "tomato";
                break;
            case "鳊鱼":
                urlitem = "Bream";
                break;
            case "鲶鱼":
                urlitem = "Catfish";
                break;
            case "鳢鱼":
                urlitem = "Channa";
                break;
            case "鲮鱼":
                urlitem = "Dace";
                break;
            case "鳗鱼":
                urlitem = "Eel";
                break;
            case "金鱼及其他观赏鱼":
                urlitem = "Goldfish";
                break;
            case "鲂鱼":
                urlitem = "Gurnard";
                break;
            case "其他鱼":
                urlitem = "Otherrarefish";
                break;
            case "鳜鱼":
                urlitem = "Siniperca";
                break;
            case "罗非鱼":
                urlitem = "Tilapia";
                break;
            case "鳟鱼":
                urlitem = "Trout";
                break;
            case "雅罗鱼":
                urlitem = "Ugui";
                break;
            case "鲈鱼":
                urlitem = "Weever";
                break;
            case "白鲌鱼":
                urlitem = "WhiteCulter";
                break;
            case "牛蛙":
                urlitem = "bullfrog";
                break;
            case "蟹":
                urlitem = "crab";
                break;
            case "虾":
                urlitem = "shrimp";
                break;
            case "龟":
                urlitem = "tortoise";
                break;
            case "鳖":
                urlitem = "turtle";
                break;


        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_agriculture_news;
    }

    private void refresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0, 500);

            }
        });
    }

    @Override
    public void onRefresh() {
        getdisease();
        super.onRefresh();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (titletype.equals(getString(R.string.kind_choose))) {
                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
                switch (type) {
                    case "家禽":
                        kindlist = Arrays.asList(getResources().getStringArray(R.array.poultry_list));
                        break;
                    case "畜牧":
                        kindlist = Arrays.asList(getResources().getStringArray(R.array.raise_livestock_list));
                        break;
                    case "农产品":
                        kindlist = Arrays.asList(getResources().getStringArray(R.array.crops_list));
                        break;
                    case "果蔬":
                        kindlist = Arrays.asList(getResources().getStringArray(R.array.garden_stuff_list));
                        break;
                    case "水产品":
                        kindlist = Arrays.asList(getResources().getStringArray(R.array.waterproduct_list));
                        break;
                    case "更多":
                        ZToastUtils.showShort(KindChooseActivity.this, R.string.null_data);
                        break;
                }
                mlistview.setAdapter(new KindChooseAdapter(KindChooseActivity.this, kindlist));
            } else {
                getdisease();
            }
            super.handleMessage(msg);
        }
    };

    private void getdisease() {
        if (CommonUtils.isNetWorkConnected(this)) {
            url = String.format(Constant.DISEATE_TYPE_URL, urlitem);
            System.out.println("url----------->" + url);
            zDataTask.get(url, null, null, this);
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }

    @Override
    public void onRequestResult(String string) {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);

        if (diseaseTypeList != null) diseaseTypeList.clear();
        diseaselist.clear();
        try {
            String result = URLDecoder.decode(string, "gbk");
            System.out.println("result----------->" + result);

            if (!TextUtils.isEmpty(result)) {
                JSONObject json = new JSONObject(result);
                if ("success".equals(json.getString("code"))) {
                    diseaseTypeList = new Gson().fromJson(json.getString("infos"),
                            new TypeToken<List<DiseaseType>>() {
                            }.getType());
                    System.out.println("diseaseTypeList size" + diseaseTypeList.size());
                    for (int i = 0; i < diseaseTypeList.size(); i++) {
                        DiseaseType diseaseType = diseaseTypeList.get(i);
                        diseaselist.add(diseaseType.getChineseName());
                    }
                    mlistview.setAdapter(new KindChooseAdapter(this, diseaselist));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onRequestResult(string);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("listview click---------------");
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        if (titletype.equals(getString(R.string.kind_choose))) {
            bundle.putString("data", kindlist.get(position - 1));
            intent.putExtras(bundle);
            setResult(KINDRESULT, intent);
        } else {
            bundle.putSerializable("data", diseaseTypeList.get(position - 1));
            intent.putExtras(bundle);
            setResult(DISEASERESULT, intent);
        }
        finish();

    }

    @Override
    public void onLeftClick(View view) {
        finish();
        super.onLeftClick(view);
    }
}
