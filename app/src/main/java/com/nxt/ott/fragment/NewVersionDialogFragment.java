package com.nxt.ott.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.server.DownLoadService;
import com.nxt.ott.util.ToastUtils;

/**
 * Created by huqiang on 2017/4/20 9:14.
 */

public class NewVersionDialogFragment extends DialogFragment {
    private String mVersionCode;
    private String mDescription;


    public static NewVersionDialogFragment newInstance(String versionCode, String description) {
        NewVersionDialogFragment fragment = new NewVersionDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("version", versionCode);
        bundle.putString("description", description);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
        Bundle bundle = getArguments();
        mVersionCode = bundle.getString("version");
        mDescription = bundle.getString("description");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_new_version_dialog,container,false);

        TextView versionTipText = (TextView) view.findViewById(R.id.version_tip_text);
        versionTipText.setText(String.format("%s版本发布", mVersionCode));
        TextView versionDescriptionText = (TextView) view.findViewById(R.id.version_description_text);
        versionDescriptionText.setText(mDescription);

        Button button = (Button) view.findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(),DownLoadService.class));
                getDialog().dismiss();
                ToastUtils.showShort(getActivity(),"江西12316正在下载...请查看通知栏");
            }
        });
        ImageView cancel = (ImageView) view.findViewById(R.id.calcel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog!=null){
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels*0.75),ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
