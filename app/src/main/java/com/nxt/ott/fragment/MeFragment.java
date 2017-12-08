package com.nxt.ott.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.nxt.ott.Constant;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.activity.account.FeedBackActivity;
import com.nxt.ott.activity.account.MyLoginActivity;
import com.nxt.ott.activity.account.UserProfileActivity;
import com.nxt.ott.activity.titlebottom.AboutUsActivity;
import com.nxt.ott.activity.titlebottom.EwmActivity;
import com.nxt.ott.util.DataCleanManager;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.util.UpdateManager;
import com.nxt.zyl.ui.fragment.ZBaseFragment;
import com.nxt.zyl.ui.widget.roundedimageview.CustomImageView;
import com.nxt.zyl.util.ZPreferenceUtils;

import java.io.File;

import butterknife.ButterKnife;

/**
 * Created by xpeng on 2016/9/23.
 */

public class MeFragment extends ZBaseFragment {
    //测试1
    private DataCleanManager dataCleanManager;
    private File cachfile;
    long filesize;
    ProgressDialog pd;
    TextView mTvUserName;
    CustomImageView iv_user_photo;
    private Switch sw_guide;

    @Override
    protected void initView(View view) {
        cachfile = new File(Environment.getExternalStorageDirectory(), Constant.CASHFILENAME);
        if (!cachfile.exists())
            cachfile.mkdirs();
            try {
            filesize = DataCleanManager.getFolderSize(cachfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTvUserName=(TextView)view.findViewById(R.id.tv_user_name);
        iv_user_photo = (CustomImageView) view.findViewById(R.id.iv_user_photo);
        sw_guide = (Switch) view.findViewById(R.id.sw_guide);
        view.findViewById(R.id.ll_mine_collection).setOnClickListener(this);
        view.findViewById(R.id.ll_mine_clear_cache).setOnClickListener(this);
        view.findViewById(R.id.ll_mine_update).setOnClickListener(this);
        view.findViewById(R.id.ll_mine_feed_back).setOnClickListener(this);
        view.findViewById(R.id.ll_mine_about).setOnClickListener(this);
        view.findViewById(R.id.ll_mine_share).setOnClickListener(this);
        view.findViewById(R.id.ll_mine_recommend).setOnClickListener(this);
//        view.findViewById(R.id.ll_mine_set).setOnClickListener(this);
        view.findViewById(R.id.ll_user_info).setOnClickListener(this);
        view.findViewById(R.id.btn_logout).setOnClickListener(this);
        sw_guide.setOnClickListener(this);
        if (ZPreferenceUtils.getPrefBoolean(Constant.IS_OPEN_GUIDE_HOME,false)){
            sw_guide.setChecked(true);
        }else {
            sw_guide.setChecked(false);
        }
        if ("".equals(ZPreferenceUtils.getPrefString(Constant.NICKNAME,""))){
            mTvUserName.setText(ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
        }else {
            mTvUserName.setText(ZPreferenceUtils.getPrefString(Constant.NICKNAME,""));
        }
        Glide.with(getActivity()).load(Constant.BASE_URL_EXPERTER+ZPreferenceUtils.getPrefString(Constant.AVATOR,"")).crossFade().placeholder(R.mipmap.mine_photo).into(iv_user_photo);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_mine_collection:
                startActivity(new Intent(getActivity(), UserProfileActivity.class));
                break;
            case R.id.ll_mine_clear_cache:
                pd = new ProgressDialog(getActivity());
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage("正在清理缓存....");
                pd.show();
                DataCleanManager.cleanApplicationData(getActivity());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataCleanManager.deleteFolderFile(cachfile.getAbsolutePath(), false);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {

                        }
                        try {
                            if (DataCleanManager.getFolderSize(cachfile) == 0) {
                                handler.sendEmptyMessage(0);
                            } else {
                                handler.sendEmptyMessage(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.ll_mine_update:
                new UpdateManager(getActivity()).checkUpdate();
                break;
            case R.id.ll_mine_feed_back:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.ll_mine_about:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.ll_mine_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "江西12316这款应用真是太棒了，" +
                        "在线咨询专家，自主诊断，电商服务，即时农业资讯，丰富的视频库，" +
                        "全面的便民服务等功能一应俱全。我正在用，你也赶快加入吧！" +
                        "戳我下载：http://d.54vc.com/dak");
                shareIntent.setType("text/plain");
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
            case R.id.ll_mine_recommend:
                startActivity(new Intent(getActivity(), EwmActivity.class));
                break;
//            case R.id.ll_mine_set:
////                if (!DemoHelper.getInstance().isLoggedIn()) {
////                    ZToastUtils.showShort(getActivity(), "您还没有登陆，请先登录");
////                    ZPreferenceUtils.setPrefBoolean(Constant.IS_LOGIN,false);
////                    startActivity(new Intent(getActivity(), MyLoginActivity.class));
////                } else {
////                    startActivity(new Intent(getActivity(), SetActivity.class));
////                }
//                break;
            case R.id.ll_user_info:
//                if (!DemoHelper.getInstance().isLoggedIn()){
//                    ZToastUtils.showShort(getActivity(),"您还没有登陆,请先登陆");
//                    ZPreferenceUtils.setPrefBoolean(Constant.IS_LOGIN,false);
//                    startActivity(new Intent(getActivity(),MyLoginActivity.class));
//                }else {
//                    startActivity(new Intent(getActivity(), UserProfileActivity.class).putExtra("setting", true)
//                            .putExtra("username", EMClient.getInstance().getCurrentUser()));
//                }
                break;
             case  R.id.btn_logout:
                showdeletedialog();
                break;
            case R.id.sw_guide:
                    if (sw_guide.isChecked()){
                        ZPreferenceUtils.setPrefBoolean(Constant.IS_OPEN_GUIDE_HOME,true);
//                        ZPreferenceUtils.setPrefBoolean(Constant.IS_OPEN_GUDEI_EXPER,true);
                    }else {
                        ZPreferenceUtils.setPrefBoolean(Constant.IS_OPEN_GUIDE_HOME,false);
//                        ZPreferenceUtils.setPrefBoolean(Constant.IS_OPEN_GUDEI_EXPER,false);
                    }
                    break;
            default:
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "释放了" + filesize + "Kb空间", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "清理失败", Toast.LENGTH_SHORT).show();
                    break;
            }
            pd.dismiss();
            super.handleMessage(msg);
        }
    };
    private void showdeletedialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("江西12316");
        builder.setMessage("确定要退出12316么，我还可以做的更多哦");
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cleandatas();
                logout();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void logout() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        // 重新显示登陆页面
                        MyApplication.getInstance().exit();
                        startActivity(new Intent(getActivity(), MyLoginActivity.class));
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showShort(getActivity(),"退出登陆失败:"+s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });


    }

