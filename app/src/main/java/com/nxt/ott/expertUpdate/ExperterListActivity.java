package com.nxt.ott.expertUpdate;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.fragment.ExperterListFragment;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class ExperterListActivity extends BaseTitleActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.search)
    SearchView searchView;
    Bundle bundle;
    public static final int PERMISSION_CODE=102;
    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this, "专家列表");
        bundle = getIntent().getExtras();

        searchView.setQueryHint("请输入专家姓名搜索");
        getSupportFragmentManager().beginTransaction().add(R.id.container,ExperterListFragment.newInstance(bundle)).commit();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ExperterListFragment fragment = (ExperterListFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                fragment.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        requirePermission();
    }

    private void requirePermission() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this,perms)){
        }else {
            EasyPermissions.requestPermissions(this,"需要拨打电话的权限",PERMISSION_CODE,perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_experter_list;
    }
    /**
     * 初始化Topbar
     *
     * @param activity {@link android.app.Activity} 使用Topbar的Activity
     */
    @Override
    protected void initTopbar(Activity activity, String titlename) {
        iv_experter_back = (RelativeLayout) activity.findViewById(R.id.layout_left);
        iv_experter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

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
