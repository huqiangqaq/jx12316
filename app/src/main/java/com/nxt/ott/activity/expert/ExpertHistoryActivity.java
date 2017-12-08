package com.nxt.ott.activity.expert;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

/**
 * Created by zhangyonglu on 2016/4/8 0008.
 */
public class ExpertHistoryActivity extends BaseTitleActivity {
    @Override
    protected void initView() {
        initTopbar(this,getString(R.string.my_ask));

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_experter_history;
    }
}
