package com.nxt.ott.fragment;

import android.content.Intent;
import android.view.View;

import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.activity.WebActivity;
import com.nxt.ott.activity.wisdom.ThreePlatformActivity;
import com.nxt.ott.activity.wisdom.TwoCenterActivity;
import com.nxt.zyl.ui.fragment.ZBaseFragment;

/**
 * Created by huqiang on 2017/7/11 8:56.
 */

public class ZhnyFragment extends ZBaseFragment implements View.OnClickListener {


    @Override
    protected void initView(View view) {
        view.findViewById(R.id.layout_wisdom_cloud).setOnClickListener(this);
        view.findViewById(R.id.layout_wisdom_center).setOnClickListener(this);
        view.findViewById(R.id.layout_wisdom_platform).setOnClickListener(this);
        view.findViewById(R.id.layout_wisdom_system).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wisdom_agriculture;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_wisdom_cloud:
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title", getString(R.string.wisdom_cloud)).putExtra("url", Constant.WISDOM_CLOUD_URL));
                break;
            case R.id.layout_wisdom_center:
                startActivity(new Intent(getActivity(), TwoCenterActivity.class));
                break;
            case R.id.layout_wisdom_platform:
                startActivity(new Intent(getActivity(), ThreePlatformActivity.class));
                break;
            case R.id.layout_wisdom_system:
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title", getString(R.string.wisdom_system)).putExtra("url", Constant.WISDOM_SYSTEM_N_URL));
                break;
            default:
                break;
        }
    }
}
