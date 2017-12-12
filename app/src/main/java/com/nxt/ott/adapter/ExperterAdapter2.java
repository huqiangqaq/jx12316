package com.nxt.ott.adapter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.ui.VideoCallActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.domain.Experter;

import java.util.List;

/**
 * Created by huqiang on 2017/5/12 9:13.
 */

public class ExperterAdapter2 extends BaseQuickAdapter<Experter,BaseViewHolder> {
    private boolean isRecommed;
//    private onExperterClick onExperterClick;
//
//    public void setOnExperterClick(ExperterAdapter2.onExperterClick onExperterClick) {
//        this.onExperterClick = onExperterClick;
//    }

    public ExperterAdapter2(int layoutResId, List<Experter> data, boolean isRecommed) {
        super(layoutResId, data);
        this.isRecommed = isRecommed;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Experter experter) {
        if (!TextUtils.isEmpty(experter.getTitle()) && (experter.getTitle().trim().length() > 0)) {
            helper.getView(R.id.img_header).setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(String.format(Constant.IMAGE_URL, experter.getTitle()), new ImageViewAware((ImageView) helper.getView(R.id.img_header), false));
        } else {
            helper.setImageResource(R.id.img_header,R.mipmap.header_update);
        }
        helper.setText(R.id.tv_experter_introuction_one,mContext.getString(R.string.name) + ":" + experter.getName() + "  " + "\n"
//                + mContext.getString(R.string.phone) + ":" + experter.getTel().substring(0, 3) + "****" + experter.getTel().substring(7, experter.getTel().length()) + "\n"
                + mContext.getString(R.string.post) + ":" + experter.getJishuzhiwu() + "\n"
                + mContext.getString(R.string.expertise) + ":" + experter.getYewuzhuanchang() + "\n"
                + mContext.getString(R.string.unit) + ":" + experter.getWorkUnit());

        helper.getView(R.id.ll_ex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.getView(R.id.layout_call_experter).getVisibility() == View.GONE) {
                    helper.getView(R.id.layout_call_experter).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.layout_call_experter).setVisibility(View.GONE);
                }
            }
        });
        helper.getView(R.id.layout_call_experter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + "12316");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                mContext.startActivity(intent);
            }
        });
//        helper.getView(R.id.tv_experter_picture).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onExperterClick.onExperterClickListener(v,helper.getLayoutPosition());
//            }
//        });
        helper.getView(R.id.tv_experter_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideoCall(experter);
            }
        });
    }

    private void startVideoCall(Experter experter) {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(mContext, R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            mContext.startActivity(new Intent(mContext, VideoCallActivity.class).putExtra("username",experter.getUid())
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
//            inputMenu.hideExtendMenuContainer();
        }
    }


//    public interface onExperterClick{
//        void onExperterClickListener(View v,int position);
//    }
}
