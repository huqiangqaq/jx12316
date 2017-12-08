package com.nxt.ott.activity.appstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.nxt.ott.MyApplication;
import com.nxt.ott.R;

/**
 * Created by Administrator on 2016/3/23.
 */
public class AppstoreActivity extends FragmentActivity {

    private FragmentManager fragmentManager;
    private Fragment storefragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_store);
        MyApplication.getInstance().addActivity(this);
        initview();
    }

    private void initview() {
        fragmentManager=getSupportFragmentManager();
        //storefragment=new StoreFragment();
        fragmentManager.beginTransaction().replace(R.id.layout_content,storefragment).commit();
    }
}
