package com.nxt.ott.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.domain.HotExperter;
import com.nxt.zyl.ui.widget.roundedimageview.CustomImageView;

import java.util.List;

/**
 * Created by huqiang on 2017/3/21 11:42.
 */

public class HotExperterAdapter extends BaseQuickAdapter<HotExperter,BaseViewHolder> {


    public HotExperterAdapter( List<HotExperter> data) {
        super(R.layout.item_active_expter, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HotExperter hotExperter) {
        Glide.with(mContext).load(String.format(Constant.IMAGE_URL,hotExperter.getAvator())).crossFade().placeholder(R.mipmap.header_update).into((CustomImageView) baseViewHolder.getView(R.id.iv_avatar));
        baseViewHolder.setText(R.id.name,hotExperter.getName());
        baseViewHolder.setText(R.id.zhiwu,"职务:"+hotExperter.getJishuzhiwu());
        baseViewHolder.setText(R.id.proffessional,"专长:"+hotExperter.getType());
    }
}
