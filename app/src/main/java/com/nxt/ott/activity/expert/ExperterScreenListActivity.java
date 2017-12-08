package com.nxt.ott.activity.expert;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ExperterAdapter;
import com.nxt.ott.base.BaseLoadingTitleActivity;
import com.nxt.ott.domain.Experter;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Updated by iwon on 2016/6/19 0001.
 */
public class ExperterScreenListActivity extends BaseLoadingTitleActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView mlistview;
    private int lastItem, index = 0;
    private View footerview;
    private String url;
    private ZDataTask zDataTask;
    private SwipeRefreshLayout refreshlayout;
    private List<Experter> experterList = new ArrayList<>();

    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this, getString(R.string.experter_ask));

        zDataTask = application.getZDataTask();
        mlistview = (ListView) findViewById(R.id.listview_expert);
        footerview = LayoutInflater.from(this).inflate(R.layout.layout_foot, null);
        refreshlayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        refreshlayout.setOnRefreshListener(this);

        Intent intent = this.getIntent();
        if (intent!=null){
            url= intent.getStringExtra("url");
            Log.e("Expert-62", "url-------------->" + url);
            if (CommonUtils.isNetWorkConnected(this)) {
                zDataTask.get(url, null, null, this);
                Log.e("Expert-66", "url-------------->" + url);
            } else {
                ZToastUtils.showShort(this, R.string.net_error);
            }
        }

        refresh();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_experter_search_result;
    }

    @Override
    public void onRequestResult(String string) {
        System.out.println("string---------->" + string);
        if (refreshlayout.isRefreshing()) refreshlayout.setRefreshing(false);
        if (!TextUtils.isEmpty(string)) {
            try {
                JSONObject js = new JSONObject(string);
                experterList = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                }.getType());
                mlistview.setAdapter(new ExperterAdapter(this, experterList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onRequestResult(string);
    }

    public void refresh() {
        refreshlayout.post(new Runnable() {
            @Override
            public void run() {
                refreshlayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0, 500);

            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            index = 0;
            if (CommonUtils.isNetWorkConnected(ExperterScreenListActivity.this)) {
                zDataTask.get(url, null, null, ExperterScreenListActivity.this);
                Log.e("Expert-65", "url-------------->" + url);
            } else {
                ZToastUtils.showShort(ExperterScreenListActivity.this, R.string.net_error);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onRefresh() {
        if (CommonUtils.isNetWorkConnected(this)) {
            zDataTask.get(url, null, null, this);
            Log.e("Expert-65", "url-------------->" + url);
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }
}
