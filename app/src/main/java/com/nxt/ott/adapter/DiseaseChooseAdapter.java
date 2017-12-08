package com.nxt.ott.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.R;
import com.nxt.ott.domain.DiseaseChooseItem;
import com.nxt.ott.view.CustomGridView;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zhangyonglu on 2016/2/1 0001.
 */
public class DiseaseChooseAdapter extends ZBaseAdapter {
    private List<String> keylist;
    private JSONObject inforjson;

    public DiseaseChooseAdapter(Context context, List<String> dataList, JSONObject jsonObject) {

        super(context, dataList);
        this.keylist = dataList;
        this.inforjson = jsonObject;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = mLayoutInflater.inflate(R.layout.disease_listview_list, null);
            holder.nameview = (TextView) convertView.findViewById(R.id.tv_disease_choose_type);
            holder.diseasechooseimg = (ImageView) convertView.findViewById(R.id.img_disease_choose_arrow);
            holder.diseasechooselayout = (RelativeLayout) convertView.findViewById(R.id.layout_disease_choose_type);
            holder.gridView = (CustomGridView) convertView.findViewById(R.id.gridview_disease_choose);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.nameview.setText(keylist.get(position));

        List<DiseaseChooseItem> itemList = null;
        try {
            itemList = new Gson().fromJson(inforjson.getString(keylist.get(position)), new TypeToken<List<DiseaseChooseItem>>() {
            }.getType());
//            holder.gridView.setAdapter(new DiseaseChooseItemAdapter(mContext,itemList));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.gridView.setAdapter(new DiseaseChooseItemAdapter(mContext, itemList));
        holder.diseasechooselayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (!holder.gridView.isShown()) {
                        holder.gridView.setVisibility(View.VISIBLE);

                    } else {
                        holder.gridView.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return convertView;
    }

    class Holder {
        RelativeLayout diseasechooselayout;
        ImageView diseasechooseimg;
        TextView nameview;
        CustomGridView gridView;
    }
}
