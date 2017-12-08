package com.nxt.ott.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.domain.Price;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiang on 2017/1/10 16:01.
 */

public class PriceAdapter extends BaseAdapter{
    private Context mContext;
    private List<Price.RowsBean> prices = new ArrayList<>();
    private LayoutInflater inflater;
    private ViewHolder holder;

    public PriceAdapter(Context mContext, List<Price.RowsBean> prices) {
        this.mContext = mContext;
        this.prices = prices;
        inflater = LayoutInflater.from(mContext);
    }

    public void setPrices(List<Price.RowsBean> prices) {
        this.prices = prices;
    }

    @Override
    public int getCount() {
        return prices.size();
    }

    @Override
    public Object getItem(int position) {
        return prices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_price2,parent,false);
            holder.Agrivarity = (TextView) convertView.findViewById(R.id.agrivarity);
            holder.guige = (TextView) convertView.findViewById(R.id.guige);
            holder.reportMonth2  = (TextView) convertView.findViewById(R.id.reportMonth2);
            holder.price = (TextView) convertView.findViewById(R.id.MarketPrice);
            holder.counit = (TextView) convertView.findViewById(R.id.coUnit);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Price.RowsBean price = prices.get(position);
        holder.guige.setText(price.getGuige());
        holder.Agrivarity.setText(price.getAgrivarity());
        holder.reportMonth2.setText(price.getReportMonth2());
        String curprice = ("0".equalsIgnoreCase(String.valueOf(price.getMarketPrice())))?String.valueOf(price.getFarmerPrice()):String.valueOf(price.getMarketPrice());
        holder.price.setText(curprice);
        holder.counit.setText(price.getCoUnit());
        return convertView;
    }

    private class ViewHolder{
        TextView Agrivarity,guige,reportMonth2,price,counit;
    }
}
