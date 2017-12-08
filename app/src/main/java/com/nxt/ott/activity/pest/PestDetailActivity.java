package com.nxt.ott.activity.pest;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.PestDetail;
import com.nxt.ott.util.LogUtils;
//import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xpeng on 2016/10/12.
 */

public class PestDetailActivity extends BaseTitleActivity {//implements XBanner.XBannerAdapter
    private static final String TAG = "PestDetailActivity";
    private PestDetail mPestDetail;

    private String nid;
//    private XBanner mBannerNet;
    private List<String> imgesUrl=new ArrayList<>();
    private TextView tvPestName,tvPestContent;

    @Override
    protected void initView() {
        initTopbar(this,getIntent().getStringExtra(Constant.BCH_PEST_DETAIL));
//        mBannerNet=(XBanner)findViewById(R.id.banner_1);
        tvPestName=(TextView)findViewById(R.id.tv_pest_name);
        tvPestContent=(TextView)findViewById(R.id.tv_pest_content);

        nid=getIntent().getStringExtra(Constant.BCH_PEST_ID);
        zDataTask.get(String.format(Constant.BCH_DETAIL_URL,nid),null,null,this);

        initBanner();
    }

    private void setData(PestDetail pestDetail){
        if(pestDetail.getTitle() != null){
            tvPestName.setText(pestDetail.getTitle());
        }
        if(pestDetail.getContent() == null||pestDetail.getContent().trim()==""){
            tvPestContent.setText("暂时没有内容介绍");
        }else {
            tvPestContent.setText(pestDetail.getContent());
        }


    }

    private void initBanner(){
        imgesUrl.add(getIntent().getStringExtra(Constant.BCH_PEST_IMG));
//        mBannerNet.setData(imgesUrl);
//
//        mBannerNet.setmAdapter(this);
    }

//    @Override
//    public void loadBanner(XBanner banner, View view, int position) {
//        Glide.with(this).load(imgesUrl.get(position)).into((ImageView) view);
//    }


    @Override
    protected int getLayout() {
        return R.layout.activity_pest_detail;
    }

    @Override
    public void onRequestResult(String string) {
        LogUtils.e(TAG,string);
        dismissLoadingDialog();
        mPestDetail=new Gson().fromJson(string,PestDetail.class);

        setData(mPestDetail);
    }
}
