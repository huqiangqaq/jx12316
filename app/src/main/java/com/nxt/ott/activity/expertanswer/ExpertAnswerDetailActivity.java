package com.nxt.ott.activity.expertanswer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.domain.ExpertAnswer;

public class ExpertAnswerDetailActivity extends Activity {
    private TextView tv_detail_question,tv_detail_answer;
    private RelativeLayout iv_experter_back;
    private TextView tvTopBarText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_answer_detail);
        initTopbar(this, getString(R.string.expertanswer_detail));
        initView();
        initData();

    }

    private void initData() {
        ExpertAnswer.RowsBean answer = (ExpertAnswer.RowsBean) getIntent().getSerializableExtra("answer");
        tv_detail_answer.setText(answer.getEanswer());
        tv_detail_question.setText("问:"+answer.getEquestion());
    }

    private void initView() {
        tv_detail_question = (TextView) findViewById(R.id.tv_detail_question);
        tv_detail_answer = (TextView) findViewById(R.id.tv_detail_answer);
    }
    /**
     * 初始化Topbar
     *
     * @param activity {@link android.app.Activity} 使用Topbar的Activity
     */
    protected void initTopbar(Activity activity, String titlename) {
        iv_experter_back = (RelativeLayout) activity.findViewById(R.id.layout_left);
        tvTopBarText = (TextView) activity.findViewById(R.id.tv_title);
        tvTopBarText.setText(titlename);
        iv_experter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
