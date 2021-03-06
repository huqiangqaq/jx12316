package com.nxt.ott.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.zyl.ui.widget.AVLoadingIndicatorView;
import com.nxt.zyl.ui.widget.pulltoroom.PullToZoomScrollViewEx;


/**
 * @author zhangyonglu
 *         Created on 2015/10/09
 */
public abstract class  BaseZoomTitleActivity extends BaseActivity {
    protected InputMethodManager inputMethodManager;
    protected ProgressDialog loadingDialog;
    protected ImageButton imgbtnLeft, imgbtnRight;
    protected AVLoadingIndicatorView loadingIndicatorView;
    protected PullToZoomScrollViewEx scrollView;
    protected ImageView headerimg;
    protected LinearLayout parentlayout;
    protected static int screenwidth;
    private RelativeLayout iv_experter_back;
    private TextView tvTopBarText;

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
    protected void initTopbar(Activity activity, String titlename) {
        imgbtnRight = (ImageButton) activity.findViewById(R.id.imgbtn_right);
        tvTopBarText = (TextView) activity.findViewById(R.id.tv_title);
        tvTopBarText.setText(titlename);
        screenwidth = getWindowManager().getDefaultDisplay().getWidth();
        scrollView = (PullToZoomScrollViewEx) activity.findViewById(R.id.meft_scroll_view);
        headerimg = (ImageView) scrollView.findViewById(R.id.img_header);

        parentlayout = (LinearLayout) scrollView.findViewById(R.id.layout_common_contentview);

        iv_experter_back = (RelativeLayout) activity.findViewById(R.id.layout_left);
        iv_experter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
