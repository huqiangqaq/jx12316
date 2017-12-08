package com.nxt.ott.activity.doctor;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseZoomTitleActivity;
import com.nxt.ott.view.CustomGridView;

/**
 * Created by zhangyonglu on 2016/3/9 0009.
 */
public class AgricultureDoctorActivity extends BaseZoomTitleActivity{
    private CustomGridView gridView;
    private int[] imgids=new int[]{
            R.mipmap.doctor_poultry,
            R.mipmap.doctor_raise_livestock,
            R.mipmap.doctor_agricultural_products,
            R.mipmap.doctor_garden_stuff,
            R.mipmap.doctor_water_production,
            R.mipmap.doctor_more};
    private String[] titlelist,introductionlist;
    private View contentview;

    @Override
    protected void initView() {
        initTopbar(this,getString(R.string.agriculture_doctor));
        int headerheight= BitmapFactory.decodeResource(getResources(), R.mipmap.doctor_top_bg)
                .getHeight();
        headerimg.setImageResource(R.mipmap.doctor_top_bg);
        scrollView.setHeaderViewSize(screenwidth,headerheight);
        contentview= LayoutInflater.from(this).inflate(R.layout.activity_agricultural_doctor, null);
        parentlayout.addView(contentview);
        gridView= (CustomGridView) scrollView.findViewById(R.id.gridview_doctor);
        titlelist=getResources().getStringArray(R.array.doctor_name_list);
        introductionlist=getResources().getStringArray(R.array.doctor_introduction_list);
        gridView.setAdapter(new DoctorAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != titlelist.length - 1) {
                    startActivity(new Intent(AgricultureDoctorActivity.this,
                            AgricultureDoctorTypeActivity.class).putExtra("type", titlelist[position]));
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.common_scrollview;
    }
    class DoctorAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return titlelist.length;
        }

        @Override
        public Object getItem(int position) {
            return titlelist[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView==null){
                holder=new Holder();
                convertView= LayoutInflater.from(AgricultureDoctorActivity.this)
                        .inflate(R.layout.layout_doctor_list,null);
                holder.imgview= (ImageView) convertView.findViewById(R.id.img_doctor);
                holder.nameview= (TextView) convertView.findViewById(R.id.tv_name);
                holder.introuctionview= (TextView) convertView.findViewById(R.id.tv_introudction);
                convertView.setTag(holder);
            }else{
                holder= (Holder) convertView.getTag();
            }
            holder.imgview.setImageResource(imgids[position]);
            holder.nameview.setText(titlelist[position]);
            holder.introuctionview.setText(introductionlist[position]);
            return convertView;
        }
        class Holder{
            ImageView imgview;
            TextView nameview;
            TextView introuctionview;
        }
    }
}
