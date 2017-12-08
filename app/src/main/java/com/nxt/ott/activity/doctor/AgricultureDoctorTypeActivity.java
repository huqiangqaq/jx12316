package com.nxt.ott.activity.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseDiseaseActivity;
import com.nxt.ott.domain.DiseaseType;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.ui.widget.AVLoadingIndicatorView;
import com.nxt.zyl.util.ZToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangyonglu on 2016/3/10 0010.
 */
public class AgricultureDoctorTypeActivity extends BaseDiseaseActivity{
    private List<String> kindlist;
    private RelativeLayout kindlayout,diseaselayout;
    private String type,typeurl,kinditemstr,diseaseitemstr;
    private ZDataTask zDataTask;
    private TextView kindtext,diseasetext,poptypetext;
    private int currenoperate=0;
    private PopupWindow mpopupWindow;
    private View contentView;
    private ListView typelistview;
    private List<DiseaseType> diseaseTypeList;
    private List<String> diseaselist=new ArrayList<>();
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private DiseaseType diseaseType;
    private static int KINDREQUEST=0,DISEASEREQUEST=1;

    @Override
    protected void initView() {
        initTopbar(this,getString(R.string.diseate_type));
        zDataTask=application.getZDataTask();
        type=getIntent().getStringExtra("type");

        kindlayout= (RelativeLayout) findViewById(R.id.layout_kind_choose);
        diseaselayout= (RelativeLayout) findViewById(R.id.layout_disease_choose);
        kindtext= (TextView) findViewById(R.id.tv_kind_choose);
        diseasetext= (TextView) findViewById(R.id.tv_disease_choose);
        kindlayout.setOnClickListener(this);
        diseaselayout.setOnClickListener(this);
        kindlist= Arrays.asList(getResources().getStringArray(R.array.raise_livestock_list));
        findViewById(R.id.btn_next).setOnClickListener(this);
//        if(type.equals(getString(R.string.water_production))||type.equals(getString(R.string.agriculture_production))){
//            diseaselayout.setVisibility(View.GONE);//水产品和农产品直接诊断  没有诊断类型选项
//        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_agriculture_doctor_type;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_kind_choose:
                currenoperate=0;
                startActivityForResult(new Intent(this,KindChooseActivity.class).putExtra("type",type).putExtra("titletype",getString(R.string.kind_choose)),KINDREQUEST);
                break;
            case R.id.layout_disease_choose:
                currenoperate=1;
                if(!kindtext.getText().equals(getString(R.string.kind_choose))) {
                    startActivityForResult(new Intent(this, KindChooseActivity.class).putExtra("type",
                            kindtext.getText()).putExtra("titletype", getString(R.string.disease_choose)),DISEASEREQUEST);
                }else{
                    ZToastUtils.showShort(this, R.string.please_choose_kind);
                }
                break;
            case R.id.btn_next:
                    if(!kindtext.getText().equals(getString(R.string.kind_choose))&&!diseasetext.getText().equals(getString(R.string.diseate_type))){
                        startActivity(new Intent(this,DiseaseChooseActivity.class).putExtra("data",diseaseType)
                                .putExtra("type",type).putExtra("selection",diseasetext.getText()));
                    }else{
                        ZToastUtils.showShort(this, R.string.please_choose_diease_type);
                    }

                break;
        }
        super.onClick(v);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
                      case 0:
                           if(resultCode==0){
                               if(data!=null) {
                                   kindtext.setText(data.getStringExtra("data"));
                               }
                           }
                          break;
                      case 1:
                          if(resultCode==1){
                              if(data!=null) {
                                  diseaseType = (DiseaseType) data.getSerializableExtra("data");
                                  diseasetext.setText(diseaseType.getChineseName());
                              }
                          }
                          break;
                  }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy------------>");

        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate------------>");
        super.onCreate(savedInstanceState);
    }
}
