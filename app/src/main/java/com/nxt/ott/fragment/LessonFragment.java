package com.nxt.ott.fragment;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.Constant;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.domain.LessonType;
import com.nxt.zyl.data.ZDataTask;
import com.nxt.zyl.ui.fragment.ZBaseFragment;
import com.nxt.zyl.util.CommonUtils;
import com.nxt.zyl.util.ZToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Updated by iwon on 2016/6/19 20:07.
 */
public class LessonFragment extends ZBaseFragment {
    private TextView tvTitle;
    private ZDataTask zDataTask;
    private TabLayout newstab;
    private ViewPager newsvp;
    protected MyApplication application;
    private List<String> titles, urllist = new ArrayList<>();
    private List<LessonType> lessonTypeList = new ArrayList<>();
    private RelativeLayout rlBack;

    @Override
    protected void initView(View view) {
//        view.findViewById(R.id.tv_title).setBackgroundColor(ZPreferenceUtils.getPrefInt(Constant.SKIN_COLOR, getResources().getColor(R.color.title_color)));
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.lesson));
        newstab = (TabLayout) view.findViewById(R.id.tab_news);
        newsvp = (ViewPager) view.findViewById(R.id.vp_news);
        application = MyApplication.getInstance();
        zDataTask = application.getZDataTask();
        rlBack = (RelativeLayout) view.findViewById(R.id.layout_left);
        rlBack.setOnClickListener(this);
        Log.e("farmlesson-22", "url----------->" + String.format(Constant.LESSON_TYPE_LIST_URL, ""));
        getData();
    }

    private void getData() {
        if (CommonUtils.isNetWorkConnected(getActivity())) {
            zDataTask.get(String.format(Constant.LESSON_TYPE_LIST_URL, ""), null, null, this);
        } else {
            ZToastUtils.showShort(getActivity(), R.string.error_network_none);
        }
    }

    @Override
    public void onRequestResult(String string) {
        Log.e("lesson-60", "result------------>" + string);
        lessonTypeList = new Gson().fromJson(string, new TypeToken<List<LessonType>>() {
        }.getType());
        setview();
        Log.e("lesson-64", "result1------------>" + lessonTypeList);
    }

    private void setview() {
        MyFragmentAdapter adapter = new MyFragmentAdapter(getActivity().getSupportFragmentManager());
//        for (int i = 0; i < lessonTypeList.size(); i++) {
//            adapter.addFragment(LessonItemFragment.newInstance(lessonTypeList.get(i).getId()), lessonTypeList.get(i).getName());
//        }
//
//        newsvp.setAdapter(adapter);
//        for (int i = 0; i < lessonTypeList.size(); i++) {
//            newstab.addTab(newstab.newTab().setText(lessonTypeList.get(i).getName()));
//        }

        adapter.addFragment(LessonItemFragment.newInstance(lessonTypeList.get(7).getId()), lessonTypeList.get(7).getName());
        adapter.addFragment(LessonItemFragment.newInstance(lessonTypeList.get(8).getId()), lessonTypeList.get(8).getName());
        //学销售改为学电商，因改动较小，暂不改后台
        adapter.addFragment(LessonItemFragment.newInstance(lessonTypeList.get(9).getId()), "学电商");
        adapter.addFragment(LessonItemFragment.newInstance(lessonTypeList.get(1).getId()), lessonTypeList.get(1).getName());
//        adapter.addFragment(LessonItemFragment.newInstance(lessonTypeList.get(0).getId()), lessonTypeList.get(0).getName());
        adapter.addFragment(LessonItemFragment.newInstance(lessonTypeList.get(15).getId()), lessonTypeList.get(15).getName());
        newsvp.setAdapter(adapter);

        newstab.addTab(newstab.newTab().setText(lessonTypeList.get(7).getName()));
        newstab.addTab(newstab.newTab().setText(lessonTypeList.get(8).getName()));
        newstab.addTab(newstab.newTab().setText(lessonTypeList.get(9).getName()));
        newstab.addTab(newstab.newTab().setText(lessonTypeList.get(1).getName()));
//        newstab.addTab(newstab.newTab().setText(lessonTypeList.get(0).getName()));
        newstab.addTab(newstab.newTab().setText(lessonTypeList.get(15).getName()));
        newstab.setupWithViewPager(newsvp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                getActivity().finish();
                break;
        }
        super.onClick(v);
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmenttitles = new ArrayList<>();                            //tab名的列表

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {

            fragments.add(fragment);
            fragmenttitles.add(title);

        }

        @Override
        public Fragment getItem(int position) {

            System.out.println("position-------->" + position + "fragments  size--->" + fragments.size());
            return fragments.get(position);

        }

        @Override
        public int getCount() {
            return fragmenttitles.size();
        }

        //此方法用来显示tab上的名字
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmenttitles.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lesson;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}
