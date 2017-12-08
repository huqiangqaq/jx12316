package com.nxt.ott.activity.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.activity.account.MainActivity;
import com.nxt.ott.activity.account.MyLoginActivity;
import com.nxt.zyl.util.ZPreferenceUtils;


/**
 * Created by iwon on 2016/06/19 0031.
 */
public class SplashActivity extends AppCompatActivity{
    private boolean isFirstIn = false;
    private static final int TIME = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private boolean isLogin = false;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_HOME:
                        setGoHome();
                    break;

                case GO_GUIDE:
                        setGoGuide();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(com.nxt.ott.R.layout.activity_splash);
        init();
//        getExperterType();
    }


    //判断是否加载引导界面
    private void init(){
        SharedPreferences preferences = getSharedPreferences("", MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn",true);
        if (!isFirstIn){
            mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
        }else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn",false);
            editor.apply();
        }
    }

    private void setGoHome(){
        isLogin = ZPreferenceUtils.getPrefBoolean(com.nxt.ott.Constant.IS_LOGIN, false);
        if (isLogin){
            Intent i = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.screen_zoom_in,R.anim.screen_zoom_out);
            finish();
        }else {
            startActivity(new Intent(SplashActivity.this, MyLoginActivity.class));
            finish();
        }

    }

    private void setGoGuide(){
        Intent i = new Intent(SplashActivity.this,GuideActivity.class);
        startActivity(i);
        finish();
    }
}

