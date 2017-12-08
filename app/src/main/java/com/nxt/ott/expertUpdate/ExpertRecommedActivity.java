package com.nxt.ott.expertUpdate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ExpertCommentAdapter;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.RecommentExperter;
import com.nxt.ott.fragment.ExperterInfoFragment;
import com.nxt.ott.sweetAlert.SweetAlertDialog;
import com.nxt.ott.util.JsonUtils;
import com.nxt.ott.util.ListDataSave;
import com.nxt.ott.util.OkhttpHelper;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.view.ProcessSuccessView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;


public class ExpertRecommedActivity extends BaseTitleActivity implements ExperterInfoFragment.onSubmitClick, ExperterInfoFragment.onSubmitAllClick {
    @BindView(R.id.ry_comment)
    RecyclerView ry_comment;
    @BindView(R.id.tv_message)
    TextView tv_message;
    private ExpertCommentAdapter adapter;
    private List<RecommentExperter.RowsBean> recommentExperterList = new ArrayList<>();
    private ExperterInfoFragment infoFragment;
    private Map<String, String> map = new HashMap<>();
    private ArrayList<String> imgPath = new ArrayList<>();//图片地址
    private String voicePath;
    private FragmentManager manager1;
    private String type;
    @BindView(R.id.iv_main)
    ImageButton iv_main;
    @BindView(R.id.tv_kind)
    ImageButton tv_kind;
    @BindView(R.id.tv_search)
    ImageButton tv_search;
    private String experterKind, title, text;
    @BindView(R.id.success)
    ProcessSuccessView successView;
    Bundle bundle;

    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this, "专家推荐");
        getExperterType();
        initDatas();
        successView.starRun();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ry_comment.setNestedScrollingEnabled(false);
        ry_comment.setHasFixedSize(false);
        ry_comment.setLayoutManager(manager);
        ry_comment.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = recommentExperterList.get(position).getName();
                type = recommentExperterList.get(position).getYewuzhuanchang();
                String zixunfuwu = recommentExperterList.get(position).getZixunfuwu();
                String remarks = recommentExperterList.get(position).getRemarks();
                String avatar = String.format(Constant.IMAGE_URL, recommentExperterList.get(position).getTitle());
                String tel = recommentExperterList.get(position).getUid();
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("type", type);
                bundle.putString("zixunfuwu", zixunfuwu);
                bundle.putString("remarks", remarks);
                bundle.putString("avatar", avatar);
                bundle.putString("tel", tel);
                ExperterInfoFragment fragment = ExperterInfoFragment.newInstance(name, type, zixunfuwu, remarks, avatar, tel);
                fragment.setOnSubmitClick(ExpertRecommedActivity.this);
                fragment.setOnSubmitAllClick(ExpertRecommedActivity.this);
                fragment.setArguments(bundle);
                manager1.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        iv_main.setOnClickListener(this);
        tv_kind.setOnClickListener(this);
        tv_search.setOnClickListener(this);
