package com.nxt.ott.activity;

import android.os.Bundle;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

public class TestActivity extends BaseTitleActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_or_forgetpwd;
    }
}
