package com.nxt.ott.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.zyl.ui.widget.AVLoadingIndicatorView;


/**
 * @author zhangyonglu
 *         Created on 2015/10/09
 */
public abstract class BaseListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener{
    protected InputMethodManager inputMethodManager;
    protected ProgressDialog loadingDialog;
    protected ImageButton imgbtnLeft, imgbtnRight;
    protected TextView tvTopBarText;
    protected AVLoadingIndicatorView loadingIndicatorView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView mlistview;
    protected View footerview;

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
     * @param activity {@link android.app.Activity} 使用Topbar的Activity
     */
    protected void initTopbar(Activity activity,String titlename) {
        imgbtnRight= (ImageButton) activity.findViewById(R.id.imgbtn_right);
        tvTopBarText = (TextView) activity.findViewById(R.id.tv_title);
        mlistview= (ListView) findViewById(R.id.listview_common);
        mlistview.addHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_empty_heade,null));
        mlistview.setOnItemClickListener(this);
        footerview= LayoutInflater.from(this).inflate(R.layout.layout_foot,null);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_blue_light, android.R.color.holo_blue_light, android.R.color.holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        tvTopBarText.setText(titlename);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
