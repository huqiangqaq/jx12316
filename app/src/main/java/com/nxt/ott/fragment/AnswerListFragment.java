package com.nxt.ott.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.activity.expert.ExpertSh1Activity;
import com.nxt.ott.adapter.AnswerListAdapter;
import com.nxt.ott.adapter.HotExperterAdapter;
import com.nxt.ott.domain.Answer;
import com.nxt.ott.domain.HotExperter;
import com.nxt.ott.domain.RecommentExperter;
import com.nxt.ott.expertUpdate.AskActivity;
import com.nxt.ott.expertUpdate.CommentActivity;
import com.nxt.ott.util.ListDataSave;
import com.nxt.ott.util.OkhttpHelper;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.view.ExperterInfoDialog;
import com.nxt.zyl.ui.fragment.ZBaseFragment;
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

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;


public class AnswerListFragment extends ZBaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //标志是否已经初始完成
    private boolean isPrepared;
    @BindView(R.id.type)
    TextView sp_type;
    @BindView(R.id.search)
    Button btn_search;
    @BindView(R.id.ll_title)
    LinearLayout ll_title;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.ry_hotexpert)
    RecyclerView ry_hotexpert;
    @BindView(R.id.ry_answer)
    RecyclerView ry_answer;
    @BindView(R.id.tv_hot)
    TextView tvhot;
    @BindView(R.id.btn_myAnswer)
    Button btn_myAnswer;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.btn_my_qusetion)
    Button btn_my_question;
    @BindView(R.id.btn_all)
    Button btn_all;
    @BindView(R.id.btn_attention)
    Button btn_attention;
