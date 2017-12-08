package com.nxt.ott.activity.pest;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;

/**
 * Created by xpeng on 2016/10/17.
 */

public class PestDetailActivity2 extends BaseTitleActivity {
    private ImageView mImageView;
    private WebView mWebView;
    private TextView mTextView;

    @Override
    protected void initView() {
        initTopbar(this,getIntent().getStringExtra(Constant.BCH_PEST_DETAIL));

        mImageView=(ImageView)findViewById(R.id.pest_iv_detail);
        mTextView=(TextView)findViewById(R.id.pest_tv_name);
        mWebView=(WebView)findViewById(R.id.pest_wv_detail);

        String detailUrl=String.format(Constant.BCH_CONTENT_URL,getIntent().getStringExtra(Constant.BCH_PEST_ID));
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);

        mTextView.setText(getIntent().getStringExtra(Constant.BCH_PEST_DETAIL));
        ImageLoader.getInstance().displayImage(getIntent().getStringExtra(Constant.BCH_PEST_IMG),new ImageViewAware(mImageView, false));
        mWebView.loadUrl(detailUrl);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_pest_detail2;
    }

}
