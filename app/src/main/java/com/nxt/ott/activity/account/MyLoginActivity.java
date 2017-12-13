/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nxt.ott.activity.account;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.nxt.ott.Constant;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.domain.ExpertInfo;
import com.nxt.ott.statusbar.StatusBarUtil;
import com.nxt.ott.util.ToastUtils;
import com.nxt.zyl.ui.notification.Effects;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.HttpUtils;
import com.nxt.zyl.util.ZPreferenceUtils;
import com.nxt.zyl.util.ZToastUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import okhttp3.Response;

/**
 * 登陆页面
 */
public class MyLoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_SETNICK = 1;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button btn_login;
    private boolean autoLogin = false;
    private Effects effects = Effects.standard;
    private String currentUsername;
    private String currentPassword;
    //    private LoadingButton loginbtn;
    private TextView tvTopBarText, tvRegister;
    private RelativeLayout iv_experter_back;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);

        Log.e("login-78", "tag---------------------------");
        // 如果用户名密码都有，直接进入主页面
        if (ZPreferenceUtils.getPrefBoolean(com.nxt.ott.Constant.IS_LOGIN, false)) {
            autoLogin = true;
            startActivity(new Intent(MyLoginActivity.this, MainActivity.class));
            Log.e("login-78", "tag---------------------------");
            return;
        }
        setContentView(R.layout.activity_login_new);
        StatusBarUtil.setTransparent(this);
//        tvTopBarText = (TextView) findViewById(R.id.tv_title);
//        initTopbar(this,getResources().getString(R.string.login));

        usernameEditText = (EditText) findViewById(R.id.et_username);
        passwordEditText = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
//        loginbtn = (LoadingButton) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        btn_login.setOnClickListener(this);
//        loginbtn.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        // 如果用户名改变，清空密码
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        if (DemoHelper.getInstance().getCurrentUsernName() != null) {
//            usernameEditText.setText(DemoHelper.getInstance().getCurrentUsernName());
//        }
    }

//    /**
//     * Topbar左侧按钮单击事件
//     */
//    public void onLeftClick(View view) {
//        finish();
//    }

