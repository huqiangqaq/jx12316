package com.nxt.ott.expertUpdate;

import android.view.View;
import android.widget.RelativeLayout;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.view.UnderLineEdittext;

import butterknife.BindView;


public class MyQuestionActivity extends BaseTitleActivity {


    @BindView(R.id.rl_et)
    RelativeLayout rl_et;
    @BindView(R.id.et_answer)
    UnderLineEdittext et_answer;
    @Override
    protected void initView() {
        initTopbar(this,"问题详情");
        rl_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_answer.requestFocus();
                toggleInput();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_question;
    }
}
