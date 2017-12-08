package com.nxt.ott.expertUpdate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.behavior.AnimatorUtil;
import com.nxt.ott.behavior.ScaleDownShowBehavior;
import com.nxt.ott.domain.Answer;
import com.nxt.ott.domain.BaseType;
import com.nxt.ott.statusbar.StatusBarUtil;
import com.nxt.ott.util.JsonUtils;
import com.nxt.ott.util.ListDataSave;
import com.nxt.ott.util.OkhttpHelper;
import com.nxt.ott.util.ToastUtils;
import com.nxt.zyl.ui.widget.AVLoadingIndicatorView;
import com.nxt.zyl.util.ZPreferenceUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import okhttp3.Call;
import okhttp3.Response;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.position.OnBottomPosCallback;
import zhy.com.highlight.position.OnTopAndLeftCallback;
import zhy.com.highlight.position.OnTopPosCallback;
import zhy.com.highlight.shape.CircleLightShape;

import static com.nxt.ott.expertUpdate.AnswerList_Activity.Type.ALL;
import static com.nxt.ott.expertUpdate.AnswerList_Activity.Type.ANSWER;
import static com.nxt.ott.expertUpdate.AnswerList_Activity.Type.DEFAULT;
import static com.nxt.ott.expertUpdate.AnswerList_Activity.Type.Kind;
import static com.nxt.ott.expertUpdate.AnswerList_Activity.Type.QUESTION;
import static com.nxt.ott.expertUpdate.AnswerList_Activity.Type.SEARCH;


public class AnswerList_Activity extends AppCompatActivity implements View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    private FloatingActionButton FAB;

    private RecyclerView ry_answers;

    private BottomSheetBehavior mBottomSheetBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private List<Answer.DataBean> answers = new ArrayList<>();
    private ListRecyclerAdapter AnswerListAdapter;
    private View headerLayout;
    private LinearLayoutManager linearLayoutManager;
    private EditText et_search;
    private ImageButton ib_search, ib_exper_list;
    private SwipeRefreshLayout refreshLayout;
    private AlertDialog.Builder builder;
    protected AVLoadingIndicatorView loadingIndicatorView;
    private int mTotalCount = 0;
    private int mPageSize = 10;
    private int mCurrentIndex = 1;
    private int mCurrentCounter = 0;
    private TextView tv_all;
    private Map<String, String> map = new HashMap<>();
    private HighLight mHightLight;
    private String kind;
    private RelativeLayout rl_quick;
    private BGABadgeRadioButton ib_gz, ib_answer, ib_question;


    public enum Type {
        ALL,
        ANSWER,
        QUESTION,
        DEFAULT,
        SEARCH,
        Kind
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list_);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.experter_main), 0);
        getExperterType();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.color.experter_main);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("问题列表");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        ry_answers = (RecyclerView) findViewById(R.id.recyclerView);
        ry_answers.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        ry_answers.setLayoutManager(linearLayoutManager);
        AnswerListAdapter = new ListRecyclerAdapter(answers);
        AnswerListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(AnswerList_Activity.this, DetailActivity.class);
                intent.putExtra("id", answers.get(position).getId());
                startActivity(intent);
            }
        });

        headerLayout = LayoutInflater.from(this).inflate(R.layout.header_layout_experter, (ViewGroup) ry_answers.getParent(), false);
        initHeaderView();
        FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(0);
                hideFAB();
            }
        });

        ScaleDownShowBehavior scaleDownShowFab = ScaleDownShowBehavior.from(FAB);
        scaleDownShowFab.setOnStateChangedListener(onStateChangedListener);

        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_layout));
        mBottomSheetBehavior.setPeekHeight(0);
        builder = new AlertDialog.Builder(AnswerList_Activity.this);
