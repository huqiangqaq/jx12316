package com.nxt.ott.activity.account;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.util.JsonUtils;
import com.nxt.ott.util.ToastUtils;
import com.nxt.zyl.util.ZPreferenceUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class UserProfileActivity extends BaseTitleActivity {
    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private ImageView headAvatar;
    private ImageView headPhotoUpdate;
    private ImageView iconRightArrow;
    private TextView tvNickName;
    private TextView tvUsername;
    private ProgressDialog dialog;
    private RelativeLayout rlNickName;

    public Bitmap photo;
    public String nickString;
    private ImageView ivBack;
    private String userName,nickName,avator;
    private File currentImageFile = null;

    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this, "个人资料");
//        ivBack = (ImageView) findViewById(R.id.iv_back);
//        ivBack.setOnClickListener(this);
        headAvatar = (ImageView) findViewById(R.id.user_head_avatar);
        headPhotoUpdate = (ImageView) findViewById(R.id.user_head_headphoto_update);
        tvUsername = (TextView) findViewById(R.id.user_username);
        tvNickName = (TextView) findViewById(R.id.user_nickname);
        rlNickName = (RelativeLayout) findViewById(R.id.rl_nickname);
        iconRightArrow = (ImageView) findViewById(R.id.ic_right_arrow);
        initListener();
    }

    private void initListener() {
        headPhotoUpdate.setVisibility(View.VISIBLE);
        iconRightArrow.setVisibility(View.VISIBLE);
        rlNickName.setOnClickListener(this);
        headAvatar.setOnClickListener(this);
        userName = ZPreferenceUtils.getPrefString(Constant.USERNAME,"");
        nickName = ZPreferenceUtils.getPrefString(Constant.NICKNAME,"");
        if (!"".equals(avator)){
            Glide.with(this).load(Constant.BASE_URL_EXPERTER+ZPreferenceUtils.getPrefString(Constant.AVATOR,"")).crossFade().placeholder(R.mipmap.em_default_avatar).into(headAvatar);
        }
        if (!"".equals(nickName)){
            tvNickName.setText(nickName);
        }else {
            tvNickName.setText(userName);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_user_profile;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rl_nickname:
                final EditText editText = new EditText(this);
                new AlertDialog.Builder(this).setTitle(R.string.setting_nickname).setIcon(android.R.drawable.ic_dialog_info).setView(editText)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nickString = editText.getText().toString();
                                if (TextUtils.isEmpty(nickString)) {
                                    Toast.makeText(UserProfileActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                updateRemoteNick(nickString);
                            }
                        }).setNegativeButton(R.string.cancel, null).show();
                break;
            case R.id.user_head_avatar:
                uploadHeadPhoto();
                break;
        }
    }

    private void uploadHeadPhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dl_title_upload_photo);
        builder.setItems(new String[]{getString(R.string.dl_msg_take_photo), getString(R.string.dl_msg_local_upload)},
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                Toast.makeText(UserProfileActivity.this, getString(R.string.toast_no_support),
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(pickIntent, REQUESTCODE_PICK);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void updateRemoteNick(final String nickString) {
        OkGo.post(Constant.ALERTINFO)
                .params("account",userName)
                .params("nickname",nickString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if ("1".equals(JsonUtils.getServerResult(s))){
                            ToastUtils.showShort(UserProfileActivity.this,"修改昵称成功!");
                            tvNickName.setText(nickString);
                            ZPreferenceUtils.setPrefString(Constant.NICKNAME,nickString);
                        }else {
                            ToastUtils.showShort(UserProfileActivity.this,"修改昵称失败!"+JsonUtils.getServerMsg(s));
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }
    /**
     * save the picture data
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
            Log.e("profile-262", "photo------------->" + photo);
            Drawable drawable = new BitmapDrawable(getResources(), photo);
            headAvatar.setImageDrawable(drawable);
            uploadUserAvatar(Bitmap2Bytes(photo));
        }

    }

    private void uploadUserAvatar(final byte[] data) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_photo), getString(R.string.dl_waiting));
        File dir = new File(Environment.getExternalStorageDirectory(),"jx12316_avator");
        if (!dir.exists()){
            dir.mkdirs();
        }
        final String imgName = System.currentTimeMillis()+".jpg";
        currentImageFile = new File(dir,imgName);
        if (!currentImageFile.exists()){
            try {
                currentImageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        currentImageFile = BetyToFile(currentImageFile.getAbsolutePath(),data);
        OkGo.post(Constant.ALERTINFO)
                .params("account",userName)
                .params("img",currentImageFile)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dialog.dismiss();
                        if ("1".equals(JsonUtils.getServerResult(s))){
                            ToastUtils.showShort(UserProfileActivity.this,"上传头像成功!");
                            ZPreferenceUtils.setPrefString(Constant.AVATOR,"/upload/"+imgName);
                            Glide.with(UserProfileActivity.this).load(currentImageFile).placeholder(R.mipmap.em_default_avatar).crossFade().into(headAvatar);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                final String avatarUrl = DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(data);
//                Log.e("profile-277", "avatarUrl---------->" + avatarUrl);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//                        if (avatarUrl != null) {
//                            Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_success),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_fail),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//
//            }
//        }).start();

        dialog.show();
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    public static File BetyToFile( String filePath,byte[] data)
    {
        File file = new File(filePath);
        BufferedOutputStream stream = null;
        FileOutputStream fstream = null;
        try {
            fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (null != fstream) {
                    fstream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return file;
    }
}
