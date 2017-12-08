package com.nxt.ott.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.domain.Pesticide;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;

import java.util.List;

/**
 * Created by zhangyonglu on 2016/3/10 0010.
 */
public class PesticideAdapter extends ZBaseAdapter<Pesticide> {
    public PesticideAdapter(Context context, List<Pesticide> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView= mLayoutInflater.inflate(R.layout.layout_pesticide_list,null);
            holder.tvnumber= (TextView) convertView.findViewById(R.id.tv_number);
            holder.tvtitle= (TextView) convertView.findViewById(R.id.tv_pesticide_title);
            holder.tvcontent= (TextView) convertView.findViewById(R.id.tv_pesticide_content);
            holder.tvcard= (TextView) convertView.findViewById(R.id.tv_pesticide_card);
            convertView.setTag(holder);

        }else{
            holder= (Holder) convertView.getTag();
        }
        Pesticide agHelper=dataList.get(position);
        int index=position+1;
        if(0<index&&index<10) {
            holder.tvnumber.setText("0"+index);
        }else{
            holder.tvnumber.setText(index+"");

        }
        if(!TextUtils.isEmpty(agHelper.getCompanyname())) holder.tvtitle.setText(agHelper.getCompanyname());
        if(!TextUtils.isEmpty(agHelper.getRegistrationnumber())) holder.tvcard.setText("登记证号\t:\t"+agHelper.getRegistrationnumber());
        if(!TextUtils.isEmpty(agHelper.getMedication())) holder.tvcontent.setText(agHelper.getMedication());
        return convertView;
    }
    class Holder{
        TextView tvnumber;
        TextView tvtitle;
        TextView tvcard;
        TextView tvcontent;
    }
}