//    private void initTopbar(Activity activity, String titlename) {
//        iv_experter_back = (RelativeLayout) activity.findViewById(R.id.layout_left);
//        tvTopBarText = (TextView) activity.findViewById(R.id.tv_title);
//        tvTopBarText.setText(titlename);
//        iv_experter_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

    /**
     * 登录
     *
     * @param
     */
    protected void login() {
        final long start = System.currentTimeMillis();
        //调用环信sdk方法登陆聊天服务器
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("huqiang","login");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response response = OkGo.post(Constant.LOADINFO)
                                    .params("account",currentUsername)
                                    .execute();
                            String jsonStr = response.body().string();
                            ExpertInfo info = new Gson().fromJson(jsonStr,ExpertInfo.class);
                            if (TextUtils.equals("1",info.getCode()) &&info.getExpert()!=null){
                                ZPreferenceUtils.setPrefString(Constant.NICKNAME, info.getExpert().getNickname());
                                ZPreferenceUtils.setPrefString(Constant.AVATOR,info.getExpert().getImg());
                            }
                            String url = String.format(Constant.LOGIN_URL, currentUsername, currentPassword, 0);
                            System.out.println("ur-------->" + url);
                            handler.sendMessage(handler.obtainMessage(0, HttpUtils.getJsonContent(url)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            @Override
            public void onError(int i, final String s) {
                final int flag =i;
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (dialog.isShowing()){
                            dialog.dismiss();
                            switch (flag){
                                case 204://用户不存在
                                    Toast.makeText(getApplicationContext(), "登陆失败:" + "用户不存在", Toast.LENGTH_SHORT).show();
                                    break;
                                case 303://未知服务
                                    Toast.makeText(getApplicationContext(), "登陆失败:" + "未知的server异常", Toast.LENGTH_SHORT).show();
                                    break;
                                case 202://用户名或密码错误
                                    Toast.makeText(getApplicationContext(), "登陆失败:" + "用户账号或密码错误", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "登陆失败:" +s, Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        }

                    }
                });
            }
            @Override
            public void onProgress(int i, String s) {

            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }

    private void loginSuccess2Umeng(final long start) {
        runOnUiThread(new Runnable() {
            public void run() {
                long costTime = System.currentTimeMillis() - start;
                Map<String, String> params = new HashMap<String, String>();
                params.put("status", "success");
                MobclickAgent.onEventValue(MyLoginActivity.this, "login1", params, (int) costTime);
                MobclickAgent.onEventDuration(MyLoginActivity.this, "login1", (int) costTime);
            }
        });
    }

    private void loginFailure2Umeng(final long start, final int code, final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                long costTime = System.currentTimeMillis() - start;
                Map<String, String> params = new HashMap<String, String>();
                params.put("status", "failure");
                params.put("error_code", code + "");
                params.put("error_description", message);
                MobclickAgent.onEventValue(MyLoginActivity.this, "login1", params, (int) costTime);
                MobclickAgent.onEventDuration(MyLoginActivity.this, "login1", (int) costTime);

            }
        });
    }

    public static String removeBOM(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }

        if (data.startsWith("\ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            loginbtn.showButtonText();
            String str = (String) msg.obj;
            System.out.println("str----------------->" + str);
            if (!TextUtils.isEmpty(str)) {
                try {
                    JSONObject json = new JSONObject(str);
                    if (TextUtils.equals(json.getString("success"),"true")){
                        dialog.dismiss();
                        String abc = removeBOM(json.getString("data"));
                        JSONObject js = new JSONObject(abc);
                        ZPreferenceUtils.setPrefString(Constant.USERNAME, currentUsername);
                        ZPreferenceUtils.setPrefString(Constant.USER_TYPE, js.getString("usertype"));
                        ZPreferenceUtils.setPrefString(Constant.YWZC, js.getString("yewuzhuanchang"));
                        Log.e("log-314", "usertype--------------->" + js.getString("usertype"));
//                     ZPreferenceUtils.setPrefString(Constant.PASSWORD,currentPassword);
//                     ZPreferenceUtils.setPrefString("dname",js.getString("dname"));//部门
//                     ZPreferenceUtils.setPrefString("name",js.getString("name"));//部门
//                     ZPreferenceUtils.setPrefString("post",js.getString("post"));//岗位
//                     ZPreferenceUtils.setPrefString("zhiwu",js.getString("zhiwu"));
//                     ZPreferenceUtils.setPrefString("uid",js.getString("uid"));
//                     ZPreferenceUtils.setPrefString("tel",js.getString("tel"));
//                     ZPreferenceUtils.setPrefInt("ID",js.getInt("ID"));
//                     ZPreferenceUtils.setPrefBoolean(Constant.ISLOGIN, true);
                        // 登陆成功，保存用户名密码
//                    MyApplication.getInstance().setUserName(currentUsername);
//                    MyApplication.getInstance().setPassword(currentPassword);
                        ZPreferenceUtils.setPrefBoolean(Constant.IS_LOGIN, true);
                        ZPreferenceUtils.setPrefString(Constant.USERNAME, usernameEditText.getText().toString());
                        //存储uid和电话号码
                        ZPreferenceUtils.setPrefString(Constant.UID,js.getString("uid"));
                        ZPreferenceUtils.setPrefString(Constant.TEL,js.getString("tel"));
//                     进入主页面
                        Intent intent = new Intent(MyLoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        dialog.dismiss();
                        ToastUtils.showShort(MyLoginActivity.this,"登陆失败:"+json.getString("msg"));
                        EMClient.getInstance().logout(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                dialog.dismiss();
                ZToastUtils.showShort(MyLoginActivity.this,"登陆失败:账号不存在!");
                EMClient.getInstance().logout(true);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget_pwd:
                startActivity(new Intent(this, RegisterStepOneActivity.class).putExtra("type", true));
                break;
            case R.id.btn_login:
                if (!CommonUtils.isNetWorkConnected(this)) {
                    Toast.makeText(this, "网络连接出现问题!", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentUsername = usernameEditText.getText().toString().trim();
                currentPassword = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(currentUsername)) {
                    ZToastUtils.showShort(this, getString(R.string.username_isnot_null));
                    return;
                } else if (!com.nxt.zyl.util.CommonUtils.isMobileNO(currentUsername)) {
                    ZToastUtils.showShort(this, getString(R.string.please_write_rightphone));
                    return;
                } else if (TextUtils.isEmpty(currentPassword)) {
                    ZToastUtils.showShort(this, getString(R.string.pwd_isnot_null));
                    return;
                }
                dialog = new SpotsDialog(this, R.style.CustomDialog);
                dialog.show();
                login();
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterStepOneActivity.class));
                break;
            default:
                break;
        }
    }

    private long exitTime;

    /**
     * 重写主界面下的返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
//                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
//                    drawerLayout.closeDrawer(Gravity.LEFT);
//                    return false;
//                }
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), getString(R.string.exit_notice),
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    moveTaskToBack(true);
//                    MyApplication.getInstance().exit();//关闭栈中的activity
//                    System.exit(0);
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
