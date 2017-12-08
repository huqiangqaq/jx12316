package com.nxt.ott.activity;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

/**
 * Updated by iwon on 2016/6/19 20:46.
 */
public class DoingActivity extends BaseTitleActivity {
    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this,getString(R.string.caption_three));
        //测试分支
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_doing;
    }
}
