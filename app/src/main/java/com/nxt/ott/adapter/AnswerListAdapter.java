package com.nxt.ott.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nxt.ott.R;
import com.nxt.ott.domain.Answer;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeTextView;

/**
 * Created by huqiang on 2017/3/21 14:05.
 */

public class AnswerListAdapter extends BaseQuickAdapter<Answer.DataBean,BaseViewHolder> {
    private boolean mIsExperter;
    public AnswerListAdapter(List<Answer.DataBean> data, boolean isExperter) {
        super(R.layout.item_answer_list,data);
        mIsExperter = isExperter;
    }

    @Override
    protected void convert(BaseViewHolder holder, Answer.DataBean answer) {
        holder.setText(R.id.title,"标题:"+answer.getTname());
        holder.setText(R.id.label,"标签："+answer.getTbiaoqian());
//        holder.setText(R.id.attention_count,"关注数:"+answer.getAttentionCount());

        if (TextUtils.equals("专家已回复",answer.getHtype())){
            holder.setText(R.id.status,"专家已回复");
            holder.setTextColor(R.id.status,R.color.status_y);
            holder.setText(R.id.reply_time,"回复时间: "+answer.getHtime());
            ((BGABadgeTextView)holder.getView(R.id.title)).hiddenBadge();
        }else {
            holder.setTextColor(R.id.status,R.color.status_n);
            holder.setText(R.id.status,"专家未回复");
//            holder.setText(R.id.reply_time,"提问时间: "+answer.getIssueDate());
            ((BGABadgeTextView)holder.getView(R.id.title)).showCirclePointBadge();
        }
        holder.setText(R.id.reply,"问题详情: "+answer.getHinfo());
//        if (mIsExperter){
//            holder.setVisible(R.id.iv_answer, true);
//            holder.setBackgroundRes(R.id.iv_answer,R.mipmap.icon_hd);
//        }else {
//            if (TextUtils.equals("1",answer.getIsAttention())){
//                holder.setBackgroundRes(R.id.btn_guanzhu,R.mipmap.icon_gz2);
//            }else {
//                holder.setBackgroundRes(R.id.btn_guanzhu,R.mipmap.icon_gz);
//            }
//            holder.getView(R.id.iv_answer).setVisibility(View.GONE);
//        }
//        holder.addOnClickListener(R.id.btn_guanzhu);
    }
}
