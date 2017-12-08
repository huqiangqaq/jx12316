package com.nxt.ott.expertUpdate;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.Detail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<String> imgs = new ArrayList<>();


    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this,"问题详情");
        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
        }
        getData();
    }

    private void getData() {
        OkGo.get(Constant.GETDETAILBYID)
                .params("id",id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Detail detail = new Gson().fromJson(s,Detail.class);
                        if (detail!=null){
                            title.setText(detail.getTitle());
                            info.setText(detail.getInfo());
                            if (TextUtils.equals("ok",detail.getZhresult())){
                                cart3.setVisibility(View.VISIBLE);
                                cart4.setVisibility(View.VISIBLE);
                                tag2.setVisibility(View.VISIBLE);
                                Glide.with(DetailActivity.this).load(detail.getZinfo().getImg()).placeholder(R.mipmap.ic_launcher).crossFade().into(avator);
                                tv1.setText("专家姓名: "+detail.getZinfo().getName());
                                tv2.setText("专家类型: "+detail.getZinfo().getType());
                                tv3.setText("简介: " +detail.getZinfo().getInfo());
                                hinfo.setText(detail.getHinfo());
                            }
                            String img = detail.getImg();
                            if (!TextUtils.isEmpty(img)){
                                if (!img.contains(",")){
                                    imgs.add(img);
                                }else {
                                    String[] imgStr = img.split(",");
                                    imgs.addAll(Arrays.asList(imgStr));
                                }
                            }
                            for (String path:imgs){
                                ImageView imageView = new ImageView(DetailActivity.this);
                                Glide.with(DetailActivity.this).load(path).crossFade().into(imageView);
                                llcontent.addView(imageView);
                            }

                        }
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }


}
