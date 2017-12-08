package com.nxt.ott.activity.account;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.Constant;
import com.nxt.ott.util.ToastUtils;


/**
 * Created by Administrator on 2016/4/1. 16:28
 */
public class FeedBackActivity extends BaseTitleActivity {
    private EditText et_note;
    private EditText et_constact;
    private EditText et_theme;
    private Button btn_submit;
    private String theme,note,contact;

    @Override
    protected int getLayout() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView() {
        initTopbar(this,"意见反馈");
        et_note=(EditText)findViewById(R.id.et_note);
        et_constact=(EditText)findViewById(R.id.et_contact);
        et_theme=(EditText)findViewById(R.id.et_theme);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        et_theme.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
                theme=et_theme.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 //
                note=et_note.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_constact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
                contact=et_constact.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void submit(View view){

        if(TextUtils.isEmpty(theme)){
            ToastUtils.showShort(this,"请输入您的反馈主题");
        }else if(TextUtils.isEmpty(note)){
            ToastUtils.showShort(this,"请输入您的意见或建议");
        }else if(TextUtils.isEmpty(contact)){
            ToastUtils.showShort(this,"请输入您的联系方式");
        }else {
            MyApplication.getInstance().getZDataTask().get(String.format(Constant.FEED_BACK_URL,
                            theme, note, contact),
                    null, null, this);
        }
    }

    @Override
    public void onRequestResult(String string) {
        if(string.contains("true")){
            ToastUtils.showShort(this, "您的意见已提交，谢谢您的反馈");
            finish();
        }
    }
}
