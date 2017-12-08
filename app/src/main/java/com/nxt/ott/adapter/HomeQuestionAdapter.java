package com.nxt.ott.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.domain.Answer;

import java.util.List;

/**
 * Created by huqiang on 2017/7/5 13:32.
 */

public class HomeQuestionAdapter extends BaseQuickAdapter<Answer.DataBean,BaseViewHolder> {
    public HomeQuestionAdapter(List<Answer.DataBean> data) {
        super(R.layout.home_question_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Answer.DataBean item) {
        helper.setText(R.id.tv_label,"标题:"+item.getTname());
        helper.setText(R.id.tv_content, TextUtils.isEmpty(item.getHinfo())?"":"专家回复: "+item.getHinfo());
//        RecyclerView rv_pic =  helper.getView(R.id.rv_pic);
//        rv_pic.setLayoutManager(new GridLayoutManager(mContext,4));
//        final List<String> imgs = new ArrayList<>();
//        if (!item.getImg().contains(",")){
//            imgs.add(item.getImg());
//        }else {
//            String[] imgStr = item.getImg().split(",");
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
//                    intent1.putExtra("imgurl",Constant.BASE_URL_EXPERTER+imgs.get(position));
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
