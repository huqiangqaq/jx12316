package com.nxt.ott.activity.account;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.sweetAlert.SweetAlertDialog;
import com.nxt.ott.util.OkhttpHelper;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.view.RxDialogCaptcha;
import com.nxt.zyl.ui.notification.Effects;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZPreferenceUtils;
import com.nxt.zyl.util.ZToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Updated by iwon on 2016/6/19 22:22.
 */
public class RegisterStepOneActivity extends BaseTitleActivity implements OkhttpHelper.MyCallBack {
    private EditText phoneet, etmsg;
    private AlertDialog registerDialog;
    private Effects effects = Effects.standard;
    private boolean isforgetpwd = false;
    private TextView btn_getmsg;
    private String smsCode, smsURL, phone, type, title;
    private EditText pwdet, repwdet;
    private String name = "";
    private Button btn_register;
    //注册
    private static final int REGISTER = 0;
    //忘记密码
    private static final int CHANGEPWD = 1;
    //发送验证码
    private static final int SENDSMS = 2;

    @Override
    protected void initView() {
        application.addActivity(this);

//        findViewById(R.id.btn_next).setOnClickListener(this);
        phoneet = (EditText) findViewById(R.id.et_phone);
        etmsg = (EditText) findViewById(R.id.et_msg);
        btn_getmsg = (TextView) findViewById(R.id.btn_getmsg);
        pwdet = (EditText) findViewById(R.id.et_pwd);
        repwdet = (EditText) findViewById(R.id.et_re_pwd);
        type = ZPreferenceUtils.getPrefString(Constant.USER_TYPE, "");
        isforgetpwd = getIntent().getBooleanExtra("type", false);
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

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_getmsg.setOnClickListener(this);
        if (isforgetpwd) {
            initTopbar(this, getString(R.string.updatepwd));
            btn_register.setText("提交");
            pwdet.setHint(R.string.new_pwd);
            title = getString(R.string.write_pwd);
//            sp_type.setVisibility(View.GONE);
        } else {
            title = getString(R.string.write_pwd_useer);
            initTopbar(this, getString(R.string.register));

        }

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_or_forgetpwd;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getmsg:
                if (TextUtils.isEmpty(phoneet.getText())) {
                    ZToastUtils.showShort(this, getString(R.string.phone_isnot_null));
//                    NiftyNotificationView.build(this, getString(R.string.phone_isnot_null), effects, R.id.mLyout).show();
                    return;
                } else if (!CommonUtils.isMobileNO(phoneet.getText().toString())) {
                    ZToastUtils.showShort(this, getString(R.string.please_write_rightphone));
//                    NiftyNotificationView.build(this, getString(R.string.please_write_rightphone), effects, R.id.mLyout).show();
                    return;
                }
                phone = phoneet.getText().toString();
                /**
                 * 验证图形码
                 */
                showCaptchaDialog();


                break;
            case R.id.btn_register:
                /**
                 * 验证短信码是否输入正确
                 */
                if (TextUtils.equals(smsCode, null) || TextUtils.isEmpty(etmsg.getText()) || !TextUtils.equals(etmsg.getText(), smsCode)) {
                    ZToastUtils.showShort(this, getString(R.string.error_sms_code));
                    return;
                }
                if (TextUtils.isEmpty(pwdet.getText())) {
                    ZToastUtils.showShort(this, getString(R.string.pwd_isnot_null));
                    return;
                }
                if (TextUtils.isEmpty(repwdet.getText())) {
                    ZToastUtils.showShort(this, getString(R.string.please_repwd));
                    return;
                }
                if (!TextUtils.equals(repwdet.getText().toString(), pwdet.getText().toString())) {
                    ZToastUtils.showShort(this, getString(R.string.pwd_write_wrong));
                    return;
                }
//        if (TextUtils.isEmpty(type)) {
//            NiftyNotificationView.build(this, String.format("请选择用户类型", "获取注册码"), effects, R.id.mLyout).show();
//            return;
//        }
                register();
                break;
        }

