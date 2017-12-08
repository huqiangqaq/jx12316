package com.nxt.ott.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.domain.ExpertAdvise;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiang on 2016/11/1 16:23.
 */

public class ExperterAdviseAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExpertAdvise.DataBean> list = new ArrayList<>();

    public ExperterAdviseAdapter(Context mContext, List<ExpertAdvise.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public List<ExpertAdvise.DataBean> getList() {
        return list;
    }

    public void setList(List<ExpertAdvise.DataBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.experter_advise_item,parent,false);
            holder.ll_experts_advise  = (LinearLayout) convertView.findViewById(R.id.ll_experts_advise);
            holder.tv_experter_advise_time = (TextView) convertView.findViewById(R.id.tv_experter_advise_time);
            holder.tv_experter_suggest = (TextView) convertView.findViewById(R.id.tv_experter_suggest);
            holder.experter_advise_line = convertView.findViewById(R.id.experter_advise_line);
            holder.stage = (TextView) convertView.findViewById(R.id.stage);
            holder.tv_author = convertView.findViewById(R.id.tv_author);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position==0){
            holder.ll_experts_advise.setBackgroundResource(R.mipmap.ll_experter_advise);
        }else {
            holder.ll_experts_advise.setBackgroundResource(R.color.white);
        }

        holder.tv_experter_advise_time.setText(timestampToStr(list.get(position).getTime()));
//        holder.tv_experter_advise.setText(list.get(position).getSuggest());
        String content = list.get(position).getContent();
        if (content.contains("<")){
            holder.tv_experter_suggest.setText("农事提醒:\n "+ Html.fromHtml(content));
        }else {
            holder.tv_experter_suggest.setText("农事提醒:\n "+content);
        }

        holder.stage.setText(list.get(position).getTittle());
        holder.tv_author.setText(list.get(position).getAuthor());
        return convertView;
    }

    private class ViewHolder{
        LinearLayout ll_experts_advise;
        TextView tv_experter_advise_time,tv_experter_suggest,stage,tv_author;
        View experter_advise_line;

    }
    private String timestampToStr(long timestamp){
        Timestamp ts = new Timestamp(timestamp);
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(ts);
    }
}
