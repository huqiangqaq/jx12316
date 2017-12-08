package com.nxt.ott.activity.account;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseLoadingTitleActivity;
import com.nxt.ott.Constant;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.ui.notification.Effects;
import com.nxt.zyl.ui.notification.NiftyNotificationView;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by iwon on 2016/6/19 .
 */
public class RegisterStepTwoActivity extends BaseLoadingTitleActivity{
    private EditText msget;
    private String smsCode;
    private String smsURL;
    private TextView msgtext;
    private ZDataTask zDataTask;
    private String phone;
    private Effects effects=Effects.standard;
    private boolean isforget;


    @Override

    protected void initView() {
        application.addActivity(this);
        initTopbar(this,getString(R.string.write_msg));
        isforget=getIntent().getBooleanExtra("type",false);
        findViewById(R.id.btn_next).setOnClickListener(this);
        msget= (EditText) findViewById(R.id.et_msg);
        msgtext= (TextView) findViewById(R.id.tv_re_getmsg);
        msgtext.setOnClickListener(this);
        phone=getIntent().getStringExtra("phone");
        zDataTask=application.getZDataTask();
        receiverMsg();

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_step_two;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_re_getmsg) {
            receiverMsg();
        }else {
            /**
             * 验证短信码是否输入正确
             */
            if (TextUtils.equals(smsCode, null) || TextUtils.isEmpty(msget.getText()) || !TextUtils.equals(msget.getText(), smsCode)) {
                NiftyNotificationView.build(this, getString(R.string.error_sms_code), effects, R.id.mLyout).show();

                return;

            }
            startActivity(new Intent(this,RegisterStepThreeActivity.class).putExtra("phone",phone).putExtra("type",isforget));
        }
        super.onClick(v);
    }
    private void receiverMsg() {
        if (CommonUtils.isNetWorkConnected(this)) {
            smsCode = CommonUtils.getRandomPSMSValidateCode();
            String content = String.format(getString(R.string.sms_content), smsCode);
            Log.e("Register-82","code----------------->" + content);
            try {
                smsURL = String.format(Constant.URL_SMS,phone, URLEncoder.encode(content, "gbk"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (smsURL != null) {
                // showloading();
                getmsg();
            }else{
                msgtext.setClickable(true);
            }
        } else {
            ZToastUtils.showShort(this,R.string.net_error);
        }
    }

    private void getmsg() {

        zDataTask.get(smsURL,null,null,this);

    }

    /**
     * 解析短信验证码请求结果
     *
     * @param result 字符串
     */
    private void receiveSMS(String result) {
        Log.e("Two-110","smsCode---------------->" + result);
        if (TextUtils.equals(result, "100")) {
            msgtext.setClickable(false);
            ZToastUtils.showShort(this,getString(R.string.msg_success));
            ValueAnimator valueAnimator = ValueAnimator.ofInt(60, 0);
            valueAnimator.setDuration(60 * 1000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    msgtext.setTextColor(getResources().getColor(R.color.darkgray));
                    msgtext.setText(String.format(getString(R.string.re_get_sms), animation.getAnimatedValue()));
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    msgtext.setText(R.string.get_sms);
                    msgtext.setTextColor(getResources().getColor(R.color.agricultural_capital_green));
                    // 获取验证码按钮设置为可用
                    msgtext.setClickable(true);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            valueAnimator.start();
        } else {
            NiftyNotificationView.build(this, getString(R.string.error_get_data), effects, R.id.mLyout).show();
            msgtext.setClickable(true);
        }
    }

    @Override
    public void onRequestResult(String string) {
        receiveSMS(string);//处理短信验证码
        super.onRequestResult(string);
    }
}
