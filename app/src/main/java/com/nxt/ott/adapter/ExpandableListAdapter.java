package com.nxt.ott.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.activity.WebActivity;
import com.nxt.ott.activity.pest.PestDetailActivity;
import com.nxt.ott.activity.pest.PestDetailActivity2;
import com.nxt.ott.domain.Pest;
import com.nxt.ott.domain.PestType;
import com.nxt.ott.domain.Pests;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.view.CustomGridView;

import java.util.List;

/**
 * Created by xpeng on 2016/10/11.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> groupList;
    private List<List<Pests.RowsBean.ArrBean>> childList;

    private GridView gridView;

    public ExpandableListAdapter(Context context, List<String> groupList, List<List<Pests.RowsBean.ArrBean>> childList){
        mContext=context;

        this.groupList=groupList;
        this.childList=childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_pest_type, null);
        }
        ImageView ivGroup = (ImageView) convertView.findViewById(R.id.pest_item_iv_right);
        TextView tvGroup = (TextView) convertView.findViewById(R.id.pest_item_tv_pesttype);

        if (isExpanded) {
            ivGroup.setImageResource(R.mipmap.icon_pest_right_open);
        } else {
            ivGroup.setImageResource(R.mipmap.icon_pest_right);
        }

        tvGroup.setText(groupList.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView=View.inflate(mContext,R.layout.item_pest_pest,null);
        }
        gridView = (GridView) convertView;
        CustomGridViewAdapter customGridViewAdapter=new CustomGridViewAdapter(mContext,childList.get(groupPosition));
        gridView.setAdapter(customGridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.getContext().startActivity(new Intent(mContext, PestDetailActivity2.class)
                        .putExtra(Constant.BCH_PEST_ID,childList.get(groupPosition).get(position).getDispestMapid()+"")
                        .putExtra(Constant.BCH_PEST_IMG,childList.get(groupPosition).get(position).getImgUrl2())
                .putExtra(Constant.BCH_PEST_DETAIL,childList.get(groupPosition).get(position).getTitle()));
//                view.getContext().startActivity(new Intent(mContext, WebActivity.class)
//                .putExtra("title",childList.get(groupPosition).get(position).getTitle())
//                .putExtra("url",String.format(Constant.BCH_CONTENT_URL,childList.get(groupPosition).get(position).getDispestMapid()+"")));
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
