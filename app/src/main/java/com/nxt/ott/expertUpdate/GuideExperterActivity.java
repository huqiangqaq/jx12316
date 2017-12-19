package com.nxt.ott.expertUpdate;

import android.widget.ImageView;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

import butterknife.BindView;

public class GuideExperterActivity extends BaseTitleActivity {

    private boolean isExperter;
    @BindView(R.id.image)
    ImageView image;
    @Override
    protected void initView() {
       isExperter = getIntent().getBooleanExtra("isExperter",false);
       if (isExperter){
           initTopbar(this,"注册专家");
           image.setImageResource(R.mipmap.guide1);
       }else {
           initTopbar(this,"注册会员");
           image.setImageResource(R.mipmap.guide2);
       }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_guide_experter;
    }
}
