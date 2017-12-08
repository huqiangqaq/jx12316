package com.hyphenate.easeui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.easeui.R;

/**
 * Created by Administrator on 2016/4/19.
 */
public class EaseContactListActivity extends FragmentActivity{
    private EaseContactListFragment easeContactListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ease_activity_contact_list);
        easeContactListFragment=new EaseContactListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,easeContactListFragment).commit();
    }
}
