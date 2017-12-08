package com.nxt.ott.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nxt.ott.R;
import com.nxt.ott.server.CommonService;
import com.nxt.ott.util.Port;
import com.nxt.zyl.ui.widget.CustomListView;
import com.nxt.zyl.ui.widget.pulltorefresh.PullToRefreshBase;
import com.nxt.zyl.ui.widget.pulltorefresh.PullToRefreshScrollView;
import com.nxt.zyl.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/4/7 0007.
 */
public class PriceFragment extends Fragment {
    private CustomListView listView;
    private View view;
    private SimpleAdapter adapter;
    private String pid="958";
    private int page=1;
    private String date="20150408";
    private PullToRefreshScrollView refreshScrollView;
    private List<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view=inflater.inflate(R.layout.price,null);
        initview();
        return view;
    }

    private void initview() {
        listView= (CustomListView) view.findViewById(R.id.customListView);
        ((TextView)view.findViewById(R.id.tv_date)).setText(TimeUtil.getdate1());
        refreshScrollView=(PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
        refreshScrollView.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);

        refreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(
                    PullToRefreshBase<ScrollView> refreshView) {
                page++;
                new MyThread(1).start();
            }
        });
    }

    @Override
    public void onStart() {
        new MyThread(0).start();//获取时间
        super.onStart();
    }
    private class MyThread extends Thread{
        int type;
        public MyThread(int ttype){
            this.type=ttype;
        }
        @Override
        public void run() {

            if(type==0){
                List<HashMap<String,String>> alist= CommonService.getPrice2(Port.PRICE_UPDATE+"?Action=grid&page="+page+"&pagesize=20");
                handler.sendMessage(handler.obtainMessage(10, alist));
            }else if(type==1){
                List<HashMap<String,String>> alist=CommonService.getPrice2(Port.PRICE_UPDATE+"?Action=grid&page="+page+"&pagesize=20");

                handler.sendMessage(handler.obtainMessage(20, alist));
            }else if(type==2){
                handler.sendMessage(handler.obtainMessage(30, CommonService.getpricedate(Port.PRICE+"?aid="+pid)));

            }
            super.run();
        }

    }


    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    list=(List<HashMap<String, String>>) msg.obj;
                    adapter=new SimpleAdapter(getActivity(), list, R.layout.price_list,new String[]{"Agrivarity","farmerPrice","coUnit"},new int[]{R.id.tv_price_stype,R.id.tv_price,R.id.tv_price_unit});
                    listView.setAdapter(adapter);
                    break;
                case 20:
                    List<HashMap<String,String>> addlist=(List<HashMap<String,String>>) msg.obj;

                    if(addlist.size()>0){
                        list.addAll(addlist);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getActivity(),"数据已加载完毕", Toast.LENGTH_SHORT).show();
                    }
                    if(refreshScrollView.isRefreshing()){
                        refreshScrollView.onRefreshComplete();
                    }
                    break;
                case 30:
                    date=((List<String>)msg.obj).get(0);
                    new MyThread(0).start();
                default:
                    break;
            }

            super.handleMessage(msg);
        }

    };
}
