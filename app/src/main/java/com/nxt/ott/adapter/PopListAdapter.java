package com.nxt.ott.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;

import java.util.List;

/**
 * Created by zhangyonglu on 2016/3/14 0014.
 */
public class PopListAdapter extends ZBaseAdapter<String> {
    public PopListAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          convertView=mLayoutInflater.inflate(R.layout.layout_pop_list,null);
          TextView nameview= (TextView) convertView;
          nameview.setText(dataList.get(position));
          return convertView;

    }

}
