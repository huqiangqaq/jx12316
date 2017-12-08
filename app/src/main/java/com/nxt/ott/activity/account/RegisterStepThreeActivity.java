package com.nxt.ott.activity.account;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.Constant;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.ui.notification.Effects;
import com.nxt.zyl.ui.notification.NiftyNotificationView;
import com.nxt.zyl.ui.widget.loadingbutton.LoadingButton;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZPreferenceUtils;
import com.nxt.zyl.util.ZToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by iwon on 2016/6/19 0001.
 */
public class RegisterStepThreeActivity extends BaseTitleActivity {

    private EditText pwdet, repwdet, useret;
    private Effects effects = Effects.standard;
    private LoadingButton loadingButton;
    private ZDataTask zDataTask;
    private String name, phone, title, type;
    private boolean isforget;
    private Spinner sp_type;

    @Override
    protected void initView() {
        application.addActivity(this);
        zDataTask = application.getZDataTask();
        isforget = getIntent().getBooleanExtra("type", false);
        loadingButton = (LoadingButton) findViewById(R.id.btn_next);
        phone = getIntent().getStringExtra("phone");
        pwdet = (EditText) findViewById(R.id.et_pwd);
        useret = (EditText) findViewById(R.id.et_username);
        repwdet = (EditText) findViewById(R.id.et_re_pwd);
        type = ZPreferenceUtils.getPrefString(Constant.USER_TYPE, "");
        Log.e("three-55", "type--------------->" + type);
        if (TextUtils.isEmpty(type) || "农户".equals(type)) {
            try {
                type = URLEncoder.encode("农户", "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                type = URLEncoder.encode("专家", "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Log.e("three-69", "type1------------>" + type);
//        sp_type=(Spinner)findViewById(R.id.sp_user_type);
//        final String[] types=getResources().getStringArray(R.array.user_type);
//        sp_type.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_item ,types));
//        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                TextView textView=(TextView)view;
//                textView.setTextColor(getResources().getColor(R.color.nav_text_color));
//                if(position!=0){
//                    try {
//                        type=URLEncoder.encode(parent.getItemAtPosition(position).toString(),"utf-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                //
//            }
//        });

        if (isforget) {
            useret.setVisibility(View.GONE);
            pwdet.setHint(R.string.new_pwd);
            title = getString(R.string.write_pwd);
//            sp_type.setVisibility(View.GONE);
        } else {
            title = getString(R.string.write_pwd_useer);

        }
        initTopbar(this, title);
        loadingButton.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_step_three;
    }

    @Override
    public void onClick(View v) {
        if (!isforget) {
            if (TextUtils.isEmpty(useret.getText())) {
                NiftyNotificationView.build(this, getString(R.string.name_isnot_null), effects, R.id.mLyout).show();
                return;
            }
        }
        if (TextUtils.isEmpty(pwdet.getText())) {
            NiftyNotificationView.build(this, getString(R.string.pwd_isnot_null), effects, R.id.mLyout).show();
            return;
        }
        if (TextUtils.isEmpty(repwdet.getText())) {
            NiftyNotificationView.build(this,getString(R.string.please_repwd), effects, R.id.mLyout).show();
            return;
        }
        if (!TextUtils.equals(repwdet.getText().toString(), pwdet.getText().toString())) {
            NiftyNotificationView.build(this, getString(R.string.pwd_write_wrong), effects, R.id.mLyout).show();
            return;
        }
//        if (TextUtils.isEmpty(type)) {
//            NiftyNotificationView.build(this, String.format("请选择用户类型", "获取注册码"), effects, R.id.mLyout).show();
//            return;
//        }
        try {
            name = URLEncoder.encode(useret.getText().toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        loadingButton.showLoading();
        // ZToastUtils.showShort(this,R.string.register_syccess);
        register();
        super.onClick(v);

    }

    private void register() {
        if (CommonUtils.isNetWorkConnected(this)) {
            Log.e("three-149", "isforget----------->" + isforget);
            if (isforget) {
                zDataTask.get(String.format(Constant.CHANGE_PWD_URL, phone, pwdet.getText().toString()), null, null, this);
            } else {
                zDataTask.get(String.format(Constant.REGISTER_URL, phone, pwdet.getText().toString(), name, type), null, null, this);
                Log.e("three-154", "url------------>" + String.format(Constant.REGISTER_URL, phone, pwdet.getText().toString(), name, type));
            }
            Log.e("three-156", "url------------>" + String.format(Constant.REGISTER_URL, phone, pwdet.getText().toString(), name, type));
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }

    @Override
    public void onRequestResult(String string) {
        Log.e("three-166", "result--------------->" + string);
        loadingButton.showButtonText();
        Response response = new Gson().fromJson(string, new TypeToken<Response>() {
        }.getType());

        if ("true".equals(response.success)) {
            if (!isforget) {
                ZToastUtils.showShort(this, R.string.register_success);
            } else {
                ZToastUtils.showShort(this, R.string.update_success);
            }
            startActivity(new Intent(this, MyLoginActivity.class));
            finish();
        } else {
            if (!isforget) {
                ZToastUtils.showShort(this, R.string.register_fail);
            } else {
                ZToastUtils.showShort(this, R.string.update_fail);
            }
        }
        super.onRequestResult(string);
    }

    /**
     * 登录接口返回数据
     */
    private class Response {
        String success;
        String msg;
        String data;
    }
}
