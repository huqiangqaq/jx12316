 package com.nxt.ott.fragment;

 import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import android.widget.ImageButton;
 import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nxt.ott.R;



public class ExperterInfoFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tv_name,tv_type,tv_zixunfuwu,tv_remarks;
    private ImageButton tv_submit,tv_submit_all;
    private ImageView iv_atavar;
    private onSubmitClick onSubmitClick;
    private onSubmitAllClick onSubmitAllClick;
    private String tel;//专家账号

    public void setOnSubmitClick(ExperterInfoFragment.onSubmitClick onSubmitClick) {
        this.onSubmitClick = onSubmitClick;
    }

    public void setOnSubmitAllClick(ExperterInfoFragment.onSubmitAllClick onSubmitAllClick) {
        this.onSubmitAllClick = onSubmitAllClick;
    }

    public ExperterInfoFragment() {
        // Required empty public constructor
    }

    public static ExperterInfoFragment newInstance(String param1, String param2,String param3,String param4,String avatar,String tel) {
        ExperterInfoFragment fragment = new ExperterInfoFragment();
        Bundle args = new Bundle();
        args.putString("name", param1);
        args.putString("type", param2);
        args.putString("zixunfuwu",param3);
        args.putString("remarks",param4);
        args.putString("avatar",avatar);
        args.putString("tel",tel);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experter_info,container,false);
        tv_name = (TextView) view.findViewById(R.id.name);
        tv_type = (TextView) view.findViewById(R.id.type);
        tv_zixunfuwu = (TextView) view.findViewById(R.id.tv_zixunfuwu);
        tv_remarks = (TextView) view.findViewById(R.id.tv_remarks);
        iv_atavar = (ImageView) view.findViewById(R.id.iv_avatar);
        tv_submit = (ImageButton) view.findViewById(R.id.tv_submit);
        tv_submit_all = (ImageButton) view.findViewById(R.id.tv_submit_all);
        tv_submit.setOnClickListener(this);
        tv_submit_all.setOnClickListener(this);
        Bundle bundle = getArguments();
        if (bundle!=null){
            tv_name.setText(bundle.getString("name"));
            tv_type.setText(bundle.getString("type"));
            tv_zixunfuwu.setText(bundle.getString("zixunfuwu"));
            tv_remarks.setText(bundle.getString("remarks"));
            tel = bundle.getString("tel");
            String avator = bundle.getString("avatar");
            Glide.with(getActivity()).load(avator).placeholder(R.mipmap.header_update).crossFade().into(iv_atavar);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit:
                onSubmitClick.submit(tel);
                break;
            case R.id.tv_submit_all:
                onSubmitAllClick.submitAll();
                break;
        }
    }


    public interface onSubmitClick{
        void submit(String point);
    }

    public interface onSubmitAllClick{
        void submitAll();
    }

}
