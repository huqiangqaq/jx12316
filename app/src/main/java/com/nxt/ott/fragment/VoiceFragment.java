package com.nxt.ott.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.nxt.ott.R;
import com.nxt.zyl.ui.fragment.ZBaseFragment;

/**
 * Created by zhangyonglu on 2016/2/25 0025.
 */
public class VoiceFragment extends ZBaseFragment{
    @Override
    protected void initView(View view) {
           view.findViewById(R.id.img_12316).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_experter;
    }

    @Override
    public void onClick(View v) {
        Uri uri = Uri.parse("tel:" + "12316");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(uri);
        startActivity(intent);
        super.onClick(v);
    }
}
