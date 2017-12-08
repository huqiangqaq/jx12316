package com.nxt.ott.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.domain.Experter;
import com.nxt.ott.expertUpdate.AskActivity;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;
import com.nxt.zyl.ui.widget.roundedimageview.CustomImageView;

import java.util.HashMap;
import java.util.List;


/**
 * Created by zhangyonglu on 2016/4/8 0008.
 */
public class ExperterAdapter extends ZBaseAdapter<Experter> {
    private HashMap<Integer,View> lvmap=new HashMap<>();
    public ExperterAdapter(Context context, List<Experter> dataList) {
        super(context, dataList);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (lvmap.get(position)==null) {
            holder = new Holder();
            convertView = mLayoutInflater.inflate(R.layout.layout_experter_list, null, false);
            holder.userPhoto = (CustomImageView) convertView.findViewById(R.id.img_header);
            holder.mtextintrouctionone = (TextView) convertView.findViewById(R.id.tv_experter_introuction_one);
            holder.calllayout = (LinearLayout) convertView.findViewById(R.id.layout_call_experter);
            convertView.setTag(holder);
            lvmap.put(position,convertView);
        } else {
            convertView=lvmap.get(position);
            holder = (Holder) convertView.getTag();
        }
        final Experter experter = dataList.get(position);
        if (!TextUtils.isEmpty(experter.getTitle()) && (experter.getTitle().trim().length() > 0)) {
            holder.userPhoto.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(String.format(Constant.IMAGE_URL, experter.getTitle()), new ImageViewAware(holder.userPhoto, false));
        } else {
            holder.userPhoto.setImageResource(R.mipmap.header_update);
        }

        holder.mtextintrouctionone.setText(mContext.getString(R.string.name) + ":" + experter.getName() + "  " + "\n"
//                + mContext.getString(R.string.phone) + ":" + experter.getTel().substring(0, 3) + "****" + experter.getTel().substring(7, experter.getTel().length()) + "\n"
                + mContext.getString(R.string.post) + ":" + experter.getJishuzhiwu() + "\n"
                + mContext.getString(R.string.expertise) + ":" + experter.getYewuzhuanchang() + "\n"
                + mContext.getString(R.string.unit) + ":" + experter.getWorkUnit());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.calllayout.getVisibility() == View.GONE) {
                    holder.calllayout.setVisibility(View.VISIBLE);
                } else {
                    holder.calllayout.setVisibility(View.GONE);
                }
//                if (ZPreferenceUtils.getPrefBoolean(Constant.IS_LOGIN, false)) {
//                    mContext.startActivity(new Intent(mContext, ChatActivity.class).putExtra("userId", experter.getTel()).putExtra("nickname", experter.getName()));
//                } else {
//                    ZToastUtils.showShort(mContext, R.string.please_login_first);
//                }

            }
        });
        holder.calllayout.findViewById(R.id.tv_experter_tel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + "12316");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                mContext.startActivity(intent);
            }
        });
//        holder.calllayout.findViewById(R.id.tv_experter_msg).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + experter.getTel()));
//                mContext.startActivity(intent);
//            }
//        });
//        holder.calllayout.findViewById(R.id.tv_experter_history).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ZPreferenceUtils.getPrefBoolean(Constant.IS_LOGIN, false)) {
//                    mContext.startActivity(new Intent(mContext, ChatActivity.class).putExtra("userId", experter.getTel()).putExtra("nickname", experter.getName()));
//                } else {
//                    ZToastUtils.showShort(mContext, R.string.please_login_first);
//                }
//            }
//        });
        holder.calllayout.findViewById(R.id.tv_experter_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AskActivity.class).putExtra("isExperter",true).putExtra("tel",dataList.get(position).getTel())
                .putExtra("type",dataList.get(position).getYewuzhuanchang()).putExtra("name",dataList.get(position).getName()));
            }
        });
        return convertView;
    }

    private class Holder {
        LinearLayout calllayout;
        TextView mtextintrouctionone;
        CustomImageView userPhoto;
    }
}