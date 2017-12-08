package com.nxt.ott.activity.scan;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.zyl.ui.notification.Effects;
import com.nxt.zyl.ui.notification.NiftyNotificationView;

import java.util.ArrayList;
import java.util.List;

/**
 * Update by iwon on 2016/6/22 11:19.
 */
public class AgriculturalCapitalActivity extends BaseTitleActivity {
    private RadioButton pesticidert, fertilizerrt, seedrt, microorganismrt;
    private RadioButton rtone, rttwo, rtthree, rtfour;
    private ImageView arrowimg;
    private Matrix matrix;
    private TranslateAnimation animation;
    private RadioGroup parentlayout;
    private ImageView imgone, imgtwo, imgthree, imgfour;
    private List<ImageView> imglist = new ArrayList<>();
    private int parentindex = 0, itemindex = 0;
    private EditText searchet;
    private Effects effects = Effects.standard;

    @Override
    protected void initView() {
        initTopbar(this, getString(R.string.pesticide_search));
        DisplayMetrics dm = new DisplayMetrics();
        searchet = (EditText) findViewById(R.id.et_search);
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        pesticidert = (RadioButton) findViewById(R.id.rt_pesticide);
        fertilizerrt = (RadioButton) findViewById(R.id.rt_fertilizer);
        seedrt = (RadioButton) findViewById(R.id.rt_seed);
        microorganismrt = (RadioButton) findViewById(R.id.rt_microorganism);
        rtone = (RadioButton) findViewById(R.id.rbt_one);
        rttwo = (RadioButton) findViewById(R.id.rbt_two);
        rtthree = (RadioButton) findViewById(R.id.rbt_three);
        rtfour = (RadioButton) findViewById(R.id.rbt_four);

        pesticidert.setOnClickListener(this);
        fertilizerrt.setOnClickListener(this);
        seedrt.setOnClickListener(this);
        microorganismrt.setOnClickListener(this);

        rtone.setOnClickListener(this);
        rttwo.setOnClickListener(this);
        rtthree.setOnClickListener(this);
        rtfour.setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);

        imgone = (ImageView) findViewById(R.id.img_one);
        imgtwo = (ImageView) findViewById(R.id.img_two);
        imgthree = (ImageView) findViewById(R.id.img_three);
        imgfour = (ImageView) findViewById(R.id.img_four);

        imglist.add(imgone);
        imglist.add(imgtwo);
        imglist.add(imgthree);
        imglist.add(imgfour);
        setImgVisbile(0);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_agricultural_capital;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rt_pesticide://农药
                setImgVisbile(0);
                setitemtext(0);
                parentindex = 0;


                break;
            case R.id.rt_fertilizer://化肥
                setImgVisbile(1);
                setitemtext(1);
                parentindex = 1;
                //ZToastUtils.showShort(this,R.string.now_support_pesticide_search);


                break;
            case R.id.rt_seed://种子
                setImgVisbile(2);
                setitemtext(2);
                parentindex = 2;

                //ZToastUtils.showShort(this,R.string.now_support_pesticide_search);


                break;
            case R.id.rt_microorganism://微生物
                setImgVisbile(3);
                setitemtext(3);
                parentindex = 3;

                //ZToastUtils.showShort(this,R.string.now_support_pesticide_search);

                break;
            case R.id.rbt_one://0:企业
                itemindex = 0;
                switch (parentindex) {
                    case 0:
                        searchet.setHint(R.string.please_write_company);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                break;
            case R.id.rbt_two://0 登记
                itemindex = 1;
                switch (parentindex) {
                    case 0:
                        searchet.setHint(R.string.please_write_cardnumber);

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                break;
            case R.id.rbt_three://只有农药有该项
                //ZToastUtils.showShort(this,R.string.now_support_ingredient_search);

                itemindex = 2;

                break;
            case R.id.rbt_four://0 作物
                // ZToastUtils.showShort(this,R.string.now_support_crop_search);

                switch (parentindex) {
                    case 0:
                        itemindex = 3;

                        break;
                    case 1:
                        itemindex = 2;
                        break;
                    case 2:
                        itemindex = 2;

                        break;
                    case 3:
                        itemindex = 2;

                        break;
                }

                break;
            case R.id.btn_search:
                if (TextUtils.isEmpty(searchet.getText())) {
                    // ZToastUtils.showShort(this,R.string.search_content_is_not_null);
                    NiftyNotificationView.build(this, getString(R.string.search_content_is_not_null), effects, R.id.mLyout).show();

                    return;
                }
                startActivity(new Intent(this, PesticideListActivity.class).putExtra("parentindex", parentindex).putExtra("itemindex", itemindex).putExtra("typename", searchet.getText().toString()));
                break;
        }
        super.onClick(v);
    }

    private void setitemtext(int index) {
        switch (index) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    private void setImgVisbile(int index) {
        for (int i = 0; i < imglist.size(); i++) {
            if (i == index) {
                imglist.get(index).setVisibility(View.VISIBLE);
            } else {
                imglist.get(i).setVisibility(View.GONE);

            }
        }
    }
}