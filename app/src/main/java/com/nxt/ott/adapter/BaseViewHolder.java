package com.nxt.ott.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by huqiang on 2017/12/12 15:35.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public BaseViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static BaseViewHolder get(Context context,ViewGroup parent,int layouId){
        View itemView = LayoutInflater.from(context).inflate(layouId,parent,false);
        BaseViewHolder viewHolder = new BaseViewHolder(context,itemView,parent);
        return viewHolder;
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view==null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public View getConvertView(){
        return mConvertView;
    }
}
