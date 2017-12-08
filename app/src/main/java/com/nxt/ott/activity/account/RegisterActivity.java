package com.nxt.ott.activity.account;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

/**
 * Created by iwon on 2016/6/19 0026.
 */
public class RegisterActivity extends BaseTitleActivity{
    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this,getString(R.string.register));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }
}
