package com.nxt.ott.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.nxt.ott.R;
import com.nxt.ott.adapter.MyArrayAdapter;
import com.nxt.ott.util.Port;
import com.nxt.zyl.util.HttpUtils;
import com.nxt.zyl.util.TimeUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/7 0007.
 */
public class WriteSupplyFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    private View view;
    private ArrayList<String> typelist=new ArrayList<String>();
    private EditText titleedit,contentedit;
    private Spinner typespinner;
    private ImageView slimage;
    private LinearLayout uploadlayout;
    private String type;
    private String picPath;
    private String title;
    private String content;
    private Map<String,File> param=new HashMap<String, File>();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view=inflater.inflate(R.layout.fbgq,null);
        initviews();
        return view;
    }
    private void initviews() {
        titleedit = (EditText)view.findViewById(R.id.fbgq_title_edit);
        contentedit = (EditText) view.findViewById(R.id.fbgq_content_edit);
        typespinner = (Spinner) view.findViewById(R.id.fbgq_type_spinner);
        slimage = (ImageView) view.findViewById(R.id.fbgq_sl_image);
        uploadlayout = (LinearLayout) view.findViewById(R.id.upploadphoto);

        typelist.add("供应信息");
        typelist.add("求购信息");
        MyArrayAdapter adapter = new MyArrayAdapter(getActivity(),
                R.layout.spinner_checked_item, typelist);
        typespinner.setAdapter(adapter);
        typespinner.setOnItemSelectedListener(this);
        uploadlayout.setOnClickListener(this);
        view.findViewById(R.id.btn_fbgq_commit).setOnClickListener(this);
        view.findViewById(R.id.btn_reset).setOnClickListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        ((TextView) view).setTextColor(this.getResources()
                .getColor(R.color.redbg));
        if("供应信息".equals(typelist.get(position))){
            type="1";
        }else{
            type="2";
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (2 == requestCode && data != null) {

            Uri uri = data.getData();
            try {
                String[] pojo = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().managedQuery(uri, pojo, null, null, null);
                if (cursor != null) {
                    ContentResolver cr = getActivity().getContentResolver();
                    int colunm_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(colunm_index);
                    if (path.endsWith("jpg") || path.endsWith("png")) {
                        picPath = path;
                        Bitmap bitmap = BitmapFactory.decodeStream(cr
                                .openInputStream(uri));
                        slimage.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(getActivity(), "图片不存在", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_LONG).show();

                }

            } catch (Exception e) {

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void upload() {
        title = titleedit.getText().toString();
        content = contentedit.getText().toString();
        File imageFile;
        final Map<String, String> params = new HashMap<String, String>();

        try {
            if (picPath == null || picPath == "") {
                param.put("Imgurl", null);

            } else {
                imageFile = new File(picPath);
                param.put("Imgurl", imageFile);
            }
            if (title == null || "".equals(title)) {
                Toast.makeText(getActivity(), "标题不能为空",Toast.LENGTH_SHORT).show();
                return;
            } else if (content == null || "".equals(content)) {
                Toast.makeText(getActivity(), "内容不能为空", Toast.LENGTH_SHORT).show();

                return;
            }
           /* Action=save
            		&BunssyType_val   // 卖是1  买是2 默认是1
            		&ProName      产品名称
            		&Price       价格 可有可无
            		&Person      联系人
            		&tel         联系方式
            		&describe    描述
            		&Imgurl      图片地址 可以不传 （postfile）
            		&addDate     添加时间 默认服务器时间*/
            params.put("Action", "save");
            params.put("BunssyType_val",type);
            params.put("ProName", title);
            params.put("describe", content);
            params.put("Person","admin");
            params.put("Price","24");
            params.put("addDate", TimeUtil.getdate2());

            params.put("tel", "155555555");
            params.put("describe", content);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String str = HttpUtils.post2(Port.FBGQUPLOAD, params, param);
                    Log.e("write-196","result--------------->" + str);
                    if("true".equals(str)){
                        mHandler.sendEmptyMessage(0);
                    }else{
                        mHandler.sendEmptyMessage(1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {// 姝ゆ柟娉曞湪ui绾跨▼杩愯
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                    titleedit.setText("");
                    contentedit.setText("");
                    slimage.setImageResource(R.mipmap.thumbnail);

                    break;
                case 1:

                    Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();

                    break;
                case 3:

                    // Toast.makeText(getActivity(), R.string.serverisontreply, 0)
                    //  .show();

                    break;

                default:

                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upploadphoto:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 璋冪敤android鐨勫浘搴�
                startActivityForResult(i, 2);
                break;
            case R.id.btn_fbgq_commit:
                upload();
                break;
            case R.id.btn_reset:
                titleedit.setText("");
                contentedit.setText("");
                slimage.setImageResource(R.mipmap.thumbnail);
                break;
            default:
                break;
        }

    }
}