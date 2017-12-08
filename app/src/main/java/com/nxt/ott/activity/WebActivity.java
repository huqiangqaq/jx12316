package com.nxt.ott.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.nxt.ott.Constant;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.util.StrictModeWrapper;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

/**
 * Update by iwon on 2016/6/19 20:25.
 */

public class WebActivity extends BaseTitleActivity {
    public WebView mWebView;
    private ImageView mImgback;
    private Intent intent;
    boolean isfirsarload = true;
    private ImageView addimg;
    private static final int REQUEST_CODE = 1;
    private String[] rechargeList = new String[3], rechargeurllist = new String[3];
    String title, url;
    private SwipeRefreshLayout refreshLayout;
    private Spinner chargesp;
    private ArrayAdapter<String> rechargeAdapter;
    private int tag;
    private RelativeLayout rlTitle;


    @Override
    protected void initView() {
        application.addActivity(this);

        intent = getIntent();
        MyApplication.getInstance().addActivity(this);
        StrictModeWrapper.init(getApplicationContext());
        title = intent.getStringExtra("title");
        tag = intent.getIntExtra("tag", 0);
        rlTitle = (RelativeLayout) findViewById(R.id.layout_title);
        if (tag == 1) {
            rlTitle.setVisibility(View.GONE);
        } else {
            rlTitle.setVisibility(View.VISIBLE);
        }
        initTopbar(this, title);
//        if(title.equals(getString(R.string.recharge))){
//            tvTopBarText.setVisibility(View.GONE);
//            chargesp= (Spinner) findViewById(R.id.sp_charge_type);
//            chargesp.setVisibility(View.VISIBLE);
//            rechargeList=getResources().getStringArray(R.array.recharge_type);
//            rechargeurllist=getResources().getStringArray(R.array.recharge_url_list);
//            rechargeAdapter=new ArrayAdapter<String>(this,R.layout.layout_sp_item,rechargeList);
//            rechargeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            chargesp.setAdapter(rechargeAdapter);
//            chargesp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    url=rechargeurllist[position];
//                    refresh();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    LogUtils.i("ssss","nothing----------->");
//                }
//            });
//        }
        url = intent.getStringExtra("url");
        boolean isSoil = intent.getBooleanExtra("isSoil",false);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                viewInfo();
            }
        });
        mWebView = (WebView) findViewById(R.id.webview);
        /**
         * 初始化webview设置
         */
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        if (!isSoil){
            mWebSettings.setBuiltInZoomControls(true);
            mWebSettings.setUseWideViewPort(true);
            mWebSettings.setLoadWithOverviewMode(true);
            mWebSettings.setBuiltInZoomControls(true);
            mWebSettings.setSupportZoom(true);
            mWebSettings.setDisplayZoomControls(true);
            mWebView.setInitialScale(100);//
        }
        mWebView.requestFocus();
        if (url.equals(Constant.JIANGXI_NET_URL)) {
            viewInfo();
        } else {
            refresh();

        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_webview;
    }

    public void viewInfo() {
        if (CommonUtils.isNetWorkConnected(this)) {
            setWebViewConfig();
            return;
        }
        ZToastUtils.showShort(this, R.string.net_error);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebViewConfig() {

        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (isfirsarload) {
                    isfirsarload = false;
                    return false;
                } else {
                    startActivity(new Intent(WebActivity.this, WebActivity.class).putExtra("title", getString(R.string.web_title)).putExtra("url", url));
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); //
            return true;
        } else {
            finish();
        }
        return false;
    }

    public void refresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0, 500);

            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewInfo();
            super.handleMessage(msg);
        }
    };
}