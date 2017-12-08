package com.nxt.ott.activity.expertadvise;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ExperterAdviseAdapter;
import com.nxt.ott.domain.ExpertAdvise;
import com.nxt.zyl.util.ZPreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ExperterAdviseActivity extends Activity {
    private RelativeLayout iv_experter_back;
    private TextView tvTopBarText;
    private List<ExpertAdvise.DataBean> list = new ArrayList<>();
    private ListView lv_expert_advise;
    private ExperterAdviseAdapter adviseAdapter;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    /**
     * 服务器端一共多少条数据
     */
    private Integer TOTAL_COUNTER = 20;

    /**
     * 每一页展示多少条数据
     */
    private int REQUEST_COUNT = 10;

    /**
     * 已经获取到多少条数据了
     */
    private int mCurrentCounter = 0;
    private int pageNum = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experter_advise);
        initTopbar(this, getString(R.string.expertadvise));
        initView();
        initData();
    }

    private JSONObject getjson() {
        HashMap<String,String> params = new HashMap<>();
        params.put("type", "1");
        params.put("pageNum", pageNum+"");
        params.put("count", "10");
        params.put("sort", "desc");
        params.put("province", ZPreferenceUtils.getPrefString(Constant.PROVINCE,""));
        params.put("city",ZPreferenceUtils.getPrefString(Constant.CITY,""));
        params.put("county",ZPreferenceUtils.getPrefString(Constant.DISTRICT,""));
        return new JSONObject(params);
    }

    private void initView() {
        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.test_list_view_frame);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        lv_expert_advise = (ListView) findViewById(R.id.lv_expert_advise);
    }

    private void refresh() {
        OkGo.post(Constant.EXPERT_ADVISE)
                .upJson(getjson())
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ExpertAdvise expertAdvise = new Gson().fromJson(s,ExpertAdvise.class);
                        list = expertAdvise.getData();
                        adviseAdapter = new ExperterAdviseAdapter(ExperterAdviseActivity.this, list);
                        lv_expert_advise.setAdapter(adviseAdapter);
                        ptrClassicFrameLayout.autoRefresh(true);
                    }
                });
//        OkHttpUtils.post().url(Constant.EXPERT_ADVISE)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        if (response != null) {
//                            ExpertAdvise expertAdvise = new Gson().fromJson(response, ExpertAdvise.class);
//                            list = expertAdvise.getRows();
//                            TOTAL_COUNTER = Integer.parseInt(expertAdvise.getTotal());
//
//                        }
//
//                    }
//                });

    }

    private void initData() {
        refresh();
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                OkGo.post(Constant.EXPERT_ADVISE)
                        .upJson(getjson())
                        .execute(new com.lzy.okgo.callback.StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                if (s!=null){
                                    ExpertAdvise expertAdvise = new Gson().fromJson(s, ExpertAdvise.class);
                                    list = expertAdvise.getData();
                                    adviseAdapter.notifyDataSetChanged();
                                    ptrClassicFrameLayout.refreshComplete();
                                    ptrClassicFrameLayout.setLoadMoreEnable(true);
                                }
                            }
                        });
//                OkHttpUtils.get().url(Constant.EXPERT_ADVISE + REQUEST_COUNT)
//                        .build()
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onError(Call call, Exception e, int id) {
//
//                            }
//
//                            @Override
//                            public void onResponse(String response, int id) {
//                                if (response != null) {
//
//                                }
//
//                            }
//                        });

            }
        });


        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                pageNum++;
                OkGo.post(Constant.EXPERT_ADVISE)
                        .upJson(getjson())
                        .execute(new com.lzy.okgo.callback.StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                ExpertAdvise expertAdvise = new Gson().fromJson(s,ExpertAdvise.class);
                                if (expertAdvise.getData()!=null){
                                    list.addAll(expertAdvise.getData());
                                    adviseAdapter.setList(list);
                                    adviseAdapter.notifyDataSetChanged();
                                    ptrClassicFrameLayout.loadMoreComplete(true);
                                }else {
                                    ptrClassicFrameLayout.loadMoreComplete(true);
                                    ptrClassicFrameLayout.setNoMoreData();
                                }
                            }
                        });
//                if (mCurrentCounter < TOTAL_COUNTER) {
//                    OkHttpUtils.get().url(Constant.EXPERT_ADVISE + REQUEST_COUNT)
//                            .build()
//                            .execute(new StringCallback() {
//                                @Override
//                                public void onError(Call call, Exception e, int id) {
//
//                                }
//
//                                @Override
//                                public void onResponse(String response, int id) {
//                                    if (response != null) {
//                                        ExpertAdvise expertAdvise = new Gson().fromJson(response, ExpertAdvise.class);
//                                        list.clear();
//                                        list.addAll(expertAdvise.getRows());
//                                        adviseAdapter.notifyDataSetChanged();
//                                        ptrClassicFrameLayout.loadMoreComplete(true);
//                                        mCurrentCounter = list.size();
//                                    }
//                                }
//                            });
//                } else {
//
//                }

            }
        });
    }

    /**
     * 初始化Topbar
     *
     * @param activity {@link android.app.Activity} 使用Topbar的Activity
     */
    protected void initTopbar(Activity activity, String titlename) {
        iv_experter_back = (RelativeLayout) activity.findViewById(R.id.layout_left);
        tvTopBarText = (TextView) activity.findViewById(R.id.tv_title);
        tvTopBarText.setText(titlename);
        iv_experter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