        super.onClick(v);
    }

    private void showCaptchaDialog() {
        final RxDialogCaptcha rxDialogCaptcha = new RxDialogCaptcha(this);
//                rxDialogCaptcha.getTitleView().setBackgroundResource(R.drawable.logo);
        rxDialogCaptcha.setTitle("图形验证码");
        rxDialogCaptcha.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rxDialogCaptcha.getEditText().getText().toString().equals(rxDialogCaptcha.getCode())){
                    invalidphone();
                }else {
                    ToastUtils.showShort(RegisterStepOneActivity.this,"验证码输入错误");
                }
                rxDialogCaptcha.cancel();
            }
        });
        rxDialogCaptcha.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogCaptcha.cancel();
            }
        });
        rxDialogCaptcha.show();
    }

    private void register() {
        if (CommonUtils.isNetWorkConnected(this)) {
            Log.e("three-149", "isforget----------->" + isforgetpwd);
            if (isforgetpwd) {
                OkhttpHelper.getInstance().Get(String.format(Constant.CHANGE_PWD_URL, phone, pwdet.getText().toString()), this, CHANGEPWD);
//                zDataTask.get(String.format(Constant.CHANGE_PWD_URL, phone, pwdet.getText().toString()), null, null, this);
            } else {
                OkhttpHelper.getInstance().Get(String.format(Constant.REGISTER_URL, phone, pwdet.getText().toString(), name, type), this, REGISTER);

                Log.e("three-154", "url------------>" + String.format(Constant.REGISTER_URL, phone, pwdet.getText().toString(), name, type));
            }
            Log.e("three-156", "url------------>" + String.format(Constant.REGISTER_URL, phone, pwdet.getText().toString(), name, type));
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }

    private void invalidphone() {
        if (CommonUtils.isNetWorkConnected(this)) {
            showloading();
            zDataTask.get(String.format(Constant.IS_REGISTER_URL, phoneet.getText().toString()), null, null, this);
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        //showRegisterialog();
        return;
    }

    @Override
    public void onRequestResult(String string) {
        Log.e("One-75", "result------------>" + string);
        dismissloading();
        if (string.contains(getString(R.string.now_unregiste))) {//未注册
            if (isforgetpwd) {
                ZToastUtils.showShort(this, getString(R.string.phone_is_noregistered));
//                NiftyNotificationView.build(this, getString(R.string.phone_is_noregistered), effects, R.id.mLyout).show();
            } else {
//                startActivity(new Intent(this, RegisterStepTwoActivity.class).putExtra("phone", phoneet.getText().toString()).putExtra("type",false));
//                finish();
                receiverMsg();
            }
        } else {//已注册
            if (isforgetpwd) {
//                startActivity(new Intent(this, RegisterStepTwoActivity.class).putExtra("phone", phoneet.getText().toString()).putExtra("type", true));
//                finish();
                receiverMsg();
            } else {
                ZToastUtils.showShort(this, getString(R.string.phone_is_registered));
//                NiftyNotificationView.build(this, getString(R.string.phone_is_registered), effects, R.id.mLyout).show();
            }
        }
        super.onRequestResult(string);
    }

    private void receiverMsg() {
        if (CommonUtils.isNetWorkConnected(this)) {
            smsCode = CommonUtils.getRandomPSMSValidateCode();
            String content = String.format(getString(R.string.sms_content), smsCode);
            Log.e("Register-82", "code----------------->" + content);
            try {
                smsURL = String.format(Constant.URL_SMS, phone, URLEncoder.encode(content, "gbk"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (smsURL != null) {
                // showloading();
                getmsg();
            } else {
                btn_getmsg.setClickable(true);
            }
        } else {
            ZToastUtils.showShort(this, R.string.net_error);
        }
    }

    public void getmsg() {
        OkhttpHelper.getInstance().Get(smsURL, this, SENDSMS);
//        OkHttpUtils.get().url(smsURL)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        receiveSMS(response);//处理短信验证码
//                    }
//                });
    }

    /**
     * 解析短信验证码请求结果
     *
     * @param result 字符串
     */
    private void receiveSMS(String result) {
        Log.e("Two-110", "smsCode---------------->" + result);
        if (TextUtils.equals(result, "100")) {
            btn_getmsg.setClickable(false);
            ZToastUtils.showShort(this, getString(R.string.msg_success));
            ValueAnimator valueAnimator = ValueAnimator.ofInt(60, 0);
            valueAnimator.setDuration(60 * 1000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    btn_getmsg.setTextColor(getResources().getColor(R.color.darkgray));
                    btn_getmsg.setText(String.format(getString(R.string.re_get_sms), animation.getAnimatedValue()));
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    btn_getmsg.setText(R.string.get_sms);
                    btn_getmsg.setTextColor(getResources().getColor(R.color.agricultural_capital_green));
                    // 获取验证码按钮设置为可用
                    btn_getmsg.setClickable(true);
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
            ZToastUtils.showShort(this, getString(R.string.error_get_data));
//            NiftyNotificationView.build(this, String.format(getString(R.string.error_get_data), "获取注册码"), effects, R.id.mLyout).show();
            btn_getmsg.setClickable(true);
        }
    }

    @Override
    public void onSucces(String response, int tag) {
        switch (tag) {
            case REGISTER:
                showResult(response);
                break;
            case CHANGEPWD:
                showResult(response);
                break;
            case SENDSMS:
                receiveSMS(response);//处理短信验证码
                break;

        }
    }

    @Override
    public void onFaile(Exception errorResponse, int tag) {

    }

    private void showResult(String response) {
        Response result = new Gson().fromJson(response, new TypeToken<Response>() {
        }.getType());

        if ("true".equals(result.success)) {
            if (!isforgetpwd) {
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("注册成功")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        })
                        .show();
            } else {
                new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("修改成功")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        })
                        .show();
            }
        } else {
            if (!isforgetpwd) {
                new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("注册失败,请重试!")
                        .show();
            } else {
                new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("修改失败,请重试!")
                        .show();
            }
        }
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
