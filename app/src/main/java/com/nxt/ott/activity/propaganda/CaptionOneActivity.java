package com.nxt.ott.activity.propaganda;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

/**
 * Created by Administrator on 2016/5/26.
 */
public class CaptionOneActivity extends BaseTitleActivity {

    @Override
    protected void initView() {

        initTopbar(this,getString(R.string.detail));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_caption_1;
    }

}
