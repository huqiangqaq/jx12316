package com.nxt.ott.activity.titlebottom;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

public class AboutUsActivity extends BaseTitleActivity{


    @Override
    protected void initView() {
         application.addActivity(this);
        initTopbar(this,getString(R.string.aboutus));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about_us;
    }

}