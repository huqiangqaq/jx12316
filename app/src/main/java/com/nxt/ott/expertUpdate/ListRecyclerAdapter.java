/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nxt.ott.expertUpdate;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.domain.Answer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 2016/7/14.
 *
 * @author Yan Zhenjie.
 */
public class ListRecyclerAdapter extends BaseQuickAdapter<Answer.DataBean,BaseViewHolder> {

    private List<Answer.DataBean> mData = new ArrayList<>();
    public ListRecyclerAdapter(List<Answer.DataBean> data ) {
        super(R.layout.item_answer_list_,data);
        this.mData = data;
    }

    public void setmData(List<Answer.DataBean> mData) {
        this.mData = mData;
    }

    @Override
    protected void convert(BaseViewHolder holder, Answer.DataBean answer) {
        holder.setText(R.id.title,answer.getTname());
        if (!TextUtils.isEmpty(answer.getBiaoqian())){
            holder.setText(R.id.label,answer.getBiaoqian());
        }else {
            holder.getView(R.id.ll_type).setVisibility(View.GONE);
        }
        if (TextUtils.equals("专家已回复",answer.getHtype())){
            holder.setText(R.id.status,"专家已回复");
            holder.setText(R.id.time,"回复时间: "+answer.getHtime());
            ((TextView)holder.getView(R.id.status)).setTextColor(mContext.getResources().getColor(R.color.status_y));
        }else {
            holder.setTextColor(R.id.status,R.color.status_n);
            holder.setText(R.id.status,"专家未回复");
            ((TextView)holder.getView(R.id.status)).setTextColor(mContext.getResources().getColor(R.color.status_n));
        }
        holder.setText(R.id.reply,answer.getHinfo());
//        if (mIsExperter){
////            holder.setVisible(R.id.iv_answer, true);
////            holder.setBackgroundRes(R.id.iv_answer,R.mipmap.icon_hd);
//        }else {
//            if (TextUtils.equals("1",answer.getIsAttention())){
////                holder.setBackgroundRes(R.id.btn_guanzhu,R.mipmap.icon_gz2);
//                Drawable drawable= mContext.getResources().getDrawable(R.mipmap.star01);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                ((TextView)holder.getView(R.id.star)).setCompoundDrawables(drawable,null,null,null);
//                holder.setText(R.id.star,"已关注");
//            }else {
//                Drawable drawable= mContext.getResources().getDrawable(R.mipmap.star);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                ((TextView)holder.getView(R.id.star)).setCompoundDrawables(drawable,null,null,null);
//                holder.setText(R.id.star,"未关注");
////                holder.setBackgroundRes(R.id.btn_guanzhu,R.mipmap.icon_gz);
//            }
////            holder.getView(R.id.iv_answer).setVisibility(View.GONE);
//        }
//        holder.addOnClickListener(R.id.star);
//        RecyclerView rv_pic =  holder.getView(R.id.rv_pic);
//        rv_pic.setLayoutManager(new GridLayoutManager(mContext,4));
//        final List<String> imgs = new ArrayList<>();
//        if (!answer.getImg().contains(",")){
//            imgs.add(answer.getImg());
//        }else {
//            String[] imgStr = answer.getImg().split(",");
//            imgs.addAll(Arrays.asList(imgStr));
//        }
//        if (!"".equals(imgs.get(0))){
//            rv_pic.setVisibility(View.VISIBLE);
//            PicAdapter picAdapter = new PicAdapter(imgs);
//            rv_pic.setAdapter(picAdapter);
//            picAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    Intent intent1 = new Intent(mContext, PictureScan.class);
//                    intent1.putExtra("imgurl", Constant.BASE_URL_EXPERTER+imgs.get(position));
//                    mContext.startActivity(intent1);
//                }
//            });
//        }else {
//            rv_pic.setVisibility(View.GONE);
//        }
    }

    public class PicAdapter extends BaseQuickAdapter<String,BaseViewHolder>{


        public PicAdapter(List<String> data) {
            super(R.layout.item_question_img,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            Glide.with(mContext).load(Constant.BASE_URL_EXPERTER+item).crossFade().placeholder(R.mipmap.header_update).into((ImageView) helper.getView(R.id.id_index_gallery_item_image));
        }
    }
}
