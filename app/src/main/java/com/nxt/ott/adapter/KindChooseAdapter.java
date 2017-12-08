package com.nxt.ott.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.view.RoundedLetterView;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;
import com.nxt.zyl.ui.roundmcolor.RandomColor;

import java.util.List;

/**
 * Created by zhangyonglu on 2016/3/15 0015.
 */
public class KindChooseAdapter extends ZBaseAdapter<String> {
    RandomColor randomColor = new RandomColor();
    int[] colors;

    public KindChooseAdapter(Context context, List<String> dataList) {

        super(context, dataList);
        colors = randomColor.randomColor(dataList.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = mLayoutInflater.inflate(R.layout.layout_kindchoose_item, null, false);
            holder.roundedLetterView = (RoundedLetterView) convertView.findViewById(R.id.roundview_kind);
            holder.itemtextview = (TextView) convertView.findViewById(R.id.tv_kind_item_name);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.roundedLetterView.setTitleText(dataList.get(position).substring(0, 1));
        holder.roundedLetterView.setBackgroundColor(colors[position]);
        holder.itemtextview.setText(dataList.get(position));
        return convertView;
    }

    class Holder {
        RoundedLetterView roundedLetterView;
        TextView itemtextview;
    }
}
