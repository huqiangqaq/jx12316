package com.nxt.ott.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nxt.ott.R;
import com.nxt.ott.domain.Pest;
import com.nxt.ott.domain.Pests;

import java.util.List;

/**
 * Created by xpeng on 2016/10/12.
 */

public class CustomGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Pests.RowsBean.ArrBean> itemList;

    public CustomGridViewAdapter(Context context, List<Pests.RowsBean.ArrBean> itemList){
        this.mContext=context;
        this.itemList=itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView=View.inflate(mContext,R.layout.item_gridview,null);
        }

        TextView textView=(TextView)convertView.findViewById(R.id.pest_item_tv_pest);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.pest_item_iv_img);

        textView.setText(itemList.get(position).getTitle());
        ImageLoader.getInstance().displayImage(itemList.get(position).getImgUrl2(),new ImageViewAware(imageView,false));
//        Glide.with(mContext).load(itemList.get(position).getImgUrl2()).into(imageView);
        return convertView;
    }
}
