package com.nxt.ott.activity.scan;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseZoomTitleActivity;
import com.nxt.ott.zxing.CaptureActivity;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Updated by iwon on 2016/6/22 11:25.
 */
public class ProductScanActivity extends BaseZoomTitleActivity implements EasyPermissions.PermissionCallbacks {
    private View contentview;
    public static final int PERMISSION_CODE=101;
    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this,getString(R.string.production_scan));
        int headerheight= BitmapFactory.decodeResource(getResources(), R.mipmap.product_scan_top_bg)
                .getHeight();
        headerimg.setImageResource(R.mipmap.product_scan_top_bg);
        scrollView.setHeaderViewSize(screenwidth,headerheight);
        contentview= LayoutInflater.from(this).inflate(R.layout.activity_product_scan, null);
        parentlayout.addView(contentview);
        contentview.findViewById(R.id.layout_scan_ewm).setOnClickListener(this);
        contentview.findViewById(R.id.layout_write_ewm).setOnClickListener(this);
        contentview.findViewById(R.id.layout_pesticide_search).setOnClickListener(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.common_scrollview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_scan_ewm:
                requirePermission();
                break;
            case R.id.layout_write_ewm:
                startActivity(new Intent(this, WriteEwmActivity.class));
                break;
            case R.id.layout_pesticide_search://农药查询
                startActivity(new Intent(this, AgriculturalCapitalActivity.class));
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    private void requirePermission() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this,perms)){
            startActivity(new Intent(this, CaptureActivity.class));
        }else {
            EasyPermissions.requestPermissions(this,"需要使用摄像头的权限",PERMISSION_CODE,perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        startActivity(new Intent(this, CaptureActivity.class));
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
}
