package com.nxt.ott.activity.titlebottom;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

/**
 * Created by zhangyonglu on 2016/2/29 0029.
 */
public class EwmActivity extends BaseTitleActivity{
    @Override
    protected void initView() {
        application.addActivity(this);

        initTopbar(this,getString(R.string.ewm));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ewm;
    }
}
