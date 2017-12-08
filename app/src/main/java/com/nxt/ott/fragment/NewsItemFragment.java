package com.nxt.ott.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.activity.WebActivity;
import com.nxt.ott.adapter.NewsAdapter;
import com.nxt.ott.domain.News;
import com.nxt.ott.Constant;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.data.volley.toolbox.HttpCallback;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Updated by iwon on 2016/6/22 10:45.
 */
public class NewsItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, HttpCallback {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mlistview;
    private View footerview;
    private int page = 1, pagesize = 10;
    private int lastItem, currentposition;
    private static String url, newsurl = Constant.POLICY_NEWS_PROVINCE_URL;
    private ZDataTask zDataTask;
    private List<News> newslist = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private List<String> urllist = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getInt("newstype") == 0) {//农资讯

            urllist.add(Constant.AGRICULTURE_NEWS_URL);//农业新闻
            urllist.add(Constant.POLICY_NEWS_PROVINCE_URL);//惠农政策
            urllist.add(Constant.MAKEMONEY_NEWS_POLICY_URL);//致富政策
            urllist.add(Constant.MAKEMONEY_NEWS_PROJECT_URL);//致富专题
            urllist.add(Constant.AGRICULTURETIME_AGRICULTURETHING);//农时农事
            urllist.add(Constant.EXTENSION_ENCYCLOPEDIA_URL);//农技百科
            urllist.add(Constant.POLICY_NEWS_COUNTRY_URL);//国内信息
            urllist.add(Constant.MAKEMONEY_NEWS_BUSINESS_URL);//动态
            urllist.add(Constant.POLICY_NEWS_OTHER_URL);//其他

        } else if (getArguments().getInt("newstype") == 1) {//二中心

            urllist.add(Constant.WISDOM_CENTER_ONE_URL);
            urllist.add(Constant.WISDOM_CENTER_TWO_URL);

        }else if (getArguments().getInt("newstype") == 2) {//三平台

            urllist.add(Constant.WISDOM_PLATFORM_ONE_URL);
            urllist.add(Constant.WISDOM_PLATFORM_TWO_URL);
            urllist.add(Constant.WISDOM_PLATFORM_THREE_URL);
        }
        currentposition = getArguments().getInt("url");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_common_listview, null);
        initView(view);
        return view;
    }

    protected void initView(View view) {
        zDataTask = MyApplication.getInstance().getZDataTask();
        mlistview = (ListView) view.findViewById(R.id.listview_common);
        mlistview.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty_heade, null));
        mlistview.setOnItemClickListener(this);
        footerview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_foot, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_light
        );
        swipeRefreshLayout.setOnRefreshListener(this);
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

    public static NewsItemFragment newInstance(int type, int newstype) {
        Bundle args = new Bundle();
        NewsItemFragment fragment = new NewsItemFragment();
        args.putInt("url", type);
        args.putInt("newstype", newstype);
        Log.e("newsfragment-112", "newstype-------------->" + newstype);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = String.format(Constant.NEWS_DETAIL_URL, newslist.get(position - 1).getId());
        startActivity(new Intent(getActivity(), WebActivity.class)
                .putExtra("title", getString(R.string.news_detail))
                .putExtra("url", url));
    }


    public void refresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0, 500);

            }
        });

    }

    public void load() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(1, 1500);

            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                page = 1;
                if (newslist != null) newslist.clear();
            } else {
                page++;

            }
            getnews();
            super.handleMessage(msg);
        }
    };


    private void getnews() {
        if (CommonUtils.isNetWorkConnected(getActivity())) {
            url = String.format(urllist.get(currentposition), page, pagesize);
            zDataTask.get(url, null, null, this);
        } else {
            ZToastUtils.showShort(getActivity(),"网络连接错误");
        }
        System.out.println("url-------->" + url);
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestFinish() {

    }

    @Override
    public void onRequestResult(String string) {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        mlistview.removeFooterView(footerview);

        if (getArguments().getInt("newstype") == 0 || getArguments().getInt("newstype") == 1) {
            if (!TextUtils.isEmpty(string)) {
                try {
                    JSONObject js = new JSONObject(string);
                    if (page == 1) {
                        newslist = new Gson().fromJson(js.getString("rows"), new TypeToken<List<News>>() {
                        }.getType());
                        newsAdapter = new NewsAdapter(getActivity(), newslist);
                        mlistview.setAdapter(newsAdapter);
                    } else {
                        List<News> addlist = new Gson().fromJson(js.getString("rows"), new TypeToken<List<News>>() {
                        }.getType());
                        if (addlist.size() > 0) {
                            newslist.addAll(addlist);
                            newsAdapter.notifyDataSetChanged();
                        } else {
                            ZToastUtils.showShort(getActivity(), R.string.data_is_over);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ZToastUtils.showShort(getActivity(), R.string.net_error);
            }
        } else if (getArguments().getInt("newstype") == 2) {
            if (!TextUtils.isEmpty(string)) {
                try {
                    JSONObject js = new JSONObject(string);
                    if (page == 1) {
                        newslist = new Gson().fromJson(js.getString("newslist"), new TypeToken<List<News>>() {
                        }.getType());
                        newsAdapter = new NewsAdapter(getActivity(), newslist);
                        mlistview.setAdapter(newsAdapter);
                    } else {
                        List<News> addlist = new Gson().fromJson(js.getString("newslist"), new TypeToken<List<News>>() {
                        }.getType());
                        if (addlist.size() > 0) {
                            newslist.addAll(addlist);
                            newsAdapter.notifyDataSetChanged();
                        } else {
                            ZToastUtils.showShort(getActivity(), R.string.data_is_over);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ZToastUtils.showShort(getActivity(), R.string.net_error);
            }
        }


    }

    @Override
    public void onRequestError(Exception e) {

    }

    @Override
    public void onRequestCancelled() {

    }

    @Override
    public void onRequestLoading(long count, long current) {

    }

    @Override
    public void onRefresh() {
        page = 1;
        getnews();
    }

    @Override
    public void onStart() {

        super.onStart();
    }

}
