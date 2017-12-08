package com.nxt.ott.activity.scan;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.nxt.ott.R;
import com.nxt.ott.activity.WebActivity;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.Constant;
import com.nxt.zyl.util.ZToastUtils;

/**
 * Updated by iwon on 2016/6/22 11:25.
 */
public class WriteEwmActivity extends BaseTitleActivity{
    private EditText searchet;

    @Override
    protected void initView() {
        application.addActivity(this);

        initTopbar(this,getString(R.string.production_scan));
        findViewById(R.id.btn_commint).setOnClickListener(this);
        searchet= (EditText) findViewById(R.id.et_search);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_write_ewm;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_commint:
                if(TextUtils.isEmpty(searchet.getText())){
                    ZToastUtils.showShort(this,R.string.scan_content_isnot_null);
                    return;
                }
                startActivity(new Intent(this,WebActivity.class).putExtra("title",getString(R.string.search_result)).putExtra("url",String.format(Constant.JIANGXI_SCAN_URL,searchet.getText().toString())));
                break;
                default:
                    break;
        }
        super.onClick(v);
    }
}
