package com.nxt.ott.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ExperterAdapter2;
import com.nxt.ott.adapter.ListDropDownAdapter;
import com.nxt.ott.domain.City;
import com.nxt.ott.domain.Country;
import com.nxt.ott.domain.ExpertType;
import com.nxt.ott.domain.Experter;
import com.nxt.ott.expertUpdate.AnswerList_Activity;
import com.nxt.ott.expertUpdate.AskActivity;
import com.nxt.ott.sweetAlert.SweetAlertDialog;
import com.nxt.ott.util.JsonUtils;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.view.DropDownMenu;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by huqiang on 2017/5/11 9:57.
 */

public class ExperterListFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener,ExperterAdapter2.onExperterClick {
    private View view;
    private DropDownMenu mDropDownMenu;
    private String headers[] = {"省市", "县区", "专业"};
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter proviceAdapter, cityAdatper, typeAdapter;
    private Map<String, String> map = new HashMap<>();
    private List<City> cities = new ArrayList<>();
    private List<String> citysName = new ArrayList<>();
    private List<ExpertType> types = new ArrayList<>();
    private List<String> typesName = new ArrayList<>();
    private List<Country> countries = new ArrayList<>();
    private List<String> countrysName = new ArrayList<>();
    private List<Experter> experterList = new ArrayList<>();
    private String mSelectCountry = null;
    private ExperterAdapter2 experterAdapter;
    private ListView proviceView, cityView, typeView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int mTotalCount =0;
    private int mPageSize = 20;
    private int mCurrentIndex = 1;
    private int mCurrentCounter = 0;
    private boolean isRecommed = false;
    private Map<String,String> mDataMap = new HashMap<>();
    private ArrayList<String> imgPath = new ArrayList<>();//图片地址
    private String voicePath;
    protected ProgressDialog loadingDialog;

