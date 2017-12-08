package com.nxt.ott.activity.expert;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ExperterAdapter;
import com.nxt.ott.base.BaseLoadingTitleActivity;
import com.nxt.ott.domain.Experter;
import com.nxt.ott.Constant;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Updated by iwon on 2016/6/19 0001.
 */
public class ExperterListActivity extends BaseLoadingTitleActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView mlistview;
    private int lastItem, index = 0;
    private View footerview;
    private String experter;
    private ZDataTask zDataTask;
    private SwipeRefreshLayout refreshlayout;
    private List<Experter> experterList = new ArrayList<>();
    private TextView tvChooes;
    private int page = 1, pagesize = 30;
    private ExperterAdapter experteradapter;
    private LinearLayout calllayout;

    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this, getString(R.string.experter_ask));
        zDataTask = application.getZDataTask();
        try {
            experter = URLEncoder.encode(getString(R.string.experter), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        calllayout = (LinearLayout) findViewById(R.id.layout_call_experter);
        mlistview = (ListView) findViewById(R.id.listview_expert);
        footerview = LayoutInflater.from(this).inflate(R.layout.layout_foot, null);
        refreshlayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        tvChooes = (TextView) findViewById(R.id.tv_right);
        tvChooes.setOnClickListener(this);
        refreshlayout.setOnRefreshListener(this);
        mlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            //AbsListView view 这个view对象就是listview
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        mlistview.addFooterView(footerview);
                        load();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
        refresh();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                startActivity(new Intent(this, ExpertSh1Activity.class));
                break;
        }
        super.onClick(v);
    }


    private void getdata() {
        if (CommonUtils.isNetWorkConnected(this)) {
            zDataTask.get(String.format(Constant.EXPERTER_LIST_URL, page, pagesize, experter), null, null, this);
            Log.e("Expert-65", "url-------------->" + String.format(Constant.EXPERTER_LIST_URL, page, pagesize, experter));
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_experter;
    }

    @Override
    public void onRequestResult(String string) {
        System.out.println("string---------->" + string);
        if (refreshlayout.isRefreshing()) refreshlayout.setRefreshing(false);
        if (!TextUtils.isEmpty(string)) {
            try {
                JSONObject js = new JSONObject(string);
                if (page == 1) {

                    experterList = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                    }.getType());
                    experteradapter = new ExperterAdapter(ExperterListActivity.this, experterList);
                    mlistview.setAdapter(experteradapter);
                } else {
                    List<Experter> addlist = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                    }.getType());
                    if (addlist.size() > 0) {
                        experterList.addAll(addlist);
                        experteradapter.notifyDataSetChanged();
                    } else {
                        ZToastUtils.showShort(ExperterListActivity.this, R.string.expert_is_over);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ZToastUtils.showShort(ExperterListActivity.this, R.string.net_error);
        }
        super.onRequestResult(string);
    }

    public void load() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(1, 1500);
            }
        });

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
            if (msg.what == 0) {
                page = 1;
                if (experterList != null) experterList.clear();
            } else {
                page++;
            }
            getdata();
            super.handleMessage(msg);
        }
    };

    @Override
    public void onRefresh() {
        page = 1;
        getdata();
    }

    @Override
    public void onStart() {

        super.onStart();
    }
}