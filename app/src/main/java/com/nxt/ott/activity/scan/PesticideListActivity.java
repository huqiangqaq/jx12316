package com.nxt.ott.activity.scan;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.R;
import com.nxt.ott.adapter.PesticideAdapter;
import com.nxt.ott.base.BaseListActivity;
import com.nxt.ott.domain.Pesticide;
import com.nxt.ott.Constant;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Updated by iwon on 2016/6/22 11:20.
 */
public class PesticideListActivity extends BaseListActivity{
    private ListView listView;
    private int type,start=0;
    private String typename,url;
    private List<Pesticide> pesticideList;
    private ZDataTask zDataTask;
    private int parentindex,itemindex;


    @Override
    protected void initView() {
        initTopbar(this,getString(R.string.search_result));
        zDataTask=application.getZDataTask();
        typename=getIntent().getStringExtra("typename");
        try {
            typename= URLEncoder.encode(typename,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        parentindex=getIntent().getIntExtra("parentindex", 0);
        itemindex=getIntent().getIntExtra("itemindex", 0);
        geturl();
        refresh();
    }

    private void geturl() {
        switch (parentindex){
            case 0:
                switch (itemindex){
                    case 0:
                        url=String.format(Constant.PESTICIDE_SEARCH_COMPANY,typename,0,10000);

                        break;
                    case 1:
                        url=String.format(Constant.PESTICIDE_SEARCH_CARD,typename,0);

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                break;
            case 1:
                switch (itemindex){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;

                }
                break;
            case 2:
                switch (itemindex){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;

                }
                break;
            case 3:
                switch (itemindex){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;

                }
                break;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_pesticidelist;
    }

    @Override
    public void onRefresh() {
        getdata();
        super.onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
        super.onClick(v);
    }


    public  void refresh(){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0,500);

            }
        });

    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            getdata();
            super.handleMessage(msg);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(PesticideListActivity.this, PesticideDetailActivity.class).putExtra("data", pesticideList.get(position-1)));

        super.onItemClick(parent, view, position, id);
    }

    @Override
    public void onRequestResult(String string) {
        if(swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        if(!TextUtils.isEmpty(string)){
            String res= null;
            try {
                res = new JSONObject(string).getString("list");
                pesticideList= new Gson().fromJson(res,
                        new TypeToken<List<Pesticide>>() {
                        }.getType());
                if(pesticideList.size()>0) {
                    mlistview.setAdapter(new PesticideAdapter(this,pesticideList));
                }else {
                    ZToastUtils.showShort(this,R.string.no_content);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onRequestResult(string);
    }
    private void getdata(){
        System.out.println();
        if(CommonUtils.isNetWorkConnected(this)){
            zDataTask.get(url,null,null,this);
        }else{
            ZToastUtils.showShort(this,R.string.net_error);
        }
    }

    @Override
    public void onRequestError(Exception e) {
        swipeRefreshLayout.setRefreshing(false);
        super.onRequestError(e);
    }
}
