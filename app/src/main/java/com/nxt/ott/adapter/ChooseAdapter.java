package com.nxt.ott.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nxt.ott.R;
import com.nxt.ott.domain.ChooseExperter;

import java.util.List;

/**
 * Created by huqiang on 2017/12/11 10:50.
 */

public class ChooseAdapter extends BaseQuickAdapter<ChooseExperter.DataBean,BaseViewHolder> {

    public ChooseAdapter() {
        super(R.layout.item_choose);
    }

    public ChooseAdapter(int layoutResId, List<ChooseExperter.DataBean> data) {
        super(R.layout.item_choose, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChooseExperter.DataBean item) {
        Glide.with(mContext).load(item.getApic()).crossFade().into((ImageView) helper.getView(R.id.avator));
        helper.setText(R.id.tv1,"姓名: "+item.getAname());
        helper.setText(R.id.tv2,"专家类型: "+item.getType());
        helper.setText(R.id.tv3,"简介: "+item.getAinfo());
    }
}