    private void cleandatas() {//清除登陆状态
        ZPreferenceUtils.setPrefBoolean(com.nxt.ott.Constant.IS_LOGIN,false);
        ZPreferenceUtils.setPrefString(Constant.USERNAME,"");
        ZPreferenceUtils.setPrefString(Constant.NICKNAME,"");
        ZPreferenceUtils.setPrefString(Constant.AVATOR,"");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            if ("".equals(ZPreferenceUtils.getPrefString(Constant.NICKNAME,""))){
                mTvUserName.setText(ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
            }else {
                mTvUserName.setText(ZPreferenceUtils.getPrefString(Constant.NICKNAME,""));
            }
            Glide.with(getActivity()).load(Constant.BASE_URL_EXPERTER+ZPreferenceUtils.getPrefString(Constant.AVATOR,"")).crossFade().placeholder(R.mipmap.mine_photo).into(iv_user_photo);
        }

        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        if ("".equals(ZPreferenceUtils.getPrefString(Constant.NICKNAME,""))){
            mTvUserName.setText(ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
        }else {
            mTvUserName.setText(ZPreferenceUtils.getPrefString(Constant.NICKNAME,""));
        }
        Glide.with(getActivity()).load(Constant.BASE_URL_EXPERTER+ZPreferenceUtils.getPrefString(Constant.AVATOR,"")).crossFade().placeholder(R.mipmap.mine_photo).into(iv_user_photo);
        super.onResume();
    }
}
