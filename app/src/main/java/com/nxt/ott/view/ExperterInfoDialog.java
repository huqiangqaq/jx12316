package com.nxt.ott.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.ui.VideoCallActivity;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.expertUpdate.AskActivity;

/**
 * Created by huqiang on 2017/4/20 10:34.
 */

public class ExperterInfoDialog extends BaseBottomDialogForV4{
    private ImageView iv_atavar;
    private TextView tv_name,tv_type,tv_zixunfuwu,tv_remarks,tv_experter_tel,tv_experter_picture,tv_experter_video;
    public ExperterInfoDialog() {
    }

    public static ExperterInfoDialog newInstance(Bundle arg) {
        ExperterInfoDialog fragment = new ExperterInfoDialog();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_experter_info2;
    }

    @Override
    public void bindView(View v) {
        tv_name = (TextView) v.findViewById(R.id.name);
        tv_type = (TextView) v.findViewById(R.id.type);
        tv_zixunfuwu = (TextView) v.findViewById(R.id.tv_zixunfuwu);
        tv_remarks = (TextView) v.findViewById(R.id.tv_remarks);
        iv_atavar = (ImageView) v.findViewById(R.id.iv_avatar);
        tv_experter_tel = (TextView) v.findViewById(R.id.tv_experter_tel);
        tv_experter_picture = (TextView) v.findViewById(R.id.tv_experter_picture);
        tv_experter_video = (TextView) v.findViewById(R.id.tv_experter_video);
        final Bundle bundle = getArguments();
        if (bundle!=null){
            tv_name.setText(bundle.getString("name"));
            tv_type.setText(bundle.getString("type"));
            tv_zixunfuwu.setText(bundle.getString("zixunfuwu"));
            tv_remarks.setText(bundle.getString("remarks"));
            String avator = bundle.getString("avator");
            Glide.with(getActivity()).load(String.format(Constant.IMAGE_URL,avator)).placeholder(R.mipmap.header_update).crossFade().into(iv_atavar);
            tv_experter_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVideoCall(bundle.getString("uid"));
                }
            });
        }
        tv_experter_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + "12316");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                getActivity().startActivity(intent);
            }
        });

        tv_experter_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), AskActivity.class).putExtra("isExperter",true).putExtra("uid",bundle.getString("uid"))
                        .putExtra("type",bundle.getString("type")).putExtra("name",bundle.getString("name")));
            }
        });


    }
    private void startVideoCall(String experter) {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            getActivity().startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username",experter)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
//            inputMenu.hideExtendMenuContainer();
        }
    }
}
