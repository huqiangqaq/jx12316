package com.nxt.ott.activity.guide;

/**
 * Created by Administrator on 2015/12/31.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.activity.account.MainActivity;
import com.nxt.ott.activity.account.MyLoginActivity;
import com.nxt.ott.activity.animation.ZoomOutPageTransformer;
import com.nxt.ott.adapter.ViewPagerAdapter;
import com.nxt.zyl.util.ZPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/31.
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener {
    //实现一个监听事件，使点的状态的随着viewpager来变化

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.dot1, R.id.dot2, R.id.dot3};

    private Button btn;
    private boolean isLogin = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);

        setContentView(R.layout.activity_guide);
        initViews();
        initDots();
    }

    //初始化方法
    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);        //通过inflater加载视图

        //将view集合实例化
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.guide_image_1, null));
        views.add(inflater.inflate(R.layout.guide_image_2, null));
        views.add(inflater.inflate(R.layout.guide_image_3, null));

        //实例化adapter
        vpAdapter = new ViewPagerAdapter(views, this);
        //找到对象
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setPageTransformer(true, new ZoomOutPageTransformer());
        vp.setAdapter(vpAdapter);

        btn = (Button) views.get(2).findViewById(R.id.btn_start);
        btn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.guide_btn_enter));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = ZPreferenceUtils.getPrefBoolean(com.nxt.ott.Constant.IS_LOGIN, false);
                if (isLogin){
                    Intent i = new Intent(GuideActivity.this,MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.zoom_out_enter, R.anim.zoom_out_exit);
                    finish();
                    Log.e("guide-78","tag---------------------------");
                }else {
                    startActivity(new Intent(GuideActivity.this, MyLoginActivity.class));
                    finish();
                }

            }
        });
        vp.addOnPageChangeListener(this);               //回掉监听方法
    }

    //对点的操作方法
    private void initDots() {

        dots = new ImageView[views.size()];         //实例化
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    //当页面滑动时调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    //当前新的页面被选中的时候调用
    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ids.length; i++) {
            if (position == i) {
                dots[i].setImageResource(R.mipmap.login_point_selected);
            } else {
                dots[i].setImageResource(R.mipmap.login_point);
            }
        }
    }
    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
