package com.nxt.ott.expertUpdate;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.Detail;
import com.nxt.ott.util.MediaManager;
import com.nxt.ott.util.ToastUtils;
import com.nxt.zyl.util.ZPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class DetailActivity extends BaseTitleActivity {
    private String id;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.llcontent)
    LinearLayout llcontent;
    @BindView(R.id.llcontent_zj)
    LinearLayout llcontent_zj;
    @BindView(R.id.avator)
    ImageView avator;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.hinfo)
    TextView hinfo;
    @BindView(R.id.cart4)
    CardView cart4;
    @BindView(R.id.cart3)
    CardView cart3;
    @BindView(R.id.tag2)
    TextView tag2;
    @BindView(R.id.btn_reply)
    Button btnReply;
    @BindView(R.id.btn_refuse)
    Button btnRefuse;
    @BindView(R.id.ll_ask)
    LinearLayout ll_ask;
    @BindView(R.id.ll_answer)
    LinearLayout ll_answer;
    @BindView(R.id.id_recoder_anim)
    FrameLayout animView;
    @BindView(R.id.id_recoder_anim2)
    FrameLayout animView2;
    private Detail detail;

    @Override
    protected void initView() {
        initTopbar(this, "问题详情");
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            Log.i("huqiang", "id=" + id);
        }
        getData();


    }

    private void getData() {
        OkGo.get(Constant.GETDETAILBYID)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        detail = new Gson().fromJson(s, Detail.class);
                        if (detail != null && "ok".equals(detail.getResult())) {
                            if (!"no".equals(detail.getTurl())) {
                                ll_ask.setVisibility(View.VISIBLE);
                                ll_ask.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        playVoice(detail.getTurl(), animView);
                                    }
                                });
                            }
                            if (!"no".equals(detail.getZurl())) {
                                ll_answer.setVisibility(View.VISIBLE);
                                ll_answer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        playVoice(detail.getZurl(), animView2);
                                    }
                                });
                            }
                            title.setText(detail.getTitle());
                            info.setText(detail.getInfo());
                            if (TextUtils.equals("ok", detail.getZhresult())) {
                                cart3.setVisibility(View.VISIBLE);
                                cart4.setVisibility(View.VISIBLE);
                                tag2.setVisibility(View.VISIBLE);
                                Glide.with(DetailActivity.this).load(detail.getZinfo().getImg()).placeholder(R.mipmap.ic_launcher).crossFade().into(avator);
                                tv1.setText("专家姓名: " + detail.getZinfo().getName());
                                tv2.setText("专家类型: " + detail.getZinfo().getType());
                                tv3.setText("简介: " + detail.getZinfo().getInfo());
                                hinfo.setText(detail.getHinfo());
                                /**
                                 * 根据是否在微信注册来判断是否加载回答和驳回按钮
                                 */
                                if (ZPreferenceUtils.getPrefBoolean(Constant.WXREGISTER, false)) {
                                    btnRefuse.setVisibility(View.VISIBLE);
                                    btnReply.setVisibility(View.VISIBLE);
                                    btnReply.setOnClickListener(DetailActivity.this);
                                    btnRefuse.setOnClickListener(DetailActivity.this);
                                }
                            }
//                            String img = detail.getImg();
//                            if (!TextUtils.isEmpty(img)) {
//                                if (!img.contains(",")) {
//                                    imgs.add(img);
//                                } else {
//                                    String[] imgStr = img.split(",");
//                                    imgs.addAll(Arrays.asList(imgStr));
//                                }
//                            }
                            //提问图片
                            for (String path : getImgList(detail.getImg())) {
                                ImageView imageView = new ImageView(DetailActivity.this);
                                Glide.with(DetailActivity.this).load(path).crossFade().into(imageView);
                                llcontent.addView(imageView);
                            }

                            //专家回答图片
                            for (String path : getImgList(detail.getZimg())) {
                                ImageView imageView = new ImageView(DetailActivity.this);
                                Glide.with(DetailActivity.this).load(path).crossFade().into(imageView);
                                llcontent_zj.addView(imageView);
                            }
                        }
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            //驳回问题
            case R.id.btn_refuse:
                OkGo.post(Constant.ANSWER)
                        .params("id", id)
                        .params("pid", ZPreferenceUtils.getPrefString(Constant.PID, ""))
                        .params("htype", "3")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    if (jsonObject.getString("result").equals("ok")) {
                                        ToastUtils.showShort(DetailActivity.this, "问题成功驳回!");
                                        finish();
                                    } else {
                                        ToastUtils.showShort(DetailActivity.this, "问题驳回失败," + jsonObject.getString("msg"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                break;
            case R.id.btn_reply:
                startActivity(new Intent(DetailActivity.this, AskActivity.class).putExtra("isExperter", true).putExtra("id", id)
                        .putExtra("stype", detail.getStype()).putExtra("questionId", detail.getQuestionId())
                        .putExtra("asktype", detail.getAsktype()).putExtra("askId", detail.getAskId()));
                break;
            default:
                break;
        }
    }

    private ArrayList<String> getImgList(String img) {
        ArrayList<String> imgs = new ArrayList();
        if (!TextUtils.isEmpty(img)) {
            if (!img.contains(",")) {
                imgs.add(img);
            } else {
                String[] imgStr = img.split(",");
                imgs.addAll(Arrays.asList(imgStr));
            }
        }
        return imgs;
    }


    private void playVoice(String voicePath, final FrameLayout anim) {
        //先把语音下载下来在播放
        OkGo.get(voicePath)
                .tag(this)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        String path = file.getAbsolutePath();
                        // 播放动画（帧动画）
//                        if (anim != null) {
//                            anim.setBackgroundResource(R.mipmap.adj);
//                            anim = null;
//                        }
//                        anim = findViewById(R.id.anim);
                        anim.setBackgroundResource(R.drawable.play_anim);
                        AnimationDrawable animation1 = (AnimationDrawable) anim.getBackground();
                        animation1.start();
                        // 播放录音
                        MediaManager.playSound(file.getAbsolutePath(), new MediaPlayer.OnCompletionListener() {

                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                anim.setBackgroundResource(R.mipmap.adj);
                            }
                        });
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}
