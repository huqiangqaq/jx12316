package com.nxt.ott.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.nxt.ott.R;
import com.nxt.ott.domain.DiseaseChooseItem;
import com.nxt.ott.Constant;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;
import com.nxt.zyl.util.ZPreferenceUtils;

import java.util.List;

/**
 * Created by zhangyonglu on 2016/2/1 0001.
 */
public class DiseaseChooseItemAdapter extends ZBaseAdapter<DiseaseChooseItem> {
    public DiseaseChooseItemAdapter(Context context, List<DiseaseChooseItem> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       final Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView=mLayoutInflater.inflate(R.layout.disease_gridview_list,null);
            holder.nameview= (CheckBox) convertView;
            convertView.setTag(holder);
        }else{
          holder= (Holder) convertView.getTag();
        }

       final DiseaseChooseItem diseaseChooseItem=dataList.get(position);
        String itemname=diseaseChooseItem.getSymptomName();
        if(!TextUtils.isEmpty(itemname)) {
                 itemname=itemname.trim().replace(" ","");
                 if(itemname.contains("：")){
                     itemname=itemname.split("：")[1];
                 }else if(itemname.contains("：∧")){
                     itemname=itemname.split("：∧")[1];
                 }else if(itemname.contains(":∧")){
                     itemname=itemname.split(":∧")[1];
                 }
            holder.nameview.setText(itemname);
        }
        holder.nameview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String  itemstr=diseaseChooseItem.getSymptomNum()+"",newcontent;
                String oldcontent= ZPreferenceUtils.getPrefString(Constant.DISEASE_CONTENT, "");
                StringBuilder builder=new StringBuilder();

                if(isChecked){
                    holder.nameview.setBackground(mContext.getResources().getDrawable(R.mipmap.grid_item_unnormal));
                    builder.append(oldcontent);
                    if(TextUtils.isEmpty(oldcontent)){
                        builder.append(itemstr);

                    }else{
                        builder.append(","+itemstr);

                    }
                    ZPreferenceUtils.setPrefString(Constant.DISEASE_CONTENT, builder.toString());

                }else{
                     if(oldcontent.contains(",")){
                         if(oldcontent.split(",")[0].equals(itemstr)){//取消排在第一位的症状
                             newcontent=oldcontent.replace(itemstr+",","");
                         }else{
                             newcontent=oldcontent.replace(","+itemstr,"");
                         }
                     }else{
                         newcontent=oldcontent.replace(itemstr,"");

                     }
                    holder.nameview.setBackground(mContext.getResources().getDrawable(R.mipmap.grid_item_normal));
                    ZPreferenceUtils.setPrefString(Constant.DISEASE_CONTENT, newcontent);

                }

            }
        });

       return convertView;
    }
    class Holder{
       CheckBox nameview;
    }
}
