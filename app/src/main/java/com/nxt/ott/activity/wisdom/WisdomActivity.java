package com.nxt.ott.activity.wisdom;

import android.content.Intent;
import android.view.View;

import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.activity.WebActivity;
import com.nxt.ott.base.BaseTitleActivity;

public class WisdomActivity extends BaseTitleActivity {


    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this,getString(R.string.wisdom));
        findViewById(R.id.layout_wisdom_cloud).setOnClickListener(this);
        findViewById(R.id.layout_wisdom_center).setOnClickListener(this);
        findViewById(R.id.layout_wisdom_platform).setOnClickListener(this);
        findViewById(R.id.layout_wisdom_system).setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_wisdom_agriculture;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layout_wisdom_cloud://一云
                startActivity(new Intent(this, WebActivity.class).putExtra("title", getString(R.string.wisdom_cloud)).putExtra("url", Constant.WISDOM_CLOUD_URL));
                break;
            case R.id.layout_wisdom_center://二中心
                startActivity(new Intent(this, TwoCenterActivity.class));
                break;
            case R.id.layout_wisdom_platform://三平台
                startActivity(new Intent(this, ThreePlatformActivity.class));
                break;
            case R.id.layout_wisdom_system://N系统
                startActivity(new Intent(this, WebActivity.class).putExtra("title", getString(R.string.wisdom_system)).putExtra("url", Constant.WISDOM_SYSTEM_N_URL));
                break;
            default:
                break;
        }
    }
}
