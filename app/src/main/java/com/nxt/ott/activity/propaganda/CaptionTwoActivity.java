package com.nxt.ott.activity.propaganda;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

/**
 * Created by Administrator on 2016/5/26.
 */
public class CaptionTwoActivity extends BaseTitleActivity {
    @Override
    protected void initView() {
        initTopbar(this,getString(R.string.caption_one));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_caption_2;
    }
}