//        mBottomSheetBehavior.setSkipCollapsed(false);
        initDatas();
        initSwipeRefreshLayout();
        createBottomSheetDialog();

        initBadgeView();
    }

    private void initBadgeView() {
        int count = ZPreferenceUtils.getPrefInt(Constant.PUSHCOUNT, 0);
        if (count > 0) {
            ib_question.showTextBadge(count + "");
        } else {
            ib_question.hiddenBadge();
        }

    }

    /**
     * 初始化swipeRefreshLayout
     */
    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        //设置刷新时动画的颜色，可以设置4个
        refreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        refreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh(DEFAULT);
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void Refresh(final Type type) {
        initBadgeView();
        AnswerListAdapter.setEnableLoadMore(false);
        mCurrentCounter = 0;
        mCurrentIndex = 1;
        switch (type) {
//            case ATTENTION:
//                map.clear();
//                map.put("collectUser", ZPreferenceUtils.getPrefString(Constant.USERNAME, ""));
//                break;
            case ANSWER:
                map.clear();
                map.put("ztypeid", ZPreferenceUtils.getPrefString(Constant.PID, "0x00"));
                break;
            case QUESTION:
                map.clear();
                map.put("tphone", ZPreferenceUtils.getPrefString(Constant.USERNAME, ""));
                break;
            case ALL:
                map.clear();
                break;
            case SEARCH:
                map.clear();
                map.put("title", et_search.getText().toString().trim());
                break;
            case Kind:
                map.clear();
                map.put("wtypeid", kind);
                break;
            default:
                break;
        }
        String user = ZPreferenceUtils.getPrefString(Constant.USERNAME, "");
        OkGo.post(Constant.GET_LOADISSUELIST)
                .params("pageNum", 1)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String str, Call call, Response response) {
                        View view = LayoutInflater.from(AnswerList_Activity.this).inflate(R.layout.empty_view_attention, (ViewGroup) ry_answers.getParent(), false);
                        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
                        Answer answer = new Gson().fromJson(str, Answer.class);
                        List<Answer.DataBean> answerList = answer.getData();
                        answers.clear();
                        answers.addAll(answerList);
                        if (answers != null) {
                            if (answers.size() == 0) {
                                AnswerListAdapter.setHeaderAndEmpty(true);
                                tv_message.setText("暂无这个类别的问题！");
                                AnswerListAdapter.setEmptyView(view);
                            }
                            AnswerListAdapter.setNewData(answers);
//                            AnswerListAdapter.notifyDataSetChanged();
                            AnswerListAdapter.setEnableLoadMore(true);
                            refreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ZToastUtils.showShort(AnswerList_Activity.this, "连接服务器失败,请检测网络连接");
                    }
                });
    }

    /**
     * 上拉自动加载
     */
    private void load() {
        mCurrentIndex++;
        String user = ZPreferenceUtils.getPrefString(Constant.USERNAME, "");
        OkGo.post(Constant.GET_LOADISSUELIST)
                .params("pageNum", mCurrentIndex)
                .params(map)
                .execute(new StringCallback() {
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
                        Answer answer = new Gson().fromJson(json, Answer.class);
                        List<Answer.DataBean> answerList = answer.getData();
                        if (mCurrentCounter >= mTotalCount || answerList.isEmpty()) {
                            //数据全部加载完毕
                            AnswerListAdapter.loadMoreEnd();
                        } else {
                            answers.addAll(answerList);
                            if (answers != null) {
                                AnswerListAdapter.notifyDataSetChanged();
                                AnswerListAdapter.loadMoreComplete();
                            }
                        }
                        refreshLayout.setEnabled(true);
                    }
                });
    }

    private void createBottomSheetDialog() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null, false);
        mBottomSheetDialog.setContentView(view);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        /**
         * 获取问题类型列表
         */
        OkGo.get(Constant.WTTYPE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        List<BaseType> types =new Gson().fromJson(s,new TypeToken<List<BaseType>>(){}.getType());
                        ListRecyclerAdapter_ adapter = new ListRecyclerAdapter_(types);
                        recyclerView.setAdapter(adapter);
                        /**
                         * 选择领域
                         */
                        adapter.setOnBottomItemClickListener(new ListRecyclerAdapter_.onBottomItemClickListener() {
                            @Override
                            public void onBottomItemClick(int postion, String text) {
//                ToastUtils.showShort(AnswerList_Activity.this,postion+"");
                                kind = text;
                                mBottomSheetDialog.dismiss();
                                Refresh(Kind);
                            }
                        });
                        setBehaviorCallback();
                    }
                });

    }

    private void setBehaviorCallback() {
        View view = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void initHeaderView() {
        rl_quick = (RelativeLayout) headerLayout.findViewById(R.id.rl_quick);
        et_search = (EditText) findViewById(R.id.search);
        ib_search = (ImageButton) findViewById(R.id.ib_search);
        ib_exper_list = (ImageButton) headerLayout.findViewById(R.id.ib_exper_list);
        ib_gz = (BGABadgeRadioButton) findViewById(R.id.ib_gz);
        ib_question = (BGABadgeRadioButton) findViewById(R.id.ib_question);
        ib_answer = (BGABadgeRadioButton) findViewById(R.id.ib_answer);
        tv_all = (TextView) headerLayout.findViewById(R.id.tv_all);

        /**
         * EditText Search事件
         */
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    Refresh(SEARCH);
                }
                return false;
            }
        });

        ib_exper_list.setOnClickListener(this);
        ib_gz.setOnClickListener(this);
        ib_question.setOnClickListener(this);
        ib_answer.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        ib_search.setOnClickListener(this);
        rl_quick.setOnClickListener(this);
    }

    private ScaleDownShowBehavior.OnStateChangedListener onStateChangedListener = new ScaleDownShowBehavior.OnStateChangedListener() {
        @Override
        public void onChanged(boolean isShow) {
            mBottomSheetBehavior.setState(isShow ? BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_COLLAPSED);
        }
    };

    private boolean initialize = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!initialize) {
            initialize = true;
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        String user = ZPreferenceUtils.getPrefString(Constant.USERNAME, "");
        OkGo.post(Constant.GET_LOADISSUELIST)
                .params("pageNum", mCurrentIndex)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String str, Call call, Response response) {
                        Answer answer = new Gson().fromJson(str, Answer.class);
                        answers = answer.getData();
                        mCurrentCounter = answers.size();
                        AnswerListAdapter.setNewData(answers);
                        AnswerListAdapter.addHeaderView(headerLayout);
                        if (answers != null) {
                            if (answers.size() == 0) {
                                View view = LayoutInflater.from(AnswerList_Activity.this).inflate(R.layout.empty_view, (ViewGroup) ry_answers.getParent(), false);
                                TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
                                tv_message.setText("暂时还没有问题!");
                                AnswerListAdapter.setEmptyView(view);
                            }
                            ry_answers.setAdapter(AnswerListAdapter);
                            AnswerListAdapter.setOnLoadMoreListener(AnswerList_Activity.this, ry_answers);
                            AnswerListAdapter.disableLoadMoreIfNotFullPage(ry_answers);
//                            if (ZPreferenceUtils.getPrefBoolean(Constant.IS_OPEN_GUIDE_HOME, false) || ZPreferenceUtils.getPrefInt(Constant.IS_FIRST_CLICK, 0) == 0) {
//                                getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                                    @Override
//                                    public void onGlobalLayout() {
//                                        getWindow().getDecorView().getViewTreeObserver()
//                                                .removeOnGlobalLayoutListener(this);
//                                        showNextKnownTipView();
//                                        ZPreferenceUtils.setPrefInt(Constant.IS_FIRST_CLICK, 1);
//                                    }
//                                });
//                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ZToastUtils.showShort(AnswerList_Activity.this, "连接服务器失败,请检测网络连接！");
                    }
                });
    }

    /**
     * 获取专家专业分类
     */
    private void getExperterType() {
        final ListDataSave save = new ListDataSave(AnswerList_Activity.this, "type");
        if (save.getDataArray("type") == null) {
            OkhttpHelper.getInstance().Get(Constant.EXPERT_TYPE_URL, new OkhttpHelper.MyCallBack() {
                @Override
                public void onSucces(String response, int tag) {
                    JSONArray array;
                    try {
                        array = new JSONArray(response);
                        String[] kinds = new String[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            kinds[i] = object.getString("name");
                        }
                        List list = Arrays.asList(kinds);
                        save.setDataList("type", list);
//                        MyApplication.getInstance().setTypes(kinds);
//                        isLoading = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFaile(Exception errorResponse, int tag) {

                }
            }, 1);
        }
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


    /**
     * 取消关注
     *
     * @param position
     * @param view
     */
    private void cancelIssueCollect(final int position, final View view) {
        builder.setTitle("提醒");
        builder.setMessage("取消关注吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showloading();
                OkGo.post(Constant.CANCEL_ISSUE_COLLECT)
                        .params("issueId", answers.get(position).getId())
                        .params("user", ZPreferenceUtils.getPrefString(Constant.USERNAME, ""))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                if ("1".equals(JsonUtils.getServerResult(s))) {
                                    dismissloading();
                                    ToastUtils.showShort(AnswerList_Activity.this, "取消关注成功！");
//                                    view.setBackgroundResource(R.mipmap.icon_djgz);
//                                    answers.get(position).setIsAttention("0");
//                                    answers.get(position).setAttentionCount(answers.get(position).getAttentionCount() - 1);
                                    AnswerListAdapter.notifyItemChanged(position + 1);
                                } else {
                                    ToastUtils.showShort(AnswerList_Activity.this, "取消关注失败");
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                ToastUtils.showShort(AnswerList_Activity.this, "失败" + e.toString());
                            }
                        });
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
     * 添加关注
     *
     * @param position
     * @param view
     */
    private void addIssueCollect(final int position, final View view) {
        builder.setTitle("提醒");
        builder.setMessage("关注这个问题?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showloading();
                OkGo.post(Constant.ADD_ISSUE_COLLECT)
                        .params("issueId", answers.get(position).getId())
                        .params("user", ZPreferenceUtils.getPrefString(Constant.USERNAME, ""))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                if ("1".equals(JsonUtils.getServerResult(s))) {
                                    dismissloading();
                                    ToastUtils.showShort(AnswerList_Activity.this, "关注成功！");
//                                    view.setBackgroundResource(R.mipmap.icon_gz);
//                                    answers.get(position).setIsAttention("1");
//                                    answers.get(position).setAttentionCount(answers.get(position).getAttentionCount() + 1);
                                    AnswerListAdapter.notifyItemChanged(position + 1);
                                } else {
                                    ToastUtils.showShort(AnswerList_Activity.this, "关注失败");
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                ToastUtils.showShort(AnswerList_Activity.this, "失败" + e.toString());
                            }
                        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void hideFAB() {
        FAB.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorUtil.scaleHide(FAB, new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        FAB.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                    }
                });
            }
        }, 500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_gz:
                if (mBottomSheetDialog.isShowing()) {
                    mBottomSheetDialog.dismiss();
                } else {
                    mBottomSheetDialog.show();
                }
                break;
            case R.id.ib_exper_list:
                startActivity(new Intent(AnswerList_Activity.this, ExperterListActivity.class));
                break;
            //回答
            case R.id.ib_answer:
                Refresh(ANSWER);
                break;
            //问题
            case R.id.ib_question:
                Refresh(QUESTION);
                break;
            //全部问题
            case R.id.tv_all:
                Refresh(ALL);
                break;
            case R.id.ib_search:
                Refresh(SEARCH);
                break;
            case R.id.rl_quick:
                startActivity(new Intent(this, AskActivity.class).putExtra("isExperter", false));
                break;
            default:
                break;
        }
    }

    public void showloading() {
        loadingIndicatorView = new AVLoadingIndicatorView(this);
        loadingIndicatorView.setVisibility(View.VISIBLE);
    }

    public void dismissloading() {
        if (loadingIndicatorView != null) {
            loadingIndicatorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        load();
    }

    /**
     * 高亮引导层
     */
    //高亮
    public void showNextKnownTipView() {
        mHightLight = new HighLight(AnswerList_Activity.this)//
                .autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
//                .intercept(false)//设置拦截属性为false 高亮布局不影响后面布局的滑动效果
                .intercept(true)//拦截属性默认为true 使下方callback生效
                .enableNext()//开启next模式并通过show方法显示 然后通过调用next()方法切换到下一个提示布局，直到移除自身
//                .anchor(findViewById(R.id.container))//如果是Activity上增加引导层，不需要设置anchor
                .addHighLight(R.id.ib_gz, R.layout.info_guanzhu, new OnTopPosCallback(45), new CircleLightShape())
                .addHighLight(R.id.ib_question, R.layout.inifo_my_question, new OnTopAndLeftCallback(45), new CircleLightShape())
                .addHighLight(R.id.ib_answer, R.layout.info_myanswer, new OnTopPosCallback(45), new CircleLightShape())
                .addHighLight(R.id.ib_search, R.layout.info_search, new OnBottomPosCallback(50), new CircleLightShape())
                .setOnRemoveCallback(new HighLightInterface.OnRemoveCallback() {//监听移除回调 intercept为true时生效
                    @Override
                    public void onRemove() {
                    }
                })
                .setOnShowCallback(new HighLightInterface.OnShowCallback() {//监听显示回调 intercept为true时生效
                    @Override
                    public void onShow() {

                    }
                });
        mHightLight.show();

    }

    /**
     * 响应所有R.id.iv_known的控件的点击事件
     * <p>
     * 移除高亮布局
     * </p>
     *
     * @param view
     */
    public void clickKnown(View view) {
        if (mHightLight.isShowing() && mHightLight.isNext())//如果开启next模式
        {
            mHightLight.next();
        } else {
            remove(null);
        }
    }

    public void remove(View view) {
        mHightLight.remove();
    }

}
