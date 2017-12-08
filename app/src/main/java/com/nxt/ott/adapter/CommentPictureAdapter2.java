package com.nxt.ott.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nxt.ott.Constant;
import com.nxt.ott.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiang on 2017/3/28 9:54.
 */

public class CommentPictureAdapter2 extends RecyclerView.Adapter<CommentPictureAdapter2.MyHolder> {

    private Context mContext;
    private List<String> list = new ArrayList<>();
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public CommentPictureAdapter2(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.item_comment_picture2,parent,false);
        return new MyHolder(item);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        Glide.with(mContext).load(Constant.BASE_URL_EXPERTER+list.get(position)).crossFade().into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(v,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        private ImageView iv;

        public MyHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_pic);
        }
    }
}
