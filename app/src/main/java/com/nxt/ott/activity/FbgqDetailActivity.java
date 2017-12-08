package com.nxt.ott.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.util.SerializableObject;
import com.nxt.zyl.util.HttpUtils;

import java.util.HashMap;

public class FbgqDetailActivity extends Activity {
    private TextView titletext, datetext, contenttext;
    private ImageView imageview;
    private HashMap<String, Object> map;
    private String imgurl;
    private LinearLayout llBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fbgqdetail);
        initview();
        initdata();
        super.onCreate(savedInstanceState);
    }

    /*map.put("Date", jb.getString("Date"));
    map.put("ProductName", jb.getString("ProductName"));
    map.put("Describe", jb.getString("Describe"));
    map.put("imghttpurl", jb.getString("imghttpurl"));
    map.put("TypeId", jb.getInt("TypeId"));*/
    private void initdata() {
        map = ((SerializableObject) getIntent().getSerializableExtra("news")).getHmap();
        titletext.setText(map.get("ProductName").toString());
        datetext.setText(map.get("Date").toString());
        contenttext.setText(map.get("Describe").toString());
        imgurl = map.get("imghttpurl").toString();
        new Thread(new Runnable() {

            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage(10, HttpUtils.getPic(imgurl)));

            }
        }).start();

    }

    private void initview() {

        llBack = (LinearLayout) findViewById(R.id.back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titletext = (TextView) findViewById(R.id.tv_title);
        datetext = (TextView) findViewById(R.id.tv_date);
        contenttext = (TextView) findViewById(R.id.tv_content);
        imageview = (ImageView) findViewById(R.id.img_fbqgimg);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FbgqDetailActivity.this,PictureScan.class).putExtra("imgurl",imgurl));
            }
        });
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            imageview.setImageBitmap((Bitmap) (msg.obj));
            super.handleMessage(msg);
        }


    };
}
