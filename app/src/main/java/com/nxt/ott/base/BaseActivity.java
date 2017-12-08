package com.nxt.ott.base;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import com.nxt.ott.MyApplication;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.data.volley.toolbox.HttpCallback;
import com.nxt.zyl.ui.widget.AVLoadingIndicatorView;
import com.nxt.zyl.util.TimeUtil;

import java.util.Calendar;

import butterknife.ButterKnife;


/**
 * @author zhangyonglu
 *         Created on 2015/10/05
 */
public abstract class BaseActivity extends FragmentActivity implements
        View.OnClickListener, HttpCallback, DatePickerDialog.OnDateSetListener {
    protected InputMethodManager inputMethodManager;
    protected ProgressDialog loadingDialog;
    protected MyApplication application;
    protected ZDataTask zDataTask;
    protected static Calendar calendar = Calendar.getInstance();
    protected String date;
    protected AVLoadingIndicatorView loadingIndicatorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        date = TimeUtil.getdate1();
        application = MyApplication.getInstance();
        zDataTask = application.getZDataTask();
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化界面
     */
    protected abstract int getLayout();

    @Override
    public void onClick(View v) {
    }

    /**
     * 显示或隐藏输入法
     */
    protected void toggleInput() {
        if (getWindow().getAttributes().softInputMode
                == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            if (inputMethodManager == null) {
                inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示进度对话框
     *
     * @param message {@link String} 消息文本
     */
    protected void showLoadingDialog(int message) {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
        }
        loadingDialog.setMessage(getString(message));
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    /**
     * 取消进度对话框
     */
    protected void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onRequestStart() {
        //Here's nothing
    }

    @Override
    public void onRequestFinish() {
        //Here's nothing
    }

    @Override
    public void onRequestResult(String string) {
        //Here's nothing
    }

    @Override
    public void onRequestError(Exception e) {
        //Here's nothing
    }

    @Override
    public void onRequestCancelled() {
        //Here's nothing
    }

    @Override
    public void onRequestLoading(long count, long current) {
        //Here's nothing
    }

    protected void showtimedialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, this, calendar
                .get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {


    }

    public void showloading() {
        loadingIndicatorView = new AVLoadingIndicatorView(this);
        loadingIndicatorView.setVisibility(View.VISIBLE);
    }

    public void dismissloading() {
        if (loadingIndicatorView != null) {
            loadingIndicatorView.setVisibility(View.GONE);
        }
    }
}
