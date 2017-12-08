package com.nxt.ott.activity.doctor;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.nxt.ott.R;
import com.nxt.ott.base.BaseDiseaseActivity;

import org.hybridsquad.android.library.BitmapUtil;
import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

/**
 * Created by zhangyonglu on 2016/2/1 0001.
 */
public class DiseasePictureActivity extends BaseDiseaseActivity implements CropHandler {
    CropParams mCropParams;
    private ImageView photoview;
    @Override
    protected void initView() {
        initTopbar(this,getString(R.string.picture_choose));
        photoview= (ImageView) findViewById(R.id.img_photo);
        mCropParams = new CropParams(this);

        rbtstepone.setChecked(true);
        rbtsteptextone.setChecked(true);

        rbtsteptwo.setChecked(true);
        rbtsteptexttwo.setChecked(true);

        rbtstepthree.setChecked(true);
        rbtsteptextthree.setChecked(true);
        findViewById(R.id.btn_take_carmera).setOnClickListener(this);
        findViewById(R.id.btn_photo_album).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
        if (requestCode == 1) {
            //Log.e(TAG, "");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_disease_picture ;
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        photoview.setImageBitmap(BitmapUtil.decodeUriAsBitmap(this, uri));

    }

    @Override
    public void onCompressed(Uri uri) {
        photoview.setImageBitmap(BitmapUtil.decodeUriAsBitmap(this, uri));
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }

    @Override
    public void onClick(View v) {
        mCropParams.refreshUri();
        Intent intent;
        switch (v.getId()){
            case R.id.btn_photo_album:
                mCropParams.enable = true;
                mCropParams.compress = false;
                intent = CropHelper.buildGalleryIntent(mCropParams);
                startActivityForResult(intent, CropHelper.REQUEST_CROP);

                break;
            case R.id.btn_take_carmera:

                mCropParams.enable = true;
                mCropParams.compress = false;
                Intent intent1 = CropHelper.buildCameraIntent(mCropParams);
                startActivityForResult(intent1, CropHelper.REQUEST_CAMERA);

                break;
            case R.id.btn_next:
               startActivity(new Intent(this,DiseaseResultActivity.class).putExtra("data",
                       getIntent().getSerializableExtra("data")));
                break;
        }
        super.onClick(v);
    }
    @Override
    protected void onDestroy() {
        CropHelper.clearCacheDir();
        super.onDestroy();
    }
}
