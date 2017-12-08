package com.nxt.ott.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.util.JsonUtils;
import com.nxt.ott.util.ToastUtils;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by huqiang on 2017/4/26 13:12.
 */

public class EditTextDialog extends BaseBottomDialog {
    private EditText mEditText;
    private TextView tv_update;
    private String id;

    public EditTextDialog() {
    }

    public static EditTextDialog NewInstance(String id){
        EditTextDialog dialog = new EditTextDialog();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_edittext;
    }

    @Override
    public void bindView(View v) {
        Bundle bundle = getArguments();
        if (bundle!=null){
            id = bundle.getString("id");
        }
        mEditText = (EditText) v.findViewById(R.id.edit_text);
        tv_update = (TextView) v.findViewById(R.id.tv_update);
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, 0);
            }
        });
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEditText.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    ToastUtils.showShort(getActivity(),"修改的内容不能为空!");
                    return;
                }
                OkGo.post(Constant.ALERT_ANSWER_TEXT)
                        .params("id",id)
                        .params("text",content)
                        .params("addAndReply","reply")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                if ("1".equals(JsonUtils.getServerResult(s))){
                                    ToastUtils.showShort(getActivity(),"修改成功!");
                                    getActivity().sendBroadcast(new Intent("com.android.broadcast.REFRESH_ACTION"));
                                    dismiss();
                                }else {
                                    ToastUtils.showShort(getActivity(),"修改失败:"+JsonUtils.getServerMsg(s));
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                ToastUtils.showShort(getActivity(),"失败:服务器没有响应!");
                            }
                        });
            }
        });
    }

    @Override
    public float getDimAmount() {
        return 0.6f;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
