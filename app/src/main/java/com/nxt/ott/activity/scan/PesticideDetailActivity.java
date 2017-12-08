package com.nxt.ott.activity.scan;

import android.text.TextUtils;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.Pesticide;

/**
 * Updated by iwon on 2016/6/22 11:19.
 */
public class PesticideDetailActivity extends BaseTitleActivity{
    private TextView tvtitle,tvdetail;
    private Pesticide agHelper;


    @Override
    protected void initView() {
        initTopbar(this,getString(R.string.agriculture_detail));
        agHelper= (Pesticide) getIntent().getSerializableExtra("data");
        tvtitle= (TextView) findViewById(R.id.tv_pesticide_title);
        tvdetail= (TextView) findViewById(R.id.tv_pesticide_detail);
        if(!TextUtils.isEmpty(agHelper.getCompanyname())) tvtitle.setText(agHelper.getCompanyname());
        tvdetail.setText("登记证号:\t"+agHelper.getRegistrationnumber()+"\n\n"+agHelper.getMedication()+"\n\n"+"剂型:\t"+agHelper.getFormulation()+
                "\n\n"+"毒性:\t"+agHelper.getToxicity()+"\n\n"+"成分:\t"+agHelper.getActiveingredients()+"\n\n"+"作物:"+agHelper.getRegistrationname()+
                "\n\n"+"预防:\t"+agHelper.getPreventname()+"\n\n"+"使用方式:\t"+agHelper.getApplicationmethod()+"\n\n"+"有效期:\t"+agHelper.getValid()+"至"+agHelper.getOther1());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_pesticide_detail;
    }
}
