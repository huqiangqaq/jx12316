package com.nxt.ott.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.FbgqAdapter;
import com.nxt.ott.server.CommonService;
import com.nxt.ott.util.NetUtil;
import com.nxt.ott.util.Port;
import com.nxt.zyl.ui.widget.CustomListView;
import com.nxt.zyl.ui.widget.pulltorefresh.PullToRefreshBase;
import com.nxt.zyl.ui.widget.pulltorefresh.PullToRefreshScrollView;

import java.io.File;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2015/4/7 0007.
 */
public class SupplyListFragment extends Fragment {
    private TextView titletext, loadtext;
    private CustomListView listview;
    private File cachfile;
    private RelativeLayout re;
    FbgqAdapter adapter;
    int index = 1;
    String url;
    Context context;
    private ProgressBar bar;
    List<HashMap<String, Object>> list;
    private View footview;
    private LayoutInflater inflater;
    private View view;
    private PullToRefreshScrollView refreshScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		/*
		 * if (view != null) { ViewGroup parent = (ViewGroup) view.getParent();
		 * if (parent != null) { parent.removeView(view); } return view; }
		 */
        view = inflater.inflate(R.layout.supplylist, null);
        initview();
        return view;
    }

    private void initview() {
        context = getActivity();
        listview = (CustomListView) view.findViewById(R.id.customListView);
        cachfile = new File(Environment.getExternalStorageDirectory(),
                Constant.CASHFILENAME);
        isnet = NetUtil.isNetworkConnected(context);
        if (!cachfile.exists())
            cachfile.mkdirs();
        refreshScrollView = (PullToRefreshScrollView) view
                .findViewById(R.id.pull_refresh_scrollview);
        refreshScrollView.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);

        refreshScrollView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ScrollView> refreshView) {
                        index = index + 1;
                        new MyThread(1).start();
                        //System.out.println("load fbgqlist---------");

                    }
                });

    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    list = (List<HashMap<String, Object>>) msg.obj;
                    adapter = new FbgqAdapter(context, list, R.layout.fbgq_list, cachfile);
                    listview.setAdapter(adapter);
                    break;

                case 20:
                    List<HashMap<String, Object>> addlist = (List<HashMap<String, Object>>) msg.obj;
                    if (addlist.size() > 0) {
                        list.addAll(addlist);
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "数据已加载完毕", Toast.LENGTH_SHORT).show();
                    }
                    refreshScrollView.onRefreshComplete();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };
    private boolean isnet;

    private class MyThread extends Thread {
        private int types;

        public MyThread(int tp) {
            this.types = tp;
        }

        @Override
        public void run() {

            List<HashMap<String, Object>> list = CommonService
                    .getfbgqlist(Port.FBGQLIST + "&page=" + index
                            + "&pagesize=10");
            if (types == 0) {

                handler.sendMessage(handler.obtainMessage(10, list));

            } else if (types == 1) {
                System.out.println("run --------------------------load");
                handler.sendMessage(handler.obtainMessage(20, list));

            }
            super.run();
        }

    }

    @Override
    public void onResume() {
        if (isnet) {
            index=1;
            new MyThread(0).start();
        } else {
            // ToastUtil.toast(context, R.string.nonet);
            // util.stopbar();
        }
        super.onResume();
    }

}