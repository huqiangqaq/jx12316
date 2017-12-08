package com.nxt.ott.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nxt.ott.Constant;
import com.nxt.ott.R;

import java.util.List;

/**
 * Created by huqiang on 2017/3/27 13:19.
 */

public class CommentPictureAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public CommentPictureAdapter(int resId,List<String> data) {
        super(resId,data);
    }


    @Override
    protected void convert(BaseViewHolder holder, String s) {
        if (s.contains("storage")){
            Glide.with(mContext).load(s).crossFade().into((ImageView) holder.getView(R.id.iv_pic));
        }else {
            Glide.with(mContext).load(Constant.BASE_URL_EXPERTER+s).crossFade().into((ImageView) holder.getView(R.id.iv_pic));
        }
        holder.addOnClickListener(R.id.iv_pic);
    }
}
