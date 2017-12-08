package com.nxt.ott.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
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
import com.nxt.ott.activity.agricultureclass.VideoDetailActivity;
import com.nxt.ott.adapter.LessonAdapter;
import com.nxt.ott.domain.LessonDetail;
import com.nxt.ott.Constant;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.data.volley.toolbox.HttpCallback;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Updated by iwon on 2016/6/19 20:12.
 */
public class LessonItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener,HttpCallback {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mlistview;
    private View footerview;
    private int page=1;
    private int lastItem,currentposition;
    private static String url;
    private ZDataTask zDataTask;
    private List<LessonDetail> lessondetaillist=new ArrayList<>();
    private LessonAdapter lessonAdapter;
    private List<String> urllist=new ArrayList<>();
    private String urlid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlid=getArguments().getString("id");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_common_listview,null);
        initView(view);
        return view;
    }

    protected void initView(View view) {
        zDataTask= MyApplication.getInstance().getZDataTask();
        mlistview= (ListView) view.findViewById(R.id.listview_common);
        //mlistview.setDividerHeight(8);
        mlistview.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty_heade,null));
        mlistview.setOnItemClickListener(this);
        footerview= LayoutInflater.from(getActivity()).inflate(R.layout.layout_foot,null);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_blue_light, android.R.color.holo_blue_light, android.R.color.holo_blue_light);
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
    public static LessonItemFragment newInstance(String id) {
        Bundle args = new Bundle();
        LessonItemFragment fragment = new LessonItemFragment();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // String url=String.format(Constant.NEWS_DETAIL_URL, lessondetaillist.get(position - 1).getId());
        startActivity(new Intent(getActivity(), VideoDetailActivity.class)
                .putExtra(Constant.LESSON_DETAIL,lessondetaillist.get(position-1)));
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
    public  void load(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(1, 1500);

            }
        });

    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0) {
                page = 1;
                if(lessondetaillist!=null) lessondetaillist.clear();
            }else{
                page++;
            }
            getnews();
            super.handleMessage(msg);
        }
    };


    private void getnews(){
        if(CommonUtils.isNetWorkConnected(getActivity())){
            url=String.format(Constant.LESSON_VIDEO_LIST_URL,urlid,page);
            zDataTask.get(url,null,null,this);
        }else{
            ZToastUtils.showShort(MyApplication.getInstance(), R.string.net_error);
        }
        System.out.println("url-------->"+url);
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestFinish() {

    }

    @Override
    public void onRequestResult(String string) {
        System.out.println("string-------------->"+string);
        if(swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        mlistview.removeFooterView(footerview);

        if(!TextUtils.isEmpty(string)){

                if(page==1) {
                    lessondetaillist = new Gson().fromJson(string, new TypeToken<List<LessonDetail>>() {
                    }.getType());
                    lessonAdapter=new LessonAdapter(getActivity(),lessondetaillist);
                    mlistview.setAdapter(lessonAdapter);
                }else{
                    List<LessonDetail> addlist=new Gson().fromJson(string,new TypeToken<List<LessonDetail>>() {
                    }.getType());
                    if(addlist.size()>0){
                        lessondetaillist.addAll(addlist);
                        lessonAdapter.notifyDataSetChanged();
                    }else{
                        ZToastUtils.showShort(getActivity(), R.string.data_is_over);
                    }
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
        page=1;
        getnews();
    }

    @Override
    public void onStart(){

        super.onStart();
    }
}
