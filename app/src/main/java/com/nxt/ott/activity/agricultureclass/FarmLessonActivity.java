package com.nxt.ott.activity.agricultureclass;

import android.support.v4.app.Fragment;
import android.view.View;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseActivity;
import com.nxt.ott.fragment.LessonFragment;

/**
 * Updated by iwon on 2016/6/19 20:03.
 */
public class FarmLessonActivity extends BaseActivity implements View.OnClickListener {
    private LessonFragment lessonFragment;

    @Override
    protected void initView() {
        lessonFragment = new LessonFragment();
        setFragment(lessonFragment);
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_farm_lesson;
    }
}
