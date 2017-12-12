package com.nxt.ott.expertUpdate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ChooseAdapter;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.BaseExperter;
import com.nxt.ott.domain.ChooseExperter;
import com.nxt.ott.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class ChooseActivity extends BaseTitleActivity implements View.OnClickListener {

    @BindView(R.id.et_text)
    EditText et_text;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.btn_type)
    Button btn_type;
    @BindView(R.id.rv_experter)
    RecyclerView rv_experter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<BaseExperter> types;
    private String typeId;
    private int pageNum = 1;
    private ChooseAdapter adapter;
    private List<ChooseExperter.DataBean> experters;
    private int mTotalCount = 0;
    private HashMap<String, String> params = new HashMap<>();


    private enum ChooseType {
        SEARCH, TYPE, LOAD, UPDATE
    }

    @Override
    protected void initView() {
        initTopbar(this, "专家列表");
        adapter = new ChooseAdapter();
        rv_experter.setLayoutManager(new LinearLayoutManager(this));
        rv_experter.setHasFixedSize(false);
        rv_experter.setNestedScrollingEnabled(false);
        rv_experter.setAdapter(adapter);
        btn_search.setOnClickListener(this);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(ChooseActivity.this,AskActivity.class).putExtra("pid",experters.get(position).getId()).putExtra("isExperter",false));
            }
        });
        /**
         * EditText Search事件
         */
        et_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    refresh(ChooseType.SEARCH);
                }
                return false;
            }
        });
        initSwipeRefreshLayout();
        getType();
        refresh(ChooseType.UPDATE);
    }

    /**
     * 初始化swipeRefreshLayout
     */
    private void initSwipeRefreshLayout() {
        refreshLayout.autoRefresh(200);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh(ChooseType.UPDATE);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                load();
            }
        });
    }

    private void refresh(final ChooseType aDefault) {
        GetRequest request = OkGo.get(Constant.EXPERTERLIST);

        switch (aDefault) {
            case SEARCH:
                if ("".equals(et_text.getText().toString())) {
                    ToastUtils.showShort(ChooseActivity.this, "请先输入搜索内容");
                    return;
                }
                params.put("title", et_text.getText().toString());
                break;
            case TYPE:
                params.put("typeid", typeId);
                break;
            case UPDATE:
                pageNum = 1;
                break;
            default:
                break;
        }
        request.params("pageNum", pageNum);
        request.params(params);
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                String json = jsonString(s);
                JSONObject js;
                try {
                    js = new JSONObject(json);
                    mTotalCount = Integer.parseInt(js.getString("totalpage"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ChooseExperter chooseExperter = new Gson().fromJson(json, ChooseExperter.class);
                experters = chooseExperter.getData();
                adapter.setNewData(experters);
                refreshLayout.finishRefresh();
            }
        });
    }

    private void getType() {
        OkGo.get(Constant.EXPERTER_TYPE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        types = new Gson().fromJson(s, new TypeToken<List<BaseExperter>>() {
                        }.getType());
                        Log.i("huqiang", types.size() + "");
                        btn_type.setOnClickListener(ChooseActivity.this);
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_choose;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_type:
                showKindDialog();
                break;
            case R.id.btn_search:
                //搜索
                refresh(ChooseType.SEARCH);
                break;
            default:
                break;
        }
    }

    private void showKindDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChooseActivity.this);
        builder.setTitle("选择专家类型");
        String[] strings = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            strings[i] = types.get(i).getAtype();
        }
        builder.setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typeId = types.get(which).getId();

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                refresh(ChooseType.TYPE);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 上拉自动加载
     */
    private void load() {
        pageNum++;
        OkGo.post(Constant.EXPERTERLIST)
                .params("pageNum", pageNum)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        String json = jsonString(s);
                        ChooseExperter experter = new Gson().fromJson(json, ChooseExperter.class);
                        List<ChooseExperter.DataBean> answerList = experter.getData();
                        if (pageNum >= mTotalCount || answerList.isEmpty()) {
                            //数据全部加载完毕
                            refreshLayout.setLoadmoreFinished(true);
                        } else {
                            experters.addAll(answerList);
                            adapter.addData(answerList);
                            if (experters != null) {
                                refreshLayout.finishLoadmore();
                            }
                        }

                    }
                });

    }

    /**
     * 处理json中特殊字符
     */
    private static String jsonString(String s) {
        char[] temp = s.toCharArray();
        int n = temp.length;
        for (int i = 0; i < n; i++) {
            if (temp[i] == ':' && temp[i + 1] == '"') {
                for (int j = i + 2; j < n; j++) {
                    if (temp[j] == '"') {
                        if (temp[j + 1] != ',' && temp[j + 1] != '}') {
                            temp[j] = '”';
                        } else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
                            break;
                        }
                    } else if (temp[j] == '-') {
                        temp[j] = ' ';
                    } else if (true) {
                        // 要过虑其他字符，继续添加判断就可以
                    }
                }
            }
        }
        return new String(temp);
    }
}
