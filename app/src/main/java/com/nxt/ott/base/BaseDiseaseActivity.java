package com.nxt.ott.base;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.zyl.ui.widget.AVLoadingIndicatorView;


/**
 * @author zhangyonglu
 *         Created on 2015/10/09
 */
public abstract class BaseDiseaseActivity extends BaseActivity {
    private RelativeLayout iv_experter_back;
    protected InputMethodManager inputMethodManager;
    protected AVLoadingIndicatorView loadingDialog;
    protected ImageButton imgbtnLeft, imgbtnRight;
    protected TextView tvTopBarText;
    protected ProgressBar progressBar;
    protected CheckBox rbtstepone,rbtsteptwo,rbtstepthree,rbtstepfour,rbtsteptextone,rbtsteptexttwo,rbtsteptextthree,rbtsteptextfour;



    /**
     * Topbar左侧按钮单击事件
     */
    public void onLeftClick(View view) {
        finish();
    }

    /**
     * Topbar右侧按钮单击事件
     */
    public void onRightClick(View view) {
    }

    /**
     * 初始化Topbar
     *
     * @param activity {@link Activity} 使用Topbar的Activity
     */
    protected void initTopbar(Activity activity,String titlename) {
        iv_experter_back = (RelativeLayout) findViewById(R.id.layout_left);
        imgbtnRight= (ImageButton) activity.findViewById(R.id.imgbtn_right);
       tvTopBarText = (TextView) activity.findViewById(R.id.tv_title);
       tvTopBarText.setText(titlename);
        rbtstepone= (CheckBox) findViewById(R.id.rbt_step_one);
        rbtsteptwo= (CheckBox) findViewById(R.id.rbt_step_two);
        rbtstepthree= (CheckBox) findViewById(R.id.rbt_step_three);
        rbtstepfour= (CheckBox) findViewById(R.id.rbt_step_four);
        rbtsteptextone= (CheckBox) findViewById(R.id.rbt_text_one);
        rbtsteptexttwo= (CheckBox) findViewById(R.id.rbt_text_two);
        rbtsteptextthree= (CheckBox) findViewById(R.id.rbt_text_three);
        rbtsteptextfour= (CheckBox) findViewById(R.id.rbt_text_four);
        loadingIndicatorView= (AVLoadingIndicatorView) activity.findViewById(R.id.loading);
        iv_experter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void showloading(){
        loadingIndicatorView.setVisibility(View.VISIBLE);
    }
    public void dismissloading(){
        loadingIndicatorView.setVisibility(View.GONE);

    }

}
