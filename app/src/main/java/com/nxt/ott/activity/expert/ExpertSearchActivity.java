package com.nxt.ott.activity.expert;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.R;
import com.nxt.ott.adapter.ExperterAdapter;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.Experter;
import com.nxt.ott.Constant;
import com.nxt.ott.util.ToastUtils;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Updated by iwon on 2016/6/19. 19:25
 */
public class ExpertSearchActivity extends BaseTitleActivity{
    private SearchView mSearchView;
    private Spinner mSpinner;
    private ListView mListView;

    private List<Experter> expertList=new ArrayList<>();
    private ExperterAdapter experterAdapter;
    private ZDataTask zDataTask;

    private String searchUrl;
    private String searchText;
    private String url;


    @Override
    protected int getLayout() {
        return R.layout.activity_expert_search;
    }

    @Override
    protected void initView() {
        initTopbar(this, getString(R.string.search_expert));
        zDataTask = application.getZDataTask();

        mSearchView=(SearchView)findViewById(R.id.search_expert);
        mSpinner=(Spinner)findViewById(R.id.expert_type);
        mListView=(ListView)findViewById(R.id.lv_expert);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    searchUrl = Constant.SEARCH_NAME_URL;
                } else {
                    searchUrl = Constant.SEARCH_TYPE_URL;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && newText.length() > 0) {
                    searchText = newText;
                    try {
                        String s = URLEncoder.encode(searchText, "utf-8");
                        url = String.format(searchUrl, s);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    getSearched();
                }else {
                    mListView.setAdapter(null);
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                //清空
                if (searchText != null) {
                    try {
                        String s = URLEncoder.encode(searchText, "utf-8");
                        url = String.format(searchUrl, s);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    getSearched();
                } else {
                    ToastUtils.showShort(ExpertSearchActivity.this, "请输入搜索类型");
                }
                return true;
            }
        });
    }

    private void getSearched(){
        if (CommonUtils.isNetWorkConnected(this)) {
            zDataTask.get(url, null, null, this);
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }

    @Override
    public void onRequestResult(String string) {

        if (!TextUtils.isEmpty(string)) {
            try {
                JSONObject js = new JSONObject(string);

                expertList = new Gson().fromJson(js.getString("Rows"), new TypeToken<List<Experter>>() {
                }.getType());
                experterAdapter=new ExperterAdapter(this, expertList);
                if(expertList.size()>0) {
                    mListView.setAdapter(experterAdapter);
                }else{
                    ZToastUtils.showShort(this, R.string.no_data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            ZToastUtils.showShort(this, R.string.no_data);
        }
        toggleInput();
        super.onRequestResult(string);
    }
}
