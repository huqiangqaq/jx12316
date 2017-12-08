package com.nxt.ott.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;

import java.util.List;

/**
 * Created by zhangyonglu on 2016/2/26 0026.
 */
public class AreaAdapter extends ZBaseAdapter<String>{

    public AreaAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.activity_gridarea_list,null);
            holder.areatext= (TextView) convertView.findViewById(R.id.tv_area);
            convertView.setTag(holder);

        }else{
            holder= (Holder) convertView.getTag();
        }
        holder.areatext.setText(dataList.get(position));
        return convertView;
    }
    class Holder{
        TextView areatext;
    }
}
