package com.nxt.ott.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.util.NetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/7 0007.
 */
public class SupplyDemandFragment extends Fragment {
    private View view;
    private Context context;
    private boolean isnet;
    private ImageView cursor;
    private int bmpW;
    private int offset;
    private ArrayList<TextView> listtext;
    private TextView supplylist_text,wrsupply_text,price_text;
    private ViewPager pager;
    private List<Fragment> fragmentlist=new ArrayList<Fragment>();
    private SupplyListFragment supplyListFragment;
    private WriteSupplyFragment writeSupplyFragment;
    private PriceFragment2 priceFragment;
    private FragmentAdapter ftadapter;
    private int currIndex;
    Animation animation = null;
    Matrix matrix;
    MyOnPageChangeListener pagelistener;

    private  int one ;// 椤靛崱1 -> 椤靛崱2 鍋忕Щ閲�
    private int two;// 椤靛



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup)view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view=inflater.inflate(R.layout.supplymsg,null);
        InitImageView();
        InitTextView();
        InitView();
        //System.out.println("oncreateview");
        return view;
    }

    private void InitView() {
        pager= (ViewPager) view.findViewById(R.id.vPager);
        supplyListFragment=new SupplyListFragment();
        writeSupplyFragment=new WriteSupplyFragment();
        priceFragment=new PriceFragment2();
        fragmentlist.add(supplyListFragment);
        fragmentlist.add(writeSupplyFragment);

        fragmentlist.add(priceFragment);
        ftadapter=new FragmentAdapter(getFragmentManager());
        //
        pager.setAdapter(ftadapter);
        pager.setCurrentItem(0);
        setcorlor(0);
        pagelistener=new MyOnPageChangeListener();
        pager.setOnPageChangeListener(pagelistener);


    }

    private void InitImageView() {
        context=getActivity();
        isnet = NetUtil.isNetworkConnected(context);
        cursor = (ImageView) view.findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.redline)
                .getWidth();// 鑾峰彇鍥剧墖瀹藉害
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 鑾峰彇鍒嗚鲸鐜囧搴�
        offset = (screenW /3 - bmpW) / 2;// 璁＄畻鍋忕Щ閲�
        matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 璁剧疆鍔ㄧ敾鍒濆浣嶇疆
        one = offset * 2 + bmpW;//
        two = one * 2;
    }
    private void InitTextView() {
        listtext = new ArrayList<TextView>();
        supplylist_text= (TextView) view.findViewById(R.id.tv_supply_list);
        wrsupply_text = (TextView) view.findViewById(R.id.tv_write_supply);
        price_text= (TextView) view.findViewById(R.id.tv_market_price);
        listtext.add(supplylist_text);
        listtext.add(wrsupply_text);
        listtext.add( price_text);
        //listtext.add(ptsource);

        supplylist_text.setOnClickListener(new MyOnClickListener(0));
        wrsupply_text.setOnClickListener(new MyOnClickListener(1));
        price_text.setOnClickListener(new MyOnClickListener(2));
        // ptsource.setOnClickListener(new MyOnClickListener(3));
    }
    /**
     * 澶存爣鐐瑰嚮鐩戝惉
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            setcorlor(index);
            pager.setCurrentItem(index);
        }
    }

    private void setcorlor(int index) {
        for (int i = 0; i < listtext.size(); i++) {
            if (i == index) {
                listtext.get(i).setTextColor(getResources().getColor(R.color.redbg));
            } else {
                listtext.get(i).setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }
    private class FragmentAdapter extends FragmentPagerAdapter{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {


            return fragmentlist.get(i);
        }

        @Override
        public int getCount() {
            return fragmentlist.size();
        }
    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {





        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int arg0) {

            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;

            }
            currIndex = arg0;
            setcorlor(currIndex);
            animation.setFillAfter(true);//
            animation.setDuration(10);
            cursor.setAnimation(animation);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //System.out.println("oncreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {

        cursor.setAnimation(animation);
        super.onStart();
    }
    @Override
    public void onResume() {

        // System.out.println("onResume");

        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }
}