//    @BindView(R.id.ll_today)
//    LinearLayout ll_today;
    private List<HotExperter> hotExperters = new ArrayList<>();
    private List<Answer.DataBean> answers = new ArrayList<>();
    private String experterKind;
    private AnswerListAdapter answerListAdapter;
    private boolean isAttention;
    private AlertDialog.Builder builder;
    private boolean isSearch = false;
    private boolean isMyQuestion = false;
    private boolean isMyAnswer = false;
    private boolean isMyAttention = false;

    public AnswerListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnswerListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnswerListFragment newInstance(String param1, String param2) {
        AnswerListFragment fragment = new AnswerListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void initView(View view) {
        initDatas();
        builder = new AlertDialog.Builder(getActivity());
        ll_title.getBackground().setAlpha(150);
        ll_bottom.getBackground().setAlpha(150);
//        ll_today.getBackground().setAlpha(150);
        getExperterType();
        getHotExperters();
        tvhot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("huqiang","huqiang");
            }
        });
        btn_myAnswer.setOnClickListener(this);
        sp_type.setOnClickListener(this);
        et_search.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_my_question.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        btn_attention.setOnClickListener(this);
        tvhot.setOnClickListener(this);
        onRecyclerItemClick();
        initSwipeRefreshLayout();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_answer_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getExperterType() {
        final ListDataSave save = new ListDataSave(getActivity(),"type");
        if (save.getDataArray("type")==null){
            OkhttpHelper.getInstance().Get(Constant.EXPERT_TYPE_URL, new OkhttpHelper.MyCallBack() {
                @Override
                public void onSucces(String response, int tag) {
                    JSONArray array = null;
                    try {
                        array = new JSONArray(response);
                        String[] kinds = new String[array.length()];
                        for (int i=0;i<array.length();i++){
                            JSONObject object = array.getJSONObject(i);
                            kinds[i] =object.getString("name");
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
            },1);
        }

    }

    private void getHotExperters() {
        OkGo.post(Constant.GET_RECOMMMEND_EXPERTER)
                .params("pageIndex", 1)
                .params("pageSize", 5)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        RecommentExperter experter = new Gson().fromJson(s, RecommentExperter.class);
                        for (int i=0;i<experter.getRows().size();i++){
                            hotExperters.add(new HotExperter(experter.getRows().get(i).getTitle(),experter.getRows().get(i).getName(),experter.getRows().get(i).getTel(),
                                    experter.getRows().get(i).getYewuzhuanchang(),experter.getRows().get(i).getZixunfuwu(),experter.getRows().get(i).getRemarks(),experter.getRows().get(i).getUid(),
                                    experter.getRows().get(i).getJishuzhiwu()));
                        }
                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        ry_hotexpert.setLayoutManager(manager);
                        HotExperterAdapter adapter = new HotExperterAdapter(hotExperters);
                        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                        ry_hotexpert.setAdapter(adapter);
                        ry_hotexpert.addOnItemTouchListener(new OnItemClickListener() {
                            @Override
                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Bundle bundle = new Bundle();
                                bundle.putString("name", hotExperters.get(position).getName());
                                bundle.putString("type", hotExperters.get(position).getType());
                                bundle.putString("avator", hotExperters.get(position).getAvator());
                                bundle.putString("zixunfuwu", hotExperters.get(position).getZixunfuwu());
                                bundle.putString("remarks", hotExperters.get(position).getRemarks());
                                bundle.putString("uid",hotExperters.get(position).getUid());
                                bundle.putString("tel",hotExperters.get(position).getTel());
                                ExperterInfoDialog dialog = ExperterInfoDialog.newInstance(bundle);
                            }

                        });
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    private void initSwipeRefreshLayout() {
        //设置刷新时动画的颜色，可以设置4个
        refreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        refreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void onRecyclerItemClick() {
        ry_answer.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("id",answers.get(position).getId());
//                intent.putExtra("title",answers.get(position).getTitle());
//                intent.putExtra("text",answers.get(position).getText());
//                intent.putExtra("img",answers.get(position).getImg());
//                intent.putExtra("voice",answers.get(position).getVoice());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }


            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
//                //初始化
//                isAttention = "1".equals(answers.get(position).getIsAttention());
//                if (isAttention){
//                    //取消关注
//                    cancelIssueCollect(position,view);
//                }else {
//                    //关注
//                    addIssueCollect(position,view);
//                }
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

        });
    }

    private void cancelIssueCollect(final int position, final View view) {
        builder.setTitle("提醒");
        builder.setMessage("取消关注吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showloading();
                OkGo.post(Constant.CANCEL_ISSUE_COLLECT)
                        .params("issueId",answers.get(position).getId())
                        .params("user",ZPreferenceUtils.getPrefString(Constant.USERNAME,""))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
//                                if ("1".equals(JsonUtils.getServerResult(s))){
//                                    dismissloading();
//                                    ToastUtils.showShort(getActivity(),"取消关注成功！");
////                                    view.setBackgroundResource(R.mipmap.icon_djgz);
//                                    answers.get(position).setIsAttention("0");
//                                    answers.get(position).setAttentionCount(answers.get(position).getAttentionCount()-1);
//                                    answerListAdapter.notifyItemChanged(position);
//                                }else {
//                                    ToastUtils.showShort(getActivity(),"取消关注失败");
//                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                ToastUtils.showShort(getActivity(),"失败"+e.toString());
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
        isAttention = false;
        builder.show();

    }


    private void addIssueCollect(final int position, final View view) {
        builder.setTitle("提醒");
        builder.setMessage("关注这个问题?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showloading();
                OkGo.post(Constant.ADD_ISSUE_COLLECT)
                        .params("issueId",answers.get(position).getId())
                        .params("user",ZPreferenceUtils.getPrefString(Constant.USERNAME,""))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
//                                if ("1".equals(JsonUtils.getServerResult(s))){
//                                    dismissloading();
//                                    ToastUtils.showShort(getActivity(),"关注成功！");
////                                    view.setBackgroundResource(R.mipmap.icon_gz);
//                                    answers.get(position).setIsAttention("1");
//                                    answers.get(position).setAttentionCount(answers.get(position).getAttentionCount()+1);
//                                    answerListAdapter.notifyItemChanged(position);
//                                }else {
//                                    ToastUtils.showShort(getActivity(),"关注失败");
//                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                ToastUtils.showShort(getActivity(),"失败"+e.toString());
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
        isAttention = true;
        builder.show();
    }

    private void initDatas(){
        String user = ZPreferenceUtils.getPrefString(Constant.USERNAME,"");
        OkGo.post(Constant.GET_LOADISSUELIST)
                .params("attentionUser",user)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String str, Call call, Response response) {
                        Answer answer = new Gson().fromJson(str,Answer.class);
                        answers = answer.getData();
                        ry_answer.setLayoutManager(new LinearLayoutManager(getActivity()));
                        ry_answer.setNestedScrollingEnabled(false);
                        ry_answer.setHasFixedSize(false);
                        ry_answer.setItemAnimator(new DefaultItemAnimator());
                        answerListAdapter = new AnswerListAdapter(answers,false);
                        if (answers!=null){
                            if (answers.size()==0){
                                View view = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) ry_answer.getParent(),false);
                                TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
                                tv_message.setText("暂时还没有问题!");
                                answerListAdapter.setEmptyView(view);
                            }
                            ry_answer.setAdapter(answerListAdapter);
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ZToastUtils.showShort(getActivity(),"服务器内部错误！");
                    }
                });
    }
    private void Refresh(){
        Map<String,String> map = new HashMap<>();
        if (isSearch){
            map.put("title",et_search.getText().toString().trim());
        }
        if (isMyAnswer){
            map.put("issueAnswer", ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
        }
        if (isMyQuestion){
            map.put("userName",ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
        }
        if (isMyAttention){
            map.put("collectUser",ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
        }
        String user = ZPreferenceUtils.getPrefString(Constant.USERNAME,"");
        OkGo.post(Constant.GET_LOADISSUELIST)
                .params(map)
                .params("attentionUser",user)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String str, Call call, Response response) {
                        Answer answer = new Gson().fromJson(str,Answer.class);
                        List<Answer.DataBean> answerList = answer.getData();
                        answers.clear();
                        answers.addAll(answerList);
                        if (answers!=null){
                            if (answers.size()==0){
                                if (isMyAttention){
                                    answerListAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.empty_view_attention, (ViewGroup) ry_answer.getParent(), false));
                                }else {
                                    answerListAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) ry_answer.getParent(), false));
                                }
                            }
                            answerListAdapter.notifyDataSetChanged();
                            refreshLayout.setRefreshing(false);
                            isSearch = false;
                            isMyQuestion = false;
                            isMyAnswer = false;
                            isMyAttention = false;
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ZToastUtils.showShort(getActivity(),"服务器内部错误！");
                    }
                });
    }
    private void showTypeSelDialog() {
        final String[] types = new ListDataSave(getActivity(),"type").getDataArray("type");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择问题分类！");
        builder.setSingleChoiceItems(types, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                experterKind = types[which];
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //请求数据
                OkGo.post(Constant.GET_LOADISSUELIST)
                        .params("type",experterKind)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String str, Call call, Response response) {
                                Answer answer = new Gson().fromJson(str,Answer.class);
                                List<Answer.DataBean> beanList = answer.getData();
                                answers.clear();
                                answers.addAll(beanList);
                                if (answers!=null){
                                    if (!(answers.size()>0)){
                                        answerListAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) ry_answer.getParent(),false));
                                    }
                                    answerListAdapter.notifyDataSetChanged();
                                }
                            }
                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_myAnswer:
                startActivity(new Intent(getActivity(),AskActivity.class));
                break;
            case R.id.type:
                showTypeSelDialog();
                break;
            case R.id.et_search:
                et_search.requestFocus();
                break;
            case R.id.search:
                isSearch = true;
                Refresh();
                break;
            case R.id.btn_my_qusetion:
                isMyAnswer = true;
                Refresh();
                break;
            case R.id.btn_all:
                isMyQuestion = true;
                Refresh();
                break;
            case R.id.btn_attention:
                isMyAttention = true;
                Refresh();
                break;
            case R.id.tv_hot:
                startActivity(new Intent(getActivity(), ExpertSh1Activity.class));
                break;
        }
    }
}
