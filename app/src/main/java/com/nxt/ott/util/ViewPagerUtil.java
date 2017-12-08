package com.nxt.ott.util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nxt.ott.Constant;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.activity.WebActivity;
import com.nxt.ott.activity.animation.ZoomOutPageTransformer;
import com.nxt.ott.domain.HomeImage;
import com.nxt.zyl.data.ZDataTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ViewPagerUtil {
    private ZDataTask zDataTask;
    private MyApplication application;
    private ArrayList<ImageView> imageViews;
    private ArrayList<String> titles;
    private ArrayList<View> dots;
    private TextView tv_title;
    private ViewPager viewPager;
    int currPage = 0;
    private List<HomeImage.RowsBean> imgList = new ArrayList<>();

    public void initVeiwPager(FrameLayout layout, List<HomeImage.RowsBean> list, Context context) {
        imgList = list;
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        application = MyApplication.getInstance();
        zDataTask = application.getZDataTask();
        imageViews = new ArrayList<ImageView>();
        titles = new ArrayList<String>();
        dots = new ArrayList<View>();
        dots.add(layout.findViewById(R.id.v_dot0));
        dots.add(layout.findViewById(R.id.v_dot1));
        dots.add(layout.findViewById(R.id.v_dot2));
        dots.add(layout.findViewById(R.id.v_dot3));
        dots.add(layout.findViewById(R.id.v_dot4));
        dots.add(layout.findViewById(R.id.v_dot5));

        tv_title = (TextView) layout.findViewById(R.id.tv_title);
        for (int i = 0; i < imgList.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ScaleType.FIT_XY);
            Glide.with(context)
                    .load(imgList.get(i).getImghttpurl())
                    .crossFade()
                    .into(imageView);
            imageViews.add(imageView);
            titles.add(imgList.get(i).getImgeHead());
            dots.get(i).setVisibility(View.VISIBLE);
        }
//        tv_title.setText(titles.get(i));
        viewPager = (ViewPager) layout.findViewById(R.id.vp);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());//添加动画效果
        viewPager.setAdapter(new MyAdapter(context));
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            private int oldPosition = 0;
            @Override
            public void onPageSelected(int position) {
                tv_title.setText(titles.get(position));
                ((View) dots.get(oldPosition))
                        .setBackgroundResource(R.drawable.dot_normal);
                ((View) dots.get(position))
                        .setBackgroundResource(R.drawable.dot_focused);
                oldPosition = position;

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        ViewPagerTask pagerTask = new ViewPagerTask();
        scheduled.scheduleAtFixedRate(pagerTask, 5, 5, TimeUnit.SECONDS);

    }

    class MyAdapter extends PagerAdapter {
        Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("title",imgList.get(position).getImgeHead());
                    intent.putExtra("url", Constant.HOME_IMAGE_DETAIL+imgList.get(position).getId());
                    context.startActivity(intent);
                }
            });
//            switch (position) {
//                case 0:
//                    view.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(context, WebActivity.class);
//                            intent.putExtra("title",imgList.get(position).getImgeHead());
//                            intent.putExtra("url", Constant.HOME_IMAGE_DETAIL+imgList.get(position).getId());
//                            context.startActivity(intent);
//                        }
//                    });
//                    break;
//                case 1:
//                    view.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent();
//                            intent.setClass(context, CaptionTwoActivity.class);
//                            context.startActivity(intent);
//                        }
//                    });
//                    break;
//                case 2:
//                    view.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent();
//                            intent.setClass(context, CaptionThreeActivity.class);
//                            context.startActivity(intent);
//                        }
//                    });
//                    break;
//                case 3:
//                    view.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent();
//                            intent.setClass(context, CaptionFourActivity.class);
//                            context.startActivity(intent);
//                        }
//                    });
//                    break;
//                case 4:
//                    view.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent();
//                            intent.setClass(context, CaptionFiveActivity.class);
//                            context.startActivity(intent);
//                        }
//                    });
//                    break;

//            }
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub
        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }
    }

    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            currPage = (currPage + 1) % imageViews.size();
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(currPage);
        }
    };



}
