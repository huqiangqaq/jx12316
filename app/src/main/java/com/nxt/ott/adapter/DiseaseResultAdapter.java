package com.nxt.ott.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.domain.DiseaseResult;
import com.nxt.ott.view.RoundedLetterView;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;

import java.util.List;

/**
 * Created by zhangyonglu on 2016/3/15 0015.
 */
public class DiseaseResultAdapter extends ZBaseAdapter<DiseaseResult> {
    public DiseaseResultAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView=mLayoutInflater.inflate(R.layout.layout_disease_result_list,null,false);
            holder.roundedLetterView= (RoundedLetterView) convertView.findViewById(R.id.roundview_disease_result);
            holder.diseasenametext= (TextView) convertView.findViewById(R.id.tv_disease_name);
            holder.diseasedefinitiontext= (TextView) convertView.findViewById(R.id.tv_disease_definition);
            holder.diseasecuretext= (TextView) convertView.findViewById(R.id.tv_cure_method);
            holder.diseasepreventtext= (TextView) convertView.findViewById(R.id.tv_prevention_measures);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }

        DiseaseResult diseaseResult=dataList.get(position);
        if(!TextUtils.isEmpty(diseaseResult.getResult())) holder.diseasenametext.setText(diseaseResult.getResult());
        if(!TextUtils.isEmpty(diseaseResult.getDefinition())) holder.diseasedefinitiontext.setText(diseaseResult.getDefinition());
        if(!TextUtils.isEmpty(diseaseResult.getCure())) holder.diseasecuretext.setText(diseaseResult.getCure());
        if(!TextUtils.isEmpty(diseaseResult.getPrevent())) holder.diseasepreventtext.setText(diseaseResult.getPrevent());
        holder.roundedLetterView.setTitleText((position+1)+"");
        return convertView;
    }
    class Holder{
        RoundedLetterView roundedLetterView;
        TextView diseasenametext;
        TextView diseasedefinitiontext;
        TextView diseasecuretext;
        TextView diseasepreventtext;

    }
}