    public static ExperterListFragment newInstance(Bundle bundle) {
        ExperterListFragment fragment = new ExperterListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.fragment_experter_list, container, false);
        Bundle bundle = getArguments();
        if (bundle!=null){
            isRecommed = bundle.getBoolean("isRecommed");
            String title = bundle.getString("title");
           String text = bundle.getString("text");
            mDataMap.put("title", title);
            mDataMap.put("text", text);
            imgPath = bundle.getStringArrayList("img");
            voicePath = bundle.getString("voice");
            mDataMap.put("userName", bundle.getString("userName"));
            mDataMap.put("userNickName", bundle.getString("userNickName"));
        }
        initView();
        return view;
    }


    private void initView() {
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
//        ry_list = (RecyclerView) view.findViewById(R.id.ry_list);
//        ry_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        proviceView = new ListView(getActivity());
        popupViews.add(proviceView);
        cityView = new ListView(getActivity());
        popupViews.add(cityView);
        typeView = new ListView(getActivity());
        popupViews.add(typeView);
        swipeRefreshLayout = new SwipeRefreshLayout(getActivity());
        swipeRefreshLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final RecyclerView contentView = new RecyclerView(getActivity());
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout.addView(contentView);
        initSwipeRefreshLayout();
        OkGo.post(Constant.EXPERT_TYPE_RESULT)
                .params("page",mCurrentIndex)
                .params("pagesize",mPageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject js;
                        try {
                            js = new JSONObject(s);
                            mTotalCount = Integer.parseInt(js.getString("Total"));
                            experterList = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                            }.getType());
                            mCurrentCounter = experterList.size();
                            experterAdapter = new ExperterAdapter2(R.layout.layout_experter_list, experterList,isRecommed);
                            contentView.setAdapter(experterAdapter);
                            experterAdapter.setOnExperterClick(ExperterListFragment.this);
                            experterAdapter.setOnLoadMoreListener(ExperterListFragment.this,contentView);
                            experterAdapter.disableLoadMoreIfNotFullPage(contentView);
                            mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, swipeRefreshLayout);
                            getItemData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 初始化刷新控件
     */
    private void initSwipeRefreshLayout() {
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    /**
     * 刷新数据
     */
    private void Refresh() {
        experterAdapter.setEnableLoadMore(false);
        mCurrentCounter =0;
        mCurrentIndex = 1;
        OkGo.post(Constant.EXPERT_TYPE_RESULT)
                .params("page",mCurrentIndex)
                .params("pagesize",mPageSize)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject js = null;
                        try {
                            js = new JSONObject(s);
                            List<Experter> list = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                            }.getType());
                            mCurrentCounter = list.size();
                            experterList.clear();
                            experterList.addAll(list);
                            experterAdapter.notifyDataSetChanged();
                            experterAdapter.setEnableLoadMore(true);
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 加载更多
     */
    private void load(){
        mCurrentIndex++;
        OkGo.post(Constant.EXPERT_TYPE_RESULT)
                .params("page",mCurrentIndex)
                .params("pagesize",mPageSize)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject js = null;
                        try {
                            js = new JSONObject(s);
                            mTotalCount = Integer.parseInt(js.getString("Total"));
                            List<Experter> list = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                            }.getType());
                            mCurrentCounter+=list.size();
                            if (mCurrentCounter>mTotalCount||list.isEmpty()){
                                //数据全部加载完毕
                                experterAdapter.loadMoreEnd();
                            }else {
                                experterList.addAll(list);
                                if (experterList!=null){
                                    experterAdapter.notifyDataSetChanged();
                                    experterAdapter.loadMoreComplete();
                                }
                            }
                            swipeRefreshLayout.setEnabled(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        experterAdapter.loadMoreFail();
                    }
                });
    }

    /**
     * 获取menu的数据
     */
    private void getItemData() {
        countrysName.add("请先选择省市!");
        cityAdatper = new ListDropDownAdapter(getActivity(), countrysName);
        cityView.setAdapter(cityAdatper);
        proviceAdapter = new ListDropDownAdapter(getActivity(), citysName);
        proviceView.setAdapter(proviceAdapter);
        typeAdapter = new ListDropDownAdapter(getActivity(), typesName);
        typeView.setAdapter(typeAdapter);
        //市
        OkGo.post(Constant.CITY_URL)
                .tag(0)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        cities = new Gson().fromJson(s, new TypeToken<List<City>>() {
                        }.getType());
                        if (cities != null) {
                            for (City city : cities) {
                                citysName.add(city.getName());
                            }
                            cities.remove(0);
                            citysName.remove(0);
                            //init provice menu
                            proviceAdapter.setList(citysName);
                            proviceView.setDividerHeight(0);
                            proviceAdapter.notifyDataSetChanged();
                            proviceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    proviceAdapter.setCheckItem(position);
                                    mDropDownMenu.setTabText(citysName.get(position));
                                    mDropDownMenu.setTabText("县区",2);
                                    mDropDownMenu.closeMenu();
                                    mSelectCountry = citysName.get(position);
                                    map.put("areacode", cities.get(position).getCode() + "");
                                    map.put("areatype", "2");
                                    refreshData();
                                    //根据市获取相应的县区
                                    OkGo.post(String.format(Constant.COUNTRY_URL, cities.get(position).getCode()))
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(String s, Call call, Response response) {
                                                    countries.clear();
                                                    countrysName.clear();
                                                    countries = new Gson().fromJson(s, new TypeToken<List<Country>>() {
                                                    }.getType());
                                                    if (countries != null) {
                                                        for (Country country : countries) {
                                                            countrysName.add(country.getName());
                                                        }
                                                        countries.remove(0);
                                                        countrysName.remove(0);
                                                        //init city menu
                                                        cityView.setDividerHeight(0);
                                                        cityAdatper.setList(countrysName);
                                                        cityAdatper.notifyDataSetChanged();
                                                        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                if (null == mSelectCountry && position != 0) {
                                                                    ZToastUtils.showShort(getActivity(), "请先选择省市!");
                                                                    return;
                                                                }
                                                                cityAdatper.setCheckItem(position);
                                                                mDropDownMenu.setTabText(countrysName.get(position));
                                                                mDropDownMenu.closeMenu();

                                                                map.put("areacode", countries.get(position).getCode() + "");
                                                                map.put("areatype", "3");

                                                                refreshData();
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    }
                });


        //专家专业
        OkGo.post(Constant.EXPERT_TYPE_URL)
                .tag(1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        types = new Gson().fromJson(s, new TypeToken<List<ExpertType>>() {
                        }.getType());
                        if (types != null) {
                            for (ExpertType type : types) {
                                typesName.add(type.getName());
                            }
                            types.remove(0);
                            typesName.remove(0);
                            //init type menu

                            typeView.setDividerHeight(0);
                            typeAdapter.setList(typesName);
                            typeAdapter.notifyDataSetChanged();

                            typeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    typeAdapter.setCheckItem(position);
                                    mDropDownMenu.setTabText(typesName.get(position));
                                    mDropDownMenu.closeMenu();
                                    map.put("ywzc", typesName.get(position));

                                    refreshData();
                                }
                            });
                        }
                    }
                });
    }

    private void refreshData() {
        experterAdapter.setEnableLoadMore(false);
        mCurrentCounter =0;
        mCurrentIndex = 1;
        OkGo.post(Constant.EXPERT_TYPE_RESULT)
                .params("page",mCurrentIndex)
                .params("pagesize",mPageSize)
                .params(map)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject js = null;
                        try {
                            js = new JSONObject(s);
                            List<Experter> list;
                            list = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                            }.getType());
                            mCurrentCounter = list.size();
                            experterList.clear();
                            experterList.addAll(list);
                            experterAdapter.notifyDataSetChanged();
                            experterAdapter.setEnableLoadMore(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 根据人名模糊查询
     * @param stext
     */
    public void search(String stext) {
        OkGo.post(Constant.EXPERT_TYPE_RESULT)
                .params("stext", stext)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject js = null;
                        try {
                            js = new JSONObject(s);
                            List<Experter> list;
                            list = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                            }.getType());
                            experterList.clear();
                            experterList.addAll(list);
                            experterAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        swipeRefreshLayout.setEnabled(false);
        load();
    }

    @Override
    public void onExperterClickListener(View v, int position) {
        if (isRecommed){
            mDataMap.put("point", experterList.get(position).getUid());
            mDataMap.put("type", experterList.get(position).getYewuzhuanchang());
            mDataMap.put("pointNickName", "");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("确定把问题提交给这位专家吗?")
                    .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            upToServer();
                        }
                    });
            builder.setNegativeButton("不了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }else {
            getActivity().startActivity(new Intent(getActivity(), AskActivity.class).putExtra("isExperter",true).putExtra("uid",experterList.get(position).getUid())
                    .putExtra("type",experterList.get(position).getYewuzhuanchang()).putExtra("name",experterList.get(position).getName()));
        }

    }

    /**
     * 提交给服务器
     */
    private void upToServer() {
        showLoadingDialog(R.string.hint_message);
        PostRequest request = OkGo.post(Constant.ADD_ISSUE);
        request.isMultipart(true);
        request.params(mDataMap);
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
                if ("1".equals(JsonUtils.getServerResult(s))){
                    dismissLoadingDialog();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText("您的问题已成功提交,请耐心等候回答!")
                            .setConfirmText("好的!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    //回到首页
                                    startActivity(new Intent(getActivity(), AnswerList_Activity.class));
                                    getActivity().finish();
                                }
                            })
                            .show();
                }else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setContentText("提交失败:"+JsonUtils.getServerMsg(s))
                            .setConfirmText("好的!")
                            .show();
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtils.showShort(getActivity(),e.toString());
            }
        });
    }

    /**
     * 显示进度对话框
     *
     * @param message {@link String} 消息文本
     */
    protected void showLoadingDialog(int message) {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(getActivity());
        }
        loadingDialog.setMessage(getString(message));
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    /**
     * 取消进度对话框
     */
    protected void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}

