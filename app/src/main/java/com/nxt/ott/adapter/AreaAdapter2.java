package com.nxt.ott.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.domain.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyonglu on 2016/2/17 0017.
 */
public class AreaAdapter2 extends ArrayAdapter<Area> implements SectionIndexer {
    List<Area> mlist;
    Context mcontext;
    List<String> list;

    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    public AreaAdapter2(Context context,int res,List<Area> list){
        super(context,res,list);
        this.mlist=list;
        this.mcontext=context;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Area getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.activity_area_list,null);
            holder.nameview= (TextView) convertView.findViewById(R.id.tv_name);
            holder.headerview= (TextView) convertView.findViewById(R.id.tv_header);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        Area area=mlist.get(position);
        String header=area.getHeader();
        if (position == 0 || header != null && !header.equals(getItem(position - 1).getHeader())) {
            if ("".equals(header)) {
                holder.headerview.setVisibility(View.GONE);
            } else {
                holder.headerview.setVisibility(View.VISIBLE);
                holder.headerview.setText(header);
            }
        } else {
            holder.headerview.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(area.getName())) {
            holder.nameview.setText(area.getName());
        }
        return convertView;
    }

    @Override
    public Object[] getSections() {
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        int count = getCount();
        list = new ArrayList<String>();
        list.add(mcontext.getString(R.string.search_new));
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 1; i < count; i++) {

            String letter = getItem(i).getHeader();
            // System.err.println("contactadapter getsection getHeader:" + letter + " name:" + getItem(i).getUsername());
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionOfSection.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }

    class Holder{
        TextView nameview;
        TextView headerview;
    }

}
