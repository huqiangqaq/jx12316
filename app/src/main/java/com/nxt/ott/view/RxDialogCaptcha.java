package com.nxt.ott.view;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nxt.ott.R;

import static com.nxt.ott.view.RxCaptcha.TYPE.CHARS;


/**
 * Created by huqiang on 2017/11/28 11:27.
 */

public class RxDialogCaptcha extends RxDialog {
    private ImageView ivcode;
    private TextView mTvSure;
    private TextView mTvCancel;
    private EditText editText;
    private TextView mTvTitle;
    public RxDialogCaptcha(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public RxDialogCaptcha(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public RxDialogCaptcha(Context context) {
        super(context);
        initView();
    }

    public RxDialogCaptcha(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public TextView getTitleView() {
        return mTvTitle;
    }
    public void setTitle(String title){
        mTvTitle.setText(title);
    }
    public EditText getEditText() {
        return editText;
    }

    public TextView getSureView() {
        return mTvSure;
    }

    public void setSure(String strSure) {
        this.mTvSure.setText(strSure);
    }

    public TextView getCancelView() {
        return mTvCancel;
    }

    public void setCancel(String strCancel) {
        this.mTvCancel.setText(strCancel);
    }

    public String getCode(){
        return RxCaptcha.build().getCode();
    }
    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_captcha, null);
//        mIvLogo = (ImageView) dialogView.findViewById(R.id.iv_logo);
        mTvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
        mTvSure = (TextView) dialogView.findViewById(R.id.tv_sure);
        mTvCancel = (TextView) dialogView.findViewById(R.id.tv_cancle);
        editText = (EditText) dialogView.findViewById(R.id.editText);
        ivcode = dialogView.findViewById(R.id.iv_code);
        RxCaptcha.build()
                .backColor(0xffffff)
                .codeLength(4)
                .fontSize(62)
                .lineNumber(2)
                .size(240, 85)
                .type(CHARS)
                .into(ivcode);
        ivcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxCaptcha.build()
                        .backColor(0xffffff)
                        .codeLength(4)
                        .fontSize(62)
                        .lineNumber(2)
                        .size(240, 85)
                        .type(CHARS)
                        .into(ivcode);
            }
        });
        setContentView(dialogView);
    }
}
