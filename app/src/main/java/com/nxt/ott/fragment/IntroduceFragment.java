package com.nxt.ott.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.nxt.ott.R;
import com.nxt.ott.Constant;
import com.nxt.zyl.data.ZDataTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Updated by iwon on 2016/6/22 10:45.
 */
public class IntroduceFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    public WebView mWebView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View footerview;
    private int currentposition;
    private String url;
    private ZDataTask zDataTask;
    private List<String> urllist = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urllist.clear();
        if (getArguments().getInt("newstype") == 1) {//二中心

            urllist.add(Constant.WISDOM_CENTER_ONE_URL);
            urllist.add(Constant.WISDOM_CENTER_TWO_URL);

        } else if (getArguments().getInt("newstype") == 2) {//三平台

            urllist.add(Constant.WISDOM_PLATFORM_ONE_URL);
            urllist.add(Constant.WISDOM_PLATFORM_TWO_URL);
            urllist.add(Constant.WISDOM_PLATFORM_THREE_URL);
        }
        url = urllist.get(getArguments().getInt("position"));
        Log.e("65", "++++++++++++++" + currentposition);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_common_webview, null);
        initView(view);
        return view;
    }

    protected void initView(View view) {
        footerview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_foot, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light
        );
        swipeRefreshLayout.setOnRefreshListener(this);
        mWebView = (WebView) view.findViewById(R.id.webview);
        /**
         * 初始化webview设置
         */
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebView.setInitialScale(25);//
        mWebView.requestFocus();

        mWebView.loadUrl(url);
    }

    public static IntroduceFragment newInstance(int position, int type ) {//接收activity传过来的url和标识
        Bundle args = new Bundle();
        IntroduceFragment fragment = new IntroduceFragment();
        args.putInt("position",position);
        args.putInt("newstype", type);
      //  Log.e("newsfragment-112", "newstype-------------->" + newstype);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRefresh() {
        mWebView.loadUrl(url);
    }
}