//        initViewPager();
    }

    /**
     * 获取专家专业类别
     */
    private void getExperterType() {
        final ListDataSave save = new ListDataSave(ExpertRecommedActivity.this, "type");
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

//    private void initViewPager() {
//        ActiveExperterFragment activeExperterFragment = ActiveExperterFragment.newInstance(title,text);
//        NotActiveFragment notActiveFragment = NotActiveFragment.newInstance(title,text);
//        fragments.add(activeExperterFragment);
//        fragments.add(notActiveFragment);
//
//        //viewpager设置适配器
//        vp_experter.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments));
//        vp_experter.setCurrentItem(0);
//    }


    private void initDatas() {
        bundle = getIntent().getExtras();
        title = bundle.getString("title");
        text = bundle.getString("text");
        map.put("title", title);
        map.put("text", text);
        imgPath = bundle.getStringArrayList("img");
        voicePath = bundle.getString("voice");
        map.put("userName", bundle.getString("userName"));
        map.put("userNickName", bundle.getString("userNickName"));
//        for (String filePath:imgPath){
//            imgFiles.add(new File(filePath));
//        }
        //根据问题标签匹配专家
        OkGo.post(Constant.GET_RECOMMMEND_EXPERTER)
                .params("pageIndex", 1)
                .params("pageSize", 5)
                .params("title", title)
                .params("text", text)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        RecommentExperter experter = new Gson().fromJson(s, RecommentExperter.class);
                        SpannableString spanText = new SpannableString("根据您的问题,已为你推荐如下" + experter.getType() + "专家!");
                        spanText.setSpan(new ForegroundColorSpan(Color.RED), 14, 18,
                                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        type = experter.getType();
                        tv_message.setText(spanText);
                        recommentExperterList = experter.getRows();
                        OkGo.post(Constant.GET_RECOMMMEND_EXPERTER)
                                .params("pageIndex", 2)
                                .params("pageSize", 5)
                                .params("title", title)
                                .params("text", text)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        RecommentExperter experter = new Gson().fromJson(s, RecommentExperter.class);
                                        recommentExperterList.addAll(experter.getRows());
                                        if (recommentExperterList != null && recommentExperterList.size() > 0) {
                                            adapter = new ExpertCommentAdapter(recommentExperterList);
                                            ry_comment.setAdapter(adapter);
                                            manager1 = getSupportFragmentManager();
                                            infoFragment = ExperterInfoFragment.newInstance(recommentExperterList.get(0).getName(), recommentExperterList.get(0).getYewuzhuanchang(),
                                                    recommentExperterList.get(0).getZixunfuwu(), recommentExperterList.get(0).getRemarks(),
                                                    String.format(Constant.IMAGE_URL, recommentExperterList.get(0).getTitle()), recommentExperterList.get(0).getUid());
                                            infoFragment.setOnSubmitClick(ExpertRecommedActivity.this);
                                            infoFragment.setOnSubmitAllClick(ExpertRecommedActivity.this);
                                            type = recommentExperterList.get(0).getYewuzhuanchang();
                                            manager1.beginTransaction().replace(R.id.container, infoFragment).commit();
                                        }
                                    }
                                });

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_expert_recommed;
    }

    //提交给专家
    @Override
    public void submit(String tel) {
        map.put("point", tel);
        map.put("type", type);
//        EaseUser easeUser = EaseUserUtils.getUserInfo(tel);
//        if (easeUser!=null){
//            map.put("pointNickName", easeUser.getNickname());
//        }else {
        map.put("pointNickName", "");
//        }
        upToServer();
    }

    //提交给所有人
    @Override
    public void submitAll() {
        map.put("point", "全部");
        map.put("pointNickName", "全部");
        map.put("type", "全部");
        upToServer();
    }

    private void upToServer() {
        showLoadingDialog(R.string.hint_message);
        PostRequest request = OkGo.post(Constant.ADD_ISSUE);
        request.isMultipart(true);
        request.params(map);
        if (imgPath.size() > 0) {
            for (int i = 0; i < imgPath.size(); i++) {
                request.params("img" + i, new File(imgPath.get(i)));
            }
        }
        if (!TextUtils.isEmpty(voicePath)) {
            request.params("voiceFile", new File(voicePath));
        }
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                if ("1".equals(JsonUtils.getServerResult(s))) {
                    dismissLoadingDialog();
                    new SweetAlertDialog(ExpertRecommedActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText("您的问题已成功提交,请耐心等候回答!")
                            .setConfirmText("好的!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    //回到首页
                                    sweetAlertDialog.dismiss();
                                    startActivity(new Intent(ExpertRecommedActivity.this, AnswerList_Activity.class));
                                    finish();
                                }
                            })
                            .show();

                } else {
                    new SweetAlertDialog(ExpertRecommedActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("提交失败:" + JsonUtils.getServerMsg(s))
                            .setConfirmText("好的!")
                            .show();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                dismissloading();
                ToastUtils.showShort(ExpertRecommedActivity.this, e.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_main:
                //回到首页
                startActivity(new Intent(this, AnswerList_Activity.class));
                finish();
                break;
            case R.id.tv_kind:
                //选择分类
                showKindDialog();
                break;
            case R.id.tv_search:
                bundle.putBoolean("isRecommed", true);
                startActivity(new Intent(ExpertRecommedActivity.this, ExperterListActivity.class).putExtras(bundle));
                break;
                default:
                    break;
        }
    }

    /**
     * 选择专家分类的对话框
     */
    private void showKindDialog() {
        final String[] types = new ListDataSave(ExpertRecommedActivity.this, "type").getDataArray("type");
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpertRecommedActivity.this);
        builder.setTitle("请选择专家分类！");
        builder.setSingleChoiceItems(types, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                experterKind = types[which];
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.dismiss();
                showLoadingDialog(R.string.kind_message);
                OkGo.post(Constant.GET_RECOMMMEND_EXPERTER)
                        .params("pageIndex", 1)
                        .params("pageSize", 10)
                        .params("title", title)
                        .params("text", text)
                        .params("type", experterKind)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                dismissLoadingDialog();
                                RecommentExperter experter = new Gson().fromJson(s, RecommentExperter.class);
                                final List<RecommentExperter.RowsBean> rowsBeen = experter.getRows();
//                                SpannableString spanText = new SpannableString("根据您的问题,已为你推荐如下"+experterKind+"专家!");
//                                spanText.setSpan(new ForegroundColorSpan(Color.RED), 14, 18,
//                                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//                                tv_message.setText(spanText);
                                ToastUtils.showShort(ExpertRecommedActivity.this, "选择了" + experterKind);
                                OkGo.post(Constant.GET_RECOMMMEND_EXPERTER)
                                        .params("pageIndex", 2)
                                        .params("pageSize", 10)
                                        .params("title", title)
                                        .params("text", text)
                                        .params("type", experterKind)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                RecommentExperter experter = new Gson().fromJson(s, RecommentExperter.class);
                                                rowsBeen.addAll(experter.getRows());
                                                recommentExperterList.clear();
                                                recommentExperterList.addAll(rowsBeen);
                                                adapter.notifyDataSetChanged();
                                                if (recommentExperterList.size() == 0) {
                                                    View view = LayoutInflater.from(ExpertRecommedActivity.this).inflate(R.layout.empty_view_attention, (ViewGroup) ry_comment.getParent(), false);
                                                    TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
                                                    tv_message.setText("暂无匹配的专家~!");
                                                    adapter.setEmptyView(view);
                                                    findViewById(R.id.container).setVisibility(View.GONE);
                                                } else {
                                                    findViewById(R.id.container).setVisibility(View.VISIBLE);
                                                    ExperterInfoFragment fragment = ExperterInfoFragment.newInstance(recommentExperterList.get(0).getName(), recommentExperterList.get(0).getYewuzhuanchang(),
                                                            recommentExperterList.get(0).getZixunfuwu(), recommentExperterList.get(0).getRemarks(),
                                                            String.format(Constant.IMAGE_URL, recommentExperterList.get(0).getTitle()), recommentExperterList.get(0).getUid());
                                                    fragment.setOnSubmitClick(ExpertRecommedActivity.this);
                                                    fragment.setOnSubmitAllClick(ExpertRecommedActivity.this);
                                                    type = recommentExperterList.get(0).getYewuzhuanchang();
                                                    manager1.beginTransaction().replace(R.id.container, fragment).commit();
                                                }

                                            }
                                        });

                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                dismissLoadingDialog();
                                ToastUtils.showShort(ExpertRecommedActivity.this, "失败:" + e.toString());
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
}
