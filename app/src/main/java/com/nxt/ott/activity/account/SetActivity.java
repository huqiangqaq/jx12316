package com.nxt.ott.activity.account;import android.support.v4.app.Fragment;import com.nxt.ott.R;import com.nxt.ott.base.BaseTitleActivity;/** * Created by cyjia on 2016/6/23. */public class SetActivity extends BaseTitleActivity {//    private SettingsFragment settingsFragment;    @Override    protected void initView() {        application.addActivity(this);        initTopbar(this, "设置");//        settingsFragment = new SettingsFragment();//        setFragment(settingsFragment);    }    @Override    protected int getLayout() {        return R.layout.activity_set;    }    private void setFragment(Fragment fragment) {        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();    }}