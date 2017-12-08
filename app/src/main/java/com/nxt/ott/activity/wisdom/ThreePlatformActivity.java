/*
 //        Fertilizername          肥料名称及含量
//        Fertilizationtime       施肥时间
//        Fertilizerdosage        肥料用量
//        Employmentquantity      施肥用工量
//        Fertilizercost          肥料费用
//        Laborcosts              用工费用
//        number            第几次(0代表秧田，1代表大田第一次，2代表大田第二次，3代表大田第三次)
//        type					  类型（秧田，大田）
//        userid                  大户id
*/

package com.nxt.ott.activity.wisdom;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.fragment.IntroduceFragment;
import com.nxt.ott.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/12/21 0021.
 */
public class ThreePlatformActivity extends BaseTitleActivity {
    private TabLayout detailstab;
    private ViewPager detailsvp;
    private List<String> titles, urllist = new ArrayList<>();
    private IntroduceFragment introduceFragment;

    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this, getString(R.string.wisdom_platform));

        detailstab = (TabLayout) findViewById(R.id.tab_news);
        detailsvp = (ViewPager) findViewById(R.id.vp_news);

        urllist.add(Constant.WISDOM_PLATFORM_ONE_URL);
        urllist.add(Constant.WISDOM_PLATFORM_TWO_URL);
        urllist.add(Constant.WISDOM_PLATFORM_THREE_URL);

        titles = Arrays.asList(getResources().getStringArray(R.array.three_platform));
        //viewpager加载adapter
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        for (int i = 0; i < titles.size(); i++) {
            Log.e("Seed-47", "size-------------->" + titles.size());
            adapter.addFragment(IntroduceFragment.newInstance(i, 2), titles.get(i));//0为分类标识
        }
        detailsvp.setAdapter(adapter);
        for (int i = 0; i < titles.size(); i++) {
            detailstab.addTab(detailstab.newTab().setText(titles.get(i)));
        }

        //TabLayout加载viewpager
        detailstab.setupWithViewPager(detailsvp);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_three_platform;
    }

    /**
     * FragmentAdapter的重写方法
     */
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
}
