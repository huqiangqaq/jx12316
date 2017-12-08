package com.nxt.ott.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

import com.nxt.ott.R;

/**
 * Created by huqiang on 2017/3/28 16:58.
 */

public class StarButton extends ToggleButton {
    private boolean flag = false;
    public StarButton(Context context) {
        this(context,null);
    }

    public StarButton(Context context, AttributeSet attrs) {
        this(context, null,0);
    }

    public StarButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked){
            setBackgroundResource(R.mipmap.icon_zan);
        }else {
            setBackgroundResource(R.mipmap.icon_zan);
        }
        super.setChecked(checked);
    }
}
