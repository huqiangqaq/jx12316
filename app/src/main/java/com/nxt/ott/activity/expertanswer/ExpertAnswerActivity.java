package com.nxt.ott.activity.expertanswer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.google.gson.Gson;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.activity.expert.ExpertSh1Activity;
import com.nxt.ott.adapter.ExperterAnswerAdapter;
import com.nxt.ott.domain.ExpertAnswer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ExpertAnswerActivity extends Activity implements View.OnClickListener {
    private RelativeLayout iv_experter_back;
    private TextView tvTopBarText;
    private List<ExpertAnswer.RowsBean> list = new ArrayList<>();
    private ListView lv_expert_answer;
    private ExperterAnswerAdapter adapter;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private TextView tel,online;
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
    private SearchView searchView;
    private Map<String,String> map = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_answer);
        initTopbar(this,getString(R.string.expertanswer));
        initView();
        initData();
    }

    private void initData() {
        refresh();
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                OkHttpUtils.get().url(Constant.EXPERT_ANSWER + REQUEST_COUNT)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (response != null) {
                                    ExpertAnswer answer = new Gson().fromJson(response, ExpertAnswer.class);
                                    list = answer.getRows();
                                    adapter.notifyDataSetChanged();
                                    ptrClassicFrameLayout.refreshComplete();
                                    ptrClassicFrameLayout.setLoadMoreEnable(true);
                                }

                            }
                        });

            }
        });

        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                REQUEST_COUNT += 10;
                if (mCurrentCounter < TOTAL_COUNTER) {
                    OkHttpUtils.get().url(Constant.EXPERT_ANSWER + REQUEST_COUNT)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    if (response != null) {
                                        ExpertAnswer answer = new Gson().fromJson(response, ExpertAnswer.class);
                                        list.clear();
                                        list.addAll(answer.getRows());
                                        adapter.notifyDataSetChanged();
                                        ptrClassicFrameLayout.loadMoreComplete(true);
                                        mCurrentCounter = list.size();
                                    }
                                }
                            });
                } else {
                    ptrClassicFrameLayout.loadMoreComplete(true);
                    ptrClassicFrameLayout.setNoMoreData();
                }

            }
        });

        lv_expert_answer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                ExpertAnswer.RowsBean bean = list.get(position);
                bundle.putSerializable("answer",list.get(position));
                startActivity(new Intent(ExpertAnswerActivity.this,ExpertAnswerDetailActivity.class).putExtras(bundle));
            }
        });
    }

    private void initView() {
        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.test_list_view_frame);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        lv_expert_answer = (ListView) findViewById(R.id.lv_expert_answer);
        tel = (TextView) findViewById(R.id.tel);
        online = (TextView) findViewById(R.id.online);
        searchView = (SearchView) findViewById(R.id.search);
        tel.setOnClickListener(this);
        online.setOnClickListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                map.put("equestion",query);
                refresh();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void refresh() {
        OkHttpUtils.post().url(Constant.EXPERT_ANSWER+REQUEST_COUNT)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        if (response != null) {
                            ExpertAnswer answer = new Gson().fromJson(response, ExpertAnswer.class);
                            list = answer.getRows();
                            TOTAL_COUNTER = Integer.parseInt(answer.getTotal());
                            adapter = new ExperterAnswerAdapter(ExpertAnswerActivity.this, list);
                            lv_expert_answer.setAdapter(adapter);
                            ptrClassicFrameLayout.autoRefresh(true);
                        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tel:
                Uri uri = Uri.parse("tel:" + "12316");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                startActivity(intent);
                break;
            case R.id.online:
                startActivity(new Intent(this, ExpertSh1Activity.class));
                break;
        }
    }
}
