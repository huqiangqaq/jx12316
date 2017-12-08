package com.nxt.ott.activity.expert;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ExperterAdapter;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.City;
import com.nxt.ott.domain.Country;
import com.nxt.ott.domain.ExpertType;
import com.nxt.ott.domain.Experter;
import com.nxt.ott.domain.Province;
import com.nxt.ott.util.LogUtils;
import com.nxt.ott.util.ToastUtils;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.ui.widget.CustomSpinner;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

//import android.support.v7.widget.SearchView;

/**
 * Update by iwon on 2016/6/19. 19:39
 */
public class ExpertSh1Activity extends BaseTitleActivity implements AdapterView.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    private ZDataTask zDataTask;
    private String url, experter, expertname, experttypename;
    private CustomSpinner spCityChoose, spCountryChoose, spExpertTypeChoose;
    private List<String> listProvinceName = new ArrayList<String>();
    private List<String> listCityName = new ArrayList<String>();
    private List<String> listCountryName = new ArrayList<String>();
    private List<String> listExpertTypeName = new ArrayList<String>();
    private List<Province> provinceList = new ArrayList<Province>();
    private List<City> cityList = new ArrayList<City>();
    private List<Country> countryList = new ArrayList<Country>();
    private List<ExpertType> expertTypeList = new ArrayList<ExpertType>();
    private ArrayAdapter<String> city_adapter, county_adapter, experter_adapter;
    public int TAG, code, areatype;
    private ListView mlistview;
    private List<Experter> experterList = new ArrayList<>();
    private SwipeRefreshLayout refreshlayout;
    private int lastItem, index = 0;
    private int page = 1, pagesize = 30;
    private View footerview;
    private ExperterAdapter experterAdapter;
    private SearchView searchView;
    private Map<String, String> map = new HashMap<>();
    private RelativeLayout iv_experter_back;

    @Override
    protected void initView() {
        initTopbar(this, getString(R.string.ask_expert));
        zDataTask = application.getZDataTask();

        footerview = LayoutInflater.from(this).inflate(R.layout.layout_foot, null);
        mlistview = (ListView) findViewById(R.id.listview_expert);
        mlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            //AbsListView view 这个view对象就是listview
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        mlistview.addFooterView(footerview);
                        load();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
        refreshlayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //设置刷新时动画的颜色，可以设置4个
        refreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        refreshlayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        spCityChoose = (CustomSpinner) findViewById(R.id.sp_city);
        spCountryChoose = (CustomSpinner) findViewById(R.id.sp_country);
        spExpertTypeChoose = (CustomSpinner) findViewById(R.id.sp_expert_type);

        spCityChoose.setOnItemSelectedListener(this);
        spCountryChoose.setOnItemSelectedListener(this);
        spExpertTypeChoose.setOnItemSelectedListener(this);

        try {
            experter = URLEncoder.encode(getString(R.string.experter), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //先加载专家类型
        url = String.format(Constant.EXPERT_TYPE_URL, "");
        TAG = 4;
        refresh();
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setQueryHint("请输入专家姓名搜索");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                map.put("usertype", "专家");
                map.put("stext", query);
                getExpert();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getExpert() {
        OkHttpUtils.post().url(Constant.SEARCH_EXPERTER)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response != null) {
                            JSONObject js = null;
                            try {
                                js = new JSONObject(response);
                                experterList.clear();
                                experterList = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                                }.getType());
                                experterAdapter = new ExperterAdapter(ExpertSh1Activity.this,experterList);
                                mlistview.setAdapter(experterAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void load() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(1, 1500);
            }
        });

    }

    private void refresh() {
        refreshlayout.post(new Runnable() {
            @Override
            public void run() {
                refreshlayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0, 500);

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                page = 1;
                if (experterList != null) experterList.clear();
            } else {
                page++;
            }
            getdata();
            super.handleMessage(msg);
        }
    };

    private void getdata() {

        if (expertname != null && code == 0) {
            if ("专   业".equals(expertname)) {
                url = String.format(Constant.EXPERTER_LIST_URL, page, pagesize, experter);
            } else {

                try {
                    experttypename = URLEncoder.encode(expertname, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url = String.format(Constant.EXPERT_TYPE_RESULT_URL_1, page, pagesize, experter, experttypename);
            }

        } else if (expertname != null && code != 0) {
            if ("专   业".equals(expertname)) {
                url = String.format(Constant.EXPERT_TYPE_RESULT_URL_2, page, pagesize, experter, code, areatype);
            } else {
                try {
                    experttypename = URLEncoder.encode(expertname, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url = String.format(Constant.EXPERT_TYPE_RESULT_URL, page, pagesize, experter, experttypename, code, areatype);
            }
        }

        if (CommonUtils.isNetWorkConnected(this)) {
            zDataTask.get(url, null, null, this);
            Log.e("Expert-141", "url-------------->" + url);
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_experts_search;
    }

    private void getData() {
        if (CommonUtils.isNetWorkConnected(this)) {
            zDataTask.get(url, null, null, this);
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }


    @Override
    public void onRequestResult(String string) {
        Log.e("type-160", "result----------->" + string);
        if (refreshlayout.isRefreshing()) refreshlayout.setRefreshing(false);
        if (TAG == 2) {
            cityList = new Gson().fromJson(string, new TypeToken<List<City>>() {
            }.getType());
            for (int i = 0; i < cityList.size(); i++) {
                listCityName.add(cityList.get(i).getName());
            }
            listCityName.remove(0);
            listCityName.add(0, "省   市");
            city_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCityName);
            city_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCityChoose.setAdapter(city_adapter);
            TAG = 3;
            url = String.format(Constant.COUNTRY_URL, 360100);
            getData();
        } else if (TAG == 3) {
            countryList = new Gson().fromJson(string, new TypeToken<List<Country>>() {
            }.getType());
            for (int i = 0; i < countryList.size(); i++) {
                listCountryName.add(countryList.get(i).getName());
            }
            listCountryName.remove(0);
            listCountryName.add(0, "县   区");
            county_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCountryName);
            county_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCountryChoose.setAdapter(county_adapter);
            TAG = 5;
            refresh();
        } else if (TAG == 4) {
            expertTypeList = new Gson().fromJson(string, new TypeToken<List<ExpertType>>() {
            }.getType());
            for (int i = 0; i < expertTypeList.size(); i++) {
                listExpertTypeName.add(expertTypeList.get(i).getName());
            }
            listExpertTypeName.remove(0);
            listExpertTypeName.add(0, "专   业");
            experter_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listExpertTypeName);
            experter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spExpertTypeChoose.setAdapter(experter_adapter);


            TAG = 2;
            url = Constant.CITY_URL;
            Log.e("out-193", "url--------------->" + url);
            getData();
        } else if (TAG == 5 && !TextUtils.isEmpty(string)) {
            try {
                JSONObject js = new JSONObject(string);
                if (page == 1) {
                    experterList = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                    }.getType());
                    experterAdapter = new ExperterAdapter(this, experterList);
                    mlistview.setAdapter(experterAdapter);
                } else {
                    List<Experter> addlist = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                    }.getType());
                    if (addlist.size() > 0) {
                        experterList.addAll(addlist);
                        experterAdapter.notifyDataSetChanged();
                    } else {
                        ZToastUtils.showShort(ExpertSh1Activity.this, R.string.expert_is_over);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ZToastUtils.showShort(ExpertSh1Activity.this, R.string.net_error);
        }
        super.onRequestResult(string);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_city:
                countryList.clear();
                listCountryName.clear();
                if (position != 0) {
                    areatype = 2;
                    code = cityList.get(position).getCode();
                    url = String.format(Constant.COUNTRY_URL, code);
                    TAG = 3;
                    getData();
                } else {
                    spCountryChoose.setAdapter(null);
                }
                break;
            case R.id.sp_country:
                if ("请选择地市".equals(listCityName)) {
                    Log.e("sh1-276", "cityname-------------->" + listCityName);
                    ToastUtils.showShort(this, "请先选择一个地市");
                } else {
                    if (position != 0) {
                        areatype = 3;
                        code = countryList.get(position).getCode();
                        TAG = 5;
                        refresh();
                    }
                }
                break;
            case R.id.sp_expert_type:
                LogUtils.i("sssss", "expert sp selected----------------->" + experter_adapter.getCount());
                expertname = listExpertTypeName.get(position);
//                TAG = 5;
                refresh();
                break;
                default:
                    break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onRefresh() {
        page = 1;
        getdata();
    }

    @Override
    public void onStart() {
        super.onStart();
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
}
