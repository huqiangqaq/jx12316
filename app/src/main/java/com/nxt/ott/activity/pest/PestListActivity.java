package com.nxt.ott.activity.pest;

import android.text.TextUtils;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ExpandableListAdapter;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.Pest;
import com.nxt.ott.domain.Pests;
import com.nxt.ott.util.LogUtils;
import com.nxt.ott.util.NetUtil;
import com.nxt.ott.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xpeng on 2016/9/27.
 */

public class PestListActivity extends BaseTitleActivity {
    private static final String TAG = "PestListActivity";

    private ExpandableListView mExpandableListView;
    private List<Pest.RowsBean> mPestList=new ArrayList<>();
    private List<String> pestTypeList=new ArrayList<>();
    private List<List<Pests.RowsBean.ArrBean>> pestList=new ArrayList<>();

    private ExpandableListAdapter expandableListAdapter;
    private Pests mPest;

    @Override
    protected void initView() {
        initTopbar(this,"病虫害图谱");
        if(NetUtil.isNetworkConnected(this)) {
            zDataTask.get(Constant.BCH_PEST_URL, null, null, this);
            showLoadingDialog(R.string.is_loading);
        }else {
            ToastUtils.showShort(this,"网络未连接");
        }
        mExpandableListView=(ExpandableListView)findViewById(R.id.pest_list);
    }

    private void setExpandableAdapter(List<String> pestType,List<List<Pests.RowsBean.ArrBean>> pest){
        expandableListAdapter=new ExpandableListAdapter(this,pestType,pest);
        mExpandableListView.setAdapter(expandableListAdapter);
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.expandGroup(0);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_pest_list;
    }

    @Override
    public void onRequestResult(String string) {
        LogUtils.e(TAG,string);
        dismissLoadingDialog();
        mPest=new Gson().fromJson(string,Pests.class);

        parsePests(mPest);
    }

    private void parsePests(Pests pests){
        for(int i=0;i<pests.getRows().size();i++){
            Pests.RowsBean rowsBean=pests.getRows().get(i);
            pestTypeList.add(rowsBean.getType());
            pestList.add(rowsBean.getArr());
        }
        setExpandableAdapter(pestTypeList,pestList);
    }
}
