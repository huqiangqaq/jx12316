package com.nxt.ott.activity.expert;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import com.nxt.ott.R;
import com.nxt.ott.activity.doctor.AgricultureDoctorActivity;
import com.nxt.ott.base.BaseZoomTitleActivity;

/**
 * Updated by iwon on 2016/6/19 0027.
 */
public class ExpertAskActivity extends BaseZoomTitleActivity {
    private View contentview;

    @Override
    protected void initView() {
        application.addActivity(this);

        initTopbar(this, getString(R.string.experter_ask));
        int headerheight = BitmapFactory.decodeResource(getResources(), R.mipmap.experter_top_bg)
                .getHeight();
        headerimg.setImageResource(R.mipmap.experter_top_bg);
        scrollView.setHeaderViewSize(screenwidth, headerheight);
        contentview = LayoutInflater.from(this).inflate(R.layout.activity_experter_ask, null);
        parentlayout.addView(contentview);
        contentview.findViewById(R.id.layout_hot_line).setOnClickListener(this);
        contentview.findViewById(R.id.layout_experter_ask).setOnClickListener(this);
        contentview.findViewById(R.id.layout_agriculture_doctor).setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.common_scrollview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_hot_line:
                Uri uri = Uri.parse("tel:" + "12316");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                startActivity(intent);
                break;

            case R.id.layout_agriculture_doctor://农大夫
                startActivity(new Intent(this, AgricultureDoctorActivity.class));
                break;
            case R.id.layout_experter_ask://专家咨询
                startActivity(new Intent(this, ExperterListActivity.class));
                break;
        }
        super.onClick(v);
    }
}

