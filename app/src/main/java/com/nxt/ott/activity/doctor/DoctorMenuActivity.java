package com.nxt.ott.activity.doctor;

import android.content.Intent;
import android.view.View;

import com.nxt.ott.R;
import com.nxt.ott.activity.pest.PestListActivity;
import com.nxt.ott.base.BaseTitleActivity;

public class DoctorMenuActivity extends BaseTitleActivity {

    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this, "农大夫");
        findViewById(R.id.ll_zwzd).setOnClickListener(this);
        findViewById(R.id.ll_bch).setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_doctor_menu;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_zwzd:
                startActivity(new Intent(this,AgricultureDoctorActivity.class));
                break;
            case R.id.ll_bch:
                startActivity(new Intent(this, PestListActivity.class));
                break;
            default:
                break;
        }
        super.onClick(v);
    }
}
