package com.nxt.ott.activity.convenience;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.activity.WebActivity;
import com.nxt.ott.base.BaseZoomTitleActivity;

/**
 * Created by zhangyonglu on 2016/3/9 0009.
 * Updated by iwon on 2016/6/24 12:02.
 */
public class ConveniencePersonActivity extends BaseZoomTitleActivity {
    private String url, title;
    private View contentview;

    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this, getString(R.string.yns));
        int headerheight = BitmapFactory.decodeResource(getResources(), R.mipmap.convenien_person_top_bg)
                .getHeight();
        headerimg.setImageResource(R.mipmap.convenien_person_top_bg);
        scrollView.setHeaderViewSize(screenwidth, headerheight);
        contentview = LayoutInflater.from(this).inflate(R.layout.activity_convenience_person, null);
        parentlayout.addView(contentview);
        contentview.findViewById(R.id.layout_mall).setOnClickListener(this);
        contentview.findViewById(R.id.layout_recharge).setOnClickListener(this);
        contentview.findViewById(R.id.layout_ticket).setOnClickListener(this);
        contentview.findViewById(R.id.layout_waterorele).setOnClickListener(this);
        contentview.findViewById(R.id.layout_doctor).setOnClickListener(this);
        contentview.findViewById(R.id.layout_break_rule).setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.common_scrollview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_mall://便民商城
                startActivity(new Intent(this, MallChooseActivity.class));
                Log.e("50", "restu--------------------->" + "1111111111111111111111111");
                break;
            case R.id.layout_recharge://话费充值
                startActivity(new Intent(this, PhoneBillActivity.class));
                break;

            case R.id.layout_ticket://车票购买
                url = Constant.SERVICE_RAILWAY_TICKET_URL;
                title = getString(R.string.ticket);
                startActivity(new Intent(this, WebActivity.class).putExtra("title", title).putExtra("url", url));
                break;

            case R.id.layout_waterorele://交水电费
                startActivity(new Intent(this, WaterAndeElectricityActivity.class));
                break;

            case R.id.layout_doctor://挂号就诊
                url = Constant.SERVICE_SEE_DOCTOR_URL;
                title = getString(R.string.see_doctor);
                startActivity(new Intent(this, WebActivity.class).putExtra("title", title).putExtra("url", url));
                break;

            case R.id.layout_break_rule://违章查询
                url = Constant.SERVICE_BREAK_RULES_SEARCH_URL;
                title = getString(R.string.break_rule);
                startActivity(new Intent(this, WebActivity.class).putExtra("title", title).putExtra("url", url));
                break;

        }
        super.onClick(v);
    }

}
