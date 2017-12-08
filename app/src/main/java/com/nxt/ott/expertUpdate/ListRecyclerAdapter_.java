package com.nxt.ott.expertUpdate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.domain.BaseType;

import java.util.List;

/**
 * Created by huqiang on 2017/6/14 10:18.
 */

public class ListRecyclerAdapter_ extends RecyclerView.Adapter<ListRecyclerAdapter_.DefineViewHolder> {
    private List<BaseType> list;

    private  onBottomItemClickListener onBottomItemClickListener;

    public void setOnBottomItemClickListener(ListRecyclerAdapter_.onBottomItemClickListener onBottomItemClickListener) {
        this.onBottomItemClickListener = onBottomItemClickListener;
    }

    public ListRecyclerAdapter_(List<BaseType> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(DefineViewHolder viewHolder, final int position) {
        viewHolder.setData(list.get(position).getAbiaoqian());
        viewHolder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBottomItemClickListener.onBottomItemClick(position,list.get(position).getId());
            }
        });
    }

    @Override
    public DefineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new DefineViewHolder(view);
    }

    static class DefineViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public DefineViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(String data) {
            tvTitle.setText(data);
        }
    }

    interface onBottomItemClickListener{
        void onBottomItemClick(int postion,String id);
    }
}
