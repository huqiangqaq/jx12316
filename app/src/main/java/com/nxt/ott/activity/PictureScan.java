package com.nxt.ott.activity;

import com.bumptech.glide.Glide;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.zyl.ui.widget.photoview.PhotoView;

public class PictureScan extends BaseTitleActivity {
    PhotoView photoView;
    String imgurl;
    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this,getString(R.string.picture_scan));
        photoView = (PhotoView) findViewById(R.id.photo);
        imgurl = getIntent().getStringExtra("imgurl");
        Glide.with(this).load(imgurl).crossFade().placeholder(R.mipmap.empty_photo).into(photoView);
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                handler.sendMessage(handler.obtainMessage(10, HttpUtils.getPic(imgurl)));
//
//            }
//        }).start();

    }
    @Override
    protected int getLayout() {
        return R.layout.activity_picture_scan;
    }

//    private Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            photoView.setImageBitmap((Bitmap) (msg.obj));
//            super.handleMessage(msg);
//        }
//
//
//    };
}
