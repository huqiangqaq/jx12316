package com.nxt.ott.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.domain.RecommentExperter;

import java.util.List;

/**
 * Created by huqiang on 2017/3/23 10:51.
 */

public class ExpertCommentAdapter extends BaseQuickAdapter<RecommentExperter.RowsBean,BaseViewHolder> {
    public ExpertCommentAdapter(List<RecommentExperter.RowsBean> data) {
        super(R.layout.item_expert_commet,data);
    }

    @Override
    protected void convert(BaseViewHolder holder, RecommentExperter.RowsBean rowsBean) {
        holder.setText(R.id.name,rowsBean.getName());
        holder.setText(R.id.type,rowsBean.getYewuzhuanchang());
        Glide.with(mContext).load(String.format(Constant.IMAGE_URL, rowsBean.getTitle())).placeholder(R.mipmap.header_update).crossFade().into((ImageView) holder.getView(R.id.avatar));
    }
}
