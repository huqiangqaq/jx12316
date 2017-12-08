package com.nxt.ott.activity.doctor;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.nxt.ott.R;
import com.nxt.ott.adapter.DiseaseChooseAdapter;
import com.nxt.ott.base.BaseDiseaseActivity;
import com.nxt.ott.domain.DiseaseFather;
import com.nxt.ott.domain.DiseaseType;
import com.nxt.ott.Constant;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.ui.widget.CustomListView;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZPreferenceUtils;
import com.nxt.zyl.util.ZToastUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhangyonglu on 2016/1/29 0029.
 */
public class DiseaseChooseActivity extends BaseDiseaseActivity {
    private RadioGroup radioGroup;
    private Spinner diseasesp;
    private String[] splist;
    private ArrayAdapter<String> diaseaseadapter;
    private CustomListView listView;
    private List<String> testList = new ArrayList<>();
    private DiseaseType diseaseType;
    private ZDataTask zDataTask;
    private List<DiseaseFather> diseaseFatherList = new ArrayList<>();
    private List<String> diseaseKeysList = new ArrayList<>();
    private String diseaseitem, type;

    @Override
    protected void initView() {
        initTopbar(this, getString(R.string.disease_choose));
        ZPreferenceUtils.setPrefString(Constant.DISEASE_CONTENT, "");//清空症状选择的数据
        zDataTask = application.getZDataTask();
        diseaseType = (DiseaseType) getIntent().getSerializableExtra("data");
        type = getIntent().getStringExtra("type");
        diseaseitem = getIntent().getStringExtra("selection");
        ZPreferenceUtils.setPrefString(Constant.DISEASE_SELECTIONS, diseaseitem);
        listView = (CustomListView) findViewById(R.id.listview_diseate_choose);
        rbtstepone.setChecked(true);
        rbtsteptextone.setChecked(true);
        rbtsteptwo.setChecked(true);
        rbtsteptexttwo.setChecked(true);
        findViewById(R.id.btn_next).setOnClickListener(this);
        //settestdata();
        showloading();
        if (CommonUtils.isNetWorkConnected(this)) {
            showloading();
            final String url;
            try {
                url = String.format(Constant.DISEATE_CHOOSE_URL, diseaseType.getSymptomDataTableName(),
                        URLEncoder.encode(diseaseType.getSections(), "utf-8"));
                System.out.println("disease choose url----------->" + url);
                zDataTask.get(url, null, null, this);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }


    }


    @Override
    protected int getLayout() {
        return R.layout.activity_disease_choose;
    }


    @Override
    public void onClick(View v) {
        String content = ZPreferenceUtils.getPrefString(Constant.DISEASE_CONTENT, "");
        if (v.getId() == R.id.btn_next) {
            System.out.println("content-------->" + content);
            if (TextUtils.isEmpty(content)) {
                ZToastUtils.showShort(this, R.string.please_choose_diease);
            } else {
                startActivity(new Intent(this, DiseasePictureActivity.class).putExtra("data", diseaseType));
            }
        }
        super.onClick(v);
    }

    @Override
    protected void onResume() {
        // ZPreferenceUtils.setPrefString(Constant.DISEASE_CONTENT,"");
        super.onResume();
    }

    @Override
    public void onRequestResult(String string) {
        dismissloading();
        try {
            String result = URLDecoder.decode(string, "gbk");
            if (!TextUtils.isEmpty(result) && result.contains("success")) {
                JSONObject js = new JSONObject(result);
                String infors = js.getString("infos");
                JSONObject inforjson = new JSONObject(infors);
                Iterator<String> it = inforjson.keys();
                while (it.hasNext()) {
                    diseaseKeysList.add(it.next());
                }
                listView.setAdapter(new DiseaseChooseAdapter(this, diseaseKeysList, inforjson));
            }
            System.out.println("string------------>" + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onRequestResult(string);
    }

    private void settestdata() {
        for (int i = 0; i < 8; i++) {
            testList.add("item" + i);
        }
        for (int i = 0; i < 15; i++) {
            DiseaseFather diseaseFather = new DiseaseFather();
            diseaseFather.setFathername("type" + i);
            diseaseFather.setContent(testList);
            diseaseFatherList.add(diseaseFather);
        }
    }
}
