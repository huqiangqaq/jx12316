package com.nxt.ott.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.domain.ExpertAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiang on 2016/11/3 15:40.
 */

public class ExperterAnswerAdapter extends BaseAdapter {
    private Context mcontext;
    private List<ExpertAnswer.RowsBean> list = new ArrayList<>();

    public ExperterAnswerAdapter(Context mcontext, List<ExpertAnswer.RowsBean> list) {
        this.mcontext = mcontext;
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
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.experter_answer_item,parent,false);
            holder.tv_answer_item_question = (TextView) convertView.findViewById(R.id.tv_answer_item_question);
            holder.tv_answer_item_answer = (TextView) convertView.findViewById(R.id.tv_answer_item_answer);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_answer_item_question.setText(list.get(position).getEquestion());
        holder.tv_answer_item_answer.setText("答:"+list.get(position).getEanswer());
        return convertView;
    }

    private class ViewHolder{
       TextView tv_answer_item_question,tv_answer_item_answer;

    }
}
