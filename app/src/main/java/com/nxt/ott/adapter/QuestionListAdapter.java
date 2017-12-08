package com.nxt.ott.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nxt.ott.domain.Question;

import java.util.List;

/**
 * Created by huqiang on 2017/4/5 11:03.
 */

public class QuestionListAdapter extends BaseQuickAdapter<Question,BaseViewHolder> {
    public QuestionListAdapter(int layoutResId, List<Question> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Question question) {

    }
}
