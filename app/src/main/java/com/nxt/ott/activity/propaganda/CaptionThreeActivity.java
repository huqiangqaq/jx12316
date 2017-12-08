package com.nxt.ott.activity.propaganda;


import android.widget.ImageView;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.util.BitmapUtil;

import static com.nxt.ott.R.id.imageView;

/**
 * Created by Administrator on 2016/5/26.
 */
public class CaptionThreeActivity extends BaseTitleActivity {
    @Override
    protected void initView() {
        initTopbar(this,getString(R.string.caption_three));
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_caption_3;
    }
}
