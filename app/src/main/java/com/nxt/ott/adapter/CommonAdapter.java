package com.nxt.ott.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by huqiang on 2017/12/12 15:50.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommonAdapter(Context mContext, int mLayoutId, List<T> mDatas) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseViewHolder holder = BaseViewHolder.get(mContext,parent,mLayoutId);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder,position);
    }

    public abstract void convert(BaseViewHolder holder,int position);
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnItemClickListener{
        void  onItemClick(View view,int position);
    }
}
