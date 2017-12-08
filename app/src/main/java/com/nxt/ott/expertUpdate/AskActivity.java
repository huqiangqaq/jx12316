package com.nxt.ott.expertUpdate;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.sweetAlert.SweetAlertDialog;
import com.nxt.ott.util.JsonUtils;
import com.nxt.ott.util.MediaManager;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.view.AudioRecorderButton;
import com.nxt.zyl.util.ZPreferenceUtils;

import net.bither.util.NativeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Call;
import okhttp3.Response;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class AskActivity extends BaseTitleActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.yy)
    TextView yy;
//    @BindView(R.id.pic)
//    TextView pic;
    @BindView(R.id.id_recorder_button)
    AudioRecorderButton mAudioRecorderButton;
    @BindView(R.id.id_recoder_time)
    TextView tv_seconds;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.recycler_view)
    MultiPickResultView resultView;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_text)
    EditText et_text;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.rl_text)
    RelativeLayout rl_text;
    private FrameLayout animView;
    private List<Recorder> mDatas = new ArrayList<>();//保存储存的录音
    private ArrayList<String> imgPath = new ArrayList<>();//图片路径
    private boolean isExperter = false;
    private String tel,type,name;
    public static final int PERMISSION_CODE=101;
    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent!=null){
            isExperter = intent.getBooleanExtra("isExperter",false);
            tel = intent.getStringExtra("uid");
            type = intent.getStringExtra("type");
            name = intent.getStringExtra("name");
            if (name!=null){
                initTopbar(this,"向"+name+"提问");
            }else {
                initTopbar(this,"提问");
            }
        }

        yy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioRecorderButton.setVisibility(View.VISIBLE);
            }
        });
        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                mDatas.clear();
                Recorder recorder = new Recorder(seconds, filePath);
                mDatas.add(recorder);
                rl.setVisibility(View.VISIBLE);
                tv_seconds.setText(Math.round(seconds) + "\"");
                mAudioRecorderButton.setVisibility(View.GONE);
            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 播放动画（帧动画）
                if (animView != null) {
                    animView.setBackgroundResource(R.mipmap.adj);
                    animView = null;
                }
                animView = findViewById(R.id.id_recoder_anim);
                animView.setBackgroundResource(R.drawable.play_anim);
                AnimationDrawable animation = (AnimationDrawable) animView.getBackground();
                animation.start();
                // 播放录音
                MediaManager.playSound(mDatas.get(0).filePath,new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        animView.setBackgroundResource(R.mipmap.adj);
                    }
                });
            }
        });

        et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_submit.setOnClickListener(this );
        rl_text.setOnClickListener(this);
        requirePermission();

    }

    private void requirePermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this,perms)){
            resultView.init(this,MultiPickResultView.ACTION_SELECT,null);
        }else {
            EasyPermissions.requestPermissions(this,"需要照相、录音的权限",PERMISSION_CODE,perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ask;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resultView.onActivityResult(1,resultCode,data);
        switch (requestCode){
            case 1:
                if (data!=null){
                    imgPath = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                }
                break;
                default:
                    break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
//            case tv_kind_experter:
//                    showKindDialog();
//                break;
            case R.id.btn_submit:
                prepareSubmit();
                break;
            case R.id.rl_text:
                et_text.requestFocus();
                toggleInput();
                break;
                default:
                    break;
        }
    }

    /**
     * 提交验证
     */
    private void prepareSubmit() {
        et_text.clearFocus();
        String title = et_title.getText().toString().trim();
        String text  = et_text.getText().toString().trim();
        if (TextUtils.isEmpty(title)){
            ToastUtils.showShort(this,"问题标签不能为空!");
            return;
        }
        if (TextUtils.isEmpty(text)&&mDatas.isEmpty()&&imgPath.isEmpty()){
            ToastUtils.showShort(this,"您至少需要提供问题详情,问题语音或问题图片其中一种");
            return;
        }
        if (title.length()<6){
            ToastUtils.showShort(this,"标题不能少于六个字,请重新输入!");
            return;
        }
       if (imgPath.size()>0){
            showLoadingDialog(R.string.handle_pic);
            for (int i=0;i<imgPath.size();i++){
                NativeUtil.compressBitmap(imgPath.get(i),imgPath.get(i));
            }

        }
        //点击提交传递数据给下一个页面
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
//        bundle.putString("point","全部");
//        bundle.putString("pointNickName","全部");
        bundle.putString("text",text);
        bundle.putStringArrayList("img",imgPath);
        bundle.putString("voice",mDatas.isEmpty()?"":mDatas.get(0).getFilePath());
        bundle.putString("userName", ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
        bundle.putString("userNickName",ZPreferenceUtils.getPrefString(Constant.NICKNAME,""));
//        bundle.putString("userName", EMClient.getInstance().getCurrentUser());
//        bundle.putString("userNickName",DemoHelper.getInstance().getCurrentUserNick());
        dismissloading();
        if (isExperter){
            Map<String,String> map = new HashMap<>();
            map.put("title", title);
            map.put("text", text);
            map.put("userName",ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
            map.put("userNickName",ZPreferenceUtils.getPrefString(Constant.NICKNAME,""));
            map.put("point",tel);
            map.put("type", type);
            map.put("pointNickName", name);
            upToServer(map,mDatas.isEmpty()?"":mDatas.get(0).getFilePath());
        }else {
            Intent intent = new Intent(this,ExpertRecommedActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        resultView.init(this,MultiPickResultView.ACTION_SELECT,null);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //这里需要重新设置Rationale和title，否则默认是英文格式
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                    .setTitle("必需权限")
                    .build()
                    .show();
        }
    }


    class Recorder {
        float time;
        String filePath;

        public Recorder(float time, String filePath) {
            super();
            this.time = time;
            this.filePath = filePath;
        }

        public float getTime() {
            return time;
        }

        public void setTime(float time) {
            this.time = time;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

    }

    /**
     * 提交给服务器
     * @param map
     * @param voicePath
     */
    private void upToServer(Map<String,String> map,String voicePath) {
        showLoadingDialog(R.string.hint_message);
        PostRequest request = OkGo.post(Constant.ADD_ISSUE);
        request.isMultipart(true);
        request.params(map);
        if (imgPath.size() > 0) {
            for (int i = 0; i < imgPath.size(); i++) {
                request.params("img" + i, new File(imgPath.get(i)));
            }
        }
        if (!TextUtils.isEmpty(voicePath)) {
            request.params("voiceFile", new File(voicePath));
        }
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                if ("1".equals(JsonUtils.getServerResult(s))){
                    dismissLoadingDialog();
                    new SweetAlertDialog(AskActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText("您的问题已成功提交给"+name+",请耐心等候回答!")
                            .setConfirmText("好的!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    //回到首页
                                    startActivity(new Intent(AskActivity.this, AnswerList_Activity.class));
                                    finish();
                                }
                            })
                            .show();
                }else {
                    new SweetAlertDialog(AskActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("提交失败:"+ JsonUtils.getServerMsg(s))
                            .setConfirmText("好的!")
                            .show();
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                dismissloading();
                ToastUtils.showShort(AskActivity.this,e.toString());
            }
        });
    }

}