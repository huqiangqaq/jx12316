package com.nxt.ott.activity.doctor;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.DiseaseResultAdapter;
import com.nxt.ott.base.BaseDiseaseActivity;
import com.nxt.ott.domain.DiseaseResult;
import com.nxt.ott.domain.DiseaseType;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZPreferenceUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by zhangyonglu on 2016/2/1 0001.
 */
public class DiseaseResultActivity extends BaseDiseaseActivity implements AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView mlistview;
    private String selections,content,resulturl;
    private List<DiseaseResult> diseaseResultList;
    private DiseaseType diseaseType;
    @Override
    protected void initView() {
        initTopbar(this,getString(R.string.result_search));
        rbtstepone.setChecked(true);
        rbtsteptextone.setChecked(true);
        rbtsteptwo.setChecked(true);
        rbtsteptexttwo.setChecked(true);
        rbtstepthree.setChecked(true);
        rbtsteptextthree.setChecked(true);
        rbtstepfour.setChecked(true);
        rbtsteptextfour.setChecked(true);
        diseaseType= (DiseaseType) getIntent().getSerializableExtra("data");
        mlistview= (ListView) findViewById(R.id.listview_common);
        mlistview.addHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_empty_heade, null));
        mlistview.setOnItemClickListener(this);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        try {
            selections= URLEncoder.encode(ZPreferenceUtils.getPrefString(Constant.DISEASE_SELECTIONS,""),"utf-8");
            content= ZPreferenceUtils.getPrefString(Constant.DISEASE_CONTENT,"");
            resulturl=String.format(Constant.DISEATE_CONTENT_URL,diseaseType.getKnowledgeDataTableName(),
                    diseaseType.getDiseaseDataTableName(),
                    diseaseType.getTestCaseDataTableName(),
                    diseaseType.getTestWeightDataTableName(),
                    diseaseType.getSymptomDataTableName(), selections,content);
            System.out.println("rsulturl------------>"+resulturl);
            Log.d("rsulturl",resulturl);
            autorefresh();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected int getLayout() {
        return R.layout.activity_disease_result;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {
           getresult();
    }
    private void autorefresh(){
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

            getresult();
            super.handleMessage(msg);
        }
    };

    private void getresult() {
        if(CommonUtils.isNetWorkConnected(this)){
            zDataTask.get(resulturl,null,null,this);
        }else{
            ZToastUtils.showShort(this,R.string.net_error);
        }
    }

    @Override
    public void onRequestResult(String string) {
   if(swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        try {
            String result= URLDecoder.decode(string,"gbk");
            Log.d("rsulturl",result);
            if(!TextUtils.isEmpty(result)&&result.contains("success")){
                JSONObject json=new JSONObject(result);
                diseaseResultList=new Gson().fromJson(json.getString("infos"),
                        new TypeToken<List<DiseaseResult>>(){}.getType());
                mlistview.setAdapter(new DiseaseResultAdapter(this,diseaseResultList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onRequestResult(string);
    }
}
