package com.nxt.ott.expertUpdate;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.activity.PictureScan;
import com.nxt.ott.adapter.CommentPictureAdapter;
import com.nxt.ott.adapter.CommentPictureAdapter2;
import com.nxt.ott.adapter.MyAdapter;
import com.nxt.ott.base.BaseTitleActivity;
import com.nxt.ott.domain.IssueAnswer;
import com.nxt.ott.domain.Recoder;
import com.nxt.ott.util.JsonUtils;
import com.nxt.ott.util.MediaManager;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.view.AudioRecorderButton;
import com.nxt.zyl.util.ZPreferenceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;
import okhttp3.Response;


public class CommentActivity extends BaseTitleActivity {
    @BindView(R.id.list_data)
    ExpandableListView mlistData;
    @BindView(R.id.lyt_comment)
    LinearLayout lyt_comment;
    @BindView(R.id.lyt_comment1)
    ImageView lyt_comment1;
    @BindView(R.id.edit_vg_lyt)
    LinearLayout edit_vg_lyt;
    @BindView(R.id.edit_comment)
    EditText edit_comment;
    @BindView(R.id.but_comment_send)
    Button but_comment_send;
    @BindView(R.id.btn_pic)
    Button btn_pic;
    @BindView(R.id.btn_voice)
    AudioRecorderButton btn_voice;
    @BindView(R.id.lv_pic)
    RecyclerView lv_pic;
    @BindView(R.id.id_recoder_anim)
    View id_recoder_anim;
    @BindView(R.id.id_recoder_time)
    TextView tv_seconds;
    @BindView(R.id.ll_pic)
    LinearLayout ll_pic;
    @BindView(R.id.question_title)
    TextView question_title;
    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.ry_pic)
    RecyclerView ry_pic;
    @BindView(R.id.ll_voice)
    LinearLayout ll_voice;
    @BindView(R.id.tv_sec)
    TextView tv_sec;
    @BindView(R.id.anim)
    View anim;
    private List<String> SelectPhotos = new ArrayList<>();
    private List<Recoder> mDatas = new ArrayList<>();//保存储存的录音
    private String mCurrentUser = ZPreferenceUtils.getPrefString(Constant.USERNAME,"");
    private String mContent;
    private boolean isReply = false;
    private boolean isZhuiJia = false;
    private boolean isContentClick =false;
    private String mIssueUser;
    private CommentPictureAdapter pictureAdapter;
    private CommentPictureAdapter2 pictureAdapter2;
    private float voiceSeconds;
    private String voicePath;
    private String id,title,text,img,voice,parentId;   //从上个页面传过来的详细情况
    private List<String> imgs = new ArrayList<>();//从上个页面传过来的图片集合
    private MyAdapter adapter;
    private int CurrentGroupPosition = 0;
    private int CurrentChildPosition = 0;
    private Map<String,String> params = new HashMap<>();
    private List<IssueAnswer.ListBean> issueAnswers = new ArrayList<>();
    private PostRequest request;
    RefreshReceiver receiver;
    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this,"评论列表");
        initDatas();
        registerReceivet();
        btn_pic.setOnClickListener(this);
        btn_voice.setOnClickListener(this);
        lyt_comment.setOnClickListener(this);
        lyt_comment1.setOnClickListener(this);
        but_comment_send.setOnClickListener(this);
        id_recoder_anim.setOnClickListener(this);
        anim.setOnClickListener(this);
        initRecyclerView();
        btn_voice.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                voiceSeconds = seconds;
                voicePath = filePath;
                mDatas.clear();
                Recoder recorder = new Recoder(seconds, filePath);
                mDatas.add(recorder);
                tv_seconds.setVisibility(View.VISIBLE);
                id_recoder_anim.setVisibility(View.VISIBLE);
                tv_seconds.setText(Math.round(seconds) + "\"");
            }
        });
        //给Edittext设置表情锅过滤器
        InputFilter emojiFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    Toast.makeText(CommentActivity.this,"不支持输入表情", Toast.LENGTH_SHORT).show();
                    return "";
                }
                return null;
            }
        };
        edit_comment.setFilters(new InputFilter[]{emojiFilter});

    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lv_pic.setLayoutManager(manager);
        lv_pic.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                SelectPhotos.remove(position);
                pictureAdapter.notifyItemRemoved(position);
            }
        });
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        ry_pic.setLayoutManager(manager1);
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        request = OkGo.post(Constant.ADD_ISSUE_ANSWER);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0)+"";
        title = intent.getStringExtra("title");
        text = intent.getStringExtra("text");
        img = intent.getStringExtra("img");
        voice = intent.getStringExtra("voice");
        question_title.setText("标题:"+title);
        tv_text.setText("问题详情:"+text);
        if (!TextUtils.isEmpty(img)){
            if (!img.contains(",")){
                imgs.add(img);
            }else {
                String[] imgStr = img.split(",");
                imgs.addAll(Arrays.asList(imgStr));
            }
        }
        if (imgs!=null){
            pictureAdapter2 = new CommentPictureAdapter2(this,imgs);
            ry_pic.setAdapter(pictureAdapter2);
            pictureAdapter2.setOnItemClickLitener(new CommentPictureAdapter2.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent1 = new Intent(CommentActivity.this, PictureScan.class);
                    intent1.putExtra("imgurl",Constant.BASE_URL_EXPERTER+imgs.get(position));
                    startActivity(intent1);
                }
            });
        }

        if (!TextUtils.isEmpty(voice)){
            ll_voice.setVisibility(View.VISIBLE);
        }else {
            ll_voice.setVisibility(View.GONE);
        }
//        if (!TextUtils.isEmpty(voice)){
//            ll_voice.setVisibility(View.VISIBLE);
//            anim.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // 播放动画（帧动画）
//                    if (anim != null) {
//                        anim.setBackgroundResource(R.mipmap.adj);
//                        anim = null;
//                    }
//                    anim = findViewById(R.id.anim);
//                    anim.setBackgroundResource(R.drawable.play_anim);
//                    AnimationDrawable animation = (AnimationDrawable) anim.getBackground();
//                    animation.start();
//                    // 播放录音
//                    // mediaPlayer = new MediaPlayer();
//                    // 通过Uri解析一个网络地址
//                    Uri uri = Uri
//                            .parse(Constant.BASE_URL_EXPERTER+voice);
//                    try {
//                        mediaPlayer.setDataSource(CommentActivity.this, uri);
//                        // mediaPlayer.prepare();
//                        // 也可以直接通过这种方式装载网络上的音频文件
//                        mediaPlayer = MediaPlayer.create(CommentActivity.this,
//                                uri);
//                        mediaPlayer.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
////                    MediaManager.playSound(Constant.BASE_URL_EXPERTER+voice, new MediaPlayer.OnCompletionListener() {
////
////                        public void onCompletion(MediaPlayer mp) {
////                            anim.setBackgroundResource(R.mipmap.adj);
////                        }
////                    });
//                }
//            });
//        }
        getQuestionList();

    }

    /**
     * 获取评论列表
     */
    private void getQuestionList() {
        OkGo.post(Constant.LOAD_ISSUE_ANSWER)
                .params("issueId",id)
                .params("user",ZPreferenceUtils.getPrefString(Constant.USERNAME,""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        IssueAnswer issueAnswer = new Gson().fromJson(s,IssueAnswer.class);
                        issueAnswers = issueAnswer.getList();
                        adapter = new MyAdapter(CommentActivity.this,issueAnswers);
                        if (issueAnswers!=null){
                            mlistData.setGroupIndicator(null);
                            mlistData.setAdapter(adapter);
                            if (issueAnswers.size()>0){
                                for (int i=0;i<issueAnswers.size();i++){
                                    mlistData.expandGroup(i);
                                }
                            }
                            adapter.setOnReplyClickListener(new MyAdapter.onReplyClickListener() {
                                @Override
                                public void onReplyClick(View v,int groupPosition, String issueUser,String issueNickName) {
                                    edit_vg_lyt.setVisibility(View.VISIBLE);
                                    edit_comment.requestFocus();
                                    isZhuiJia = true;
                                    mIssueUser = issueUser;
                                    edit_comment.setHint(String.format(getResources().getString(R.string.hint_reply),issueNickName));
                                    CurrentGroupPosition = groupPosition;
                                }
                            });
                            adapter.setOnContentClickListener(new MyAdapter.onContentClickListener() {
                                @Override
                                public void onContentClick(View v,int groupPosition,int childPosition, String issueUser,String issueNickName) {
                                    edit_vg_lyt.setVisibility(View.VISIBLE);
                                    edit_comment.requestFocus();
//                                    isReply = true;
                                    isContentClick = true;
                                    mIssueUser = issueUser;
                                    edit_comment.setHint(String.format(getResources().getString(R.string.hint_reply),issueNickName));
                                    CurrentChildPosition = childPosition;
                                    CurrentGroupPosition = groupPosition;
                                }
                            });
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
//        for (int i = 0;i<1;i++){
//            List<String> list = new ArrayList<>();
//            Comment comment = new Comment("胡强"+i,"你好啊"+i,"谢许薪"+i, i % 2 == 0,list,"",0);
//            comments.add(comment);
//        }
//        for (int j=0;j<1;j++){
//            Comment2 comment2 = new Comment2("钟志华"+j,"2016-3-30 15:29:30",25,"内容"+j,imgReply,"",comments);
//            comment2s.add(comment2);
//        }
    }

    /**
     * 刷新数据
     */
    private void refesh(){
        OkGo.post(Constant.LOAD_ISSUE_ANSWER)
                .params("issueId",id)
                .params("user",ZPreferenceUtils.getPrefString(Constant.USERNAME,""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        IssueAnswer issueAnswer = new Gson().fromJson(s, IssueAnswer.class);
                        if (issueAnswers != null) {
                            List<IssueAnswer.ListBean> answerList = issueAnswer.getList();
                            issueAnswers.clear();
                            issueAnswers.addAll(answerList);
                            if (issueAnswers != null) {
                                adapter.refresh();
                                for (int i=0;i<issueAnswers.size();i++){
                                    mlistData.expandGroup(i);
                                }
                                if (issueAnswers.size() == 0) {
                                    mlistData.setEmptyView(LayoutInflater.from(CommentActivity.this).inflate(R.layout.empty_view, (ViewGroup) mlistData.getParent(),false));
                                }
                                if (isReply){
                                    adapter.refresh();
                                    for (int i=0;i<issueAnswers.size();i++){
                                        mlistData.expandGroup(i);
                                    }
                                    isReply = false;
                                }else if (isContentClick){
                                    adapter.refresh(mlistData,CurrentGroupPosition);
                                    isContentClick = false;
                                }else if (isZhuiJia){
                                    adapter.refresh(mlistData,CurrentGroupPosition);
                                    isZhuiJia = false;
                                }

                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_comment;
    }

    /**
     * 隐藏或者显示输入框
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            /**
             *这堆数值是算我的下边输入区域的布局的，
             * 规避点击输入区域也会隐藏输入区域
             */

            v.getLocationInWindow(leftTop);
            int left = leftTop[0] - 100;//50
            int top = leftTop[1] - 100;//50
            int bottom = top + v.getHeight() + 600; //300
            int right = left + v.getWidth() + 240;//240
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
    /**
     * 点击屏幕其他地方收起输入法
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                onFocusChange(false);

            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        edit_comment.getContext().getSystemService(INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    edit_comment.requestFocus();//获取焦点
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);
                    edit_comment.setVisibility(View.VISIBLE);
                    edit_vg_lyt.setVisibility(View.GONE);
                }
            }
        }, 100);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.lyt_comment1:
                edit_vg_lyt.setVisibility(View.VISIBLE);
                isReply = true;
                edit_comment.requestFocus();
                edit_comment.setHint(getResources().getString(R.string.hint_comment));
                break;
            case R.id.but_comment_send:
                if (isReply){
                    //回复
                    params.clear();
                    mContent = edit_comment.getText().toString().trim();
                    if ("".equals(mContent)) {
                        Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<String> list = new ArrayList<>();
                    list.addAll(SelectPhotos);
                    params.put("issueId",id);
                    params.put("answerText",mContent);
                    params.put("answerUser",ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
                    params.put("addAndReply","reply");
                    params.put("type", ZPreferenceUtils.getPrefString(Constant.YWZC,""));
                    params.put("answerNickName",ZPreferenceUtils.getPrefString(Constant.NICKNAME,""));
                    if (list.size()>0){
                        for (int i=0;i<list.size();i++){
                            request.params("answerImg"+i,new File(list.get(i)));
                        }
                    }
                    if (!TextUtils.isEmpty(voicePath)) {
                        request.params("answerVoice", new File(voicePath));
                    }
                    uptoServer();
                    SelectPhotos.clear();
                    voicePath ="";
                    edit_comment.setText("");
                    edit_comment.clearFocus();
                    edit_vg_lyt.setVisibility(View.GONE);
                    ll_pic.setVisibility(View.GONE);
                }else if (isContentClick){
                    mContent = edit_comment.getText().toString().trim();
                    if ("".equals(mContent)) {
                        Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.equals(mCurrentUser,mIssueUser)){
                        ToastUtils.showShort(CommentActivity.this,"不能回复自己哦,亲!");
                        return;
                    }
                    //点击回复内容追问
                    mContent = edit_comment.getText().toString().trim();
                    List<String> list = new ArrayList<>();
                    list.addAll(SelectPhotos);
                    params.clear();
                    params.put("issueId",id);
                    params.put("issueText",mContent);
                    params.put("issueUser",ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
                    params.put("addAndReply","add");
                    params.put("type", ZPreferenceUtils.getPrefString(Constant.YWZC,""));
                    params.put("issueNickName",ZPreferenceUtils.getPrefString(Constant.NICKNAME,""));
                    params.put("parentId",issueAnswers.get(CurrentGroupPosition).getId()+"");
                    params.put("answerUser",issueAnswers.get(CurrentGroupPosition).getListAnswer().get(CurrentChildPosition).getIssueUser());
                    params.put("answerNickName",issueAnswers.get(CurrentGroupPosition).getListAnswer().get(CurrentChildPosition).getIssueNickName());
                    if (list.size()>0){
                        for (int i=0;i<list.size();i++){
                            request.params("issueImg"+i,new File(list.get(i)));
                        }
                    }
                    if (!TextUtils.isEmpty(voicePath)) {
                        request.params("issueVoice", new File(voicePath));
                    }
                    uptoServer();
                    SelectPhotos.clear();
                    voicePath ="";
                    edit_comment.setText("");
                    edit_comment.clearFocus();
                    edit_vg_lyt.setVisibility(View.GONE);
                    ll_pic.setVisibility(View.GONE);
                }else if (isZhuiJia){
                    //追问
                    mContent = edit_comment.getText().toString().trim();
                    if ("".equals(mContent)) {
                        Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.equals(mCurrentUser,mIssueUser)){
                        ToastUtils.showShort(CommentActivity.this,"不能回复自己哦,亲!");
                        return;
                    }
                    mContent = edit_comment.getText().toString().trim();
                    List<String> list = new ArrayList<>();
                    list.addAll(SelectPhotos);
                    params.clear();
                    params.put("issueId",id);
                    params.put("issueText",mContent);
                    params.put("issueUser",ZPreferenceUtils.getPrefString(Constant.USERNAME,""));
                    params.put("addAndReply","add");
                    params.put("type", ZPreferenceUtils.getPrefString(Constant.YWZC,""));
                    params.put("issueNickName",ZPreferenceUtils.getPrefString(Constant.NICKNAME,""));
                    params.put("parentId",issueAnswers.get(CurrentGroupPosition).getId()+"");
                    params.put("answerUser",issueAnswers.get(CurrentGroupPosition).getAnswerUser());
                    params.put("answerNickName",issueAnswers.get(CurrentGroupPosition).getAnswerNickName());
                    if (list.size()>0){
                        for (int i=0;i<list.size();i++){
                            request.params("issueImg"+i,new File(list.get(i)));
                        }
                    }
                    if (!TextUtils.isEmpty(voicePath)) {
                        request.params("issueVoice", new File(voicePath));
                    }
                    uptoServer();
                    SelectPhotos.clear();
                    voicePath ="";
                    edit_comment.setText("");
                    edit_comment.clearFocus();
                    edit_vg_lyt.setVisibility(View.GONE);
                    ll_pic.setVisibility(View.GONE);
                }
            break;
            case R.id.btn_pic:
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setShowCamera(true)
                        .setPreviewEnabled(false)
                        .start(this);
                break;
            case R.id.id_recoder_anim:
                // 播放动画（帧动画）
                if (id_recoder_anim != null) {
                    id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                    id_recoder_anim = null;
                }
                id_recoder_anim = findViewById(R.id.id_recoder_anim);
                id_recoder_anim.setBackgroundResource(R.drawable.play_anim);
                AnimationDrawable animation = (AnimationDrawable) id_recoder_anim.getBackground();
                animation.start();
                // 播放录音
                MediaManager.playSound(mDatas.get(0).getFilePath(),new MediaPlayer.OnCompletionListener() {

                    public void onCompletion(MediaPlayer mp) {
                        id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                    }
                });
                break;
            case R.id.anim:
                //先把语音下载下来在播放
                OkGo.get(Constant.BASE_URL_EXPERTER+voice)
                        .tag(this)
                        .execute(new FileCallback() {
                            @Override
                            public void onSuccess(File file, Call call, Response response) {
                                String path = file.getAbsolutePath();
                                // 播放动画（帧动画）
                                if (anim != null) {
                                    anim.setBackgroundResource(R.mipmap.adj);
                                    anim = null;
                                }
                                anim = findViewById(R.id.anim);
                                anim.setBackgroundResource(R.drawable.play_anim);
                                AnimationDrawable animation1 = (AnimationDrawable) anim.getBackground();
                                animation1.start();
                                // 播放录音
                                MediaManager.playSound(file.getAbsolutePath(),new MediaPlayer.OnCompletionListener() {

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
                break;
                default:
                    break;

        }
    }

    /**
     * 上传到服务器
     */
    private void uptoServer() {
        request.isMultipart(true);
        request.params(params);
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                if ("1".equals(JsonUtils.getServerResult(s))){
                    ToastUtils.showShort(CommentActivity.this, "评论成功");
                    request.removeAllParams();
                    refesh();
                }else {
                    ToastUtils.showShort(CommentActivity.this,"评论失败");
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode ==PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE){
            if (data!=null){
                SelectPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            if (SelectPhotos!=null){
                ll_pic.setVisibility(View.VISIBLE);
                pictureAdapter= new CommentPictureAdapter(R.layout.item_comment_picture,SelectPhotos);
                pictureAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                lv_pic.setAdapter(pictureAdapter);
            }
//            SelectPhotos.clear();
//            if (photos!=null){
//                ll_pic.setVisibility(View.VISIBLE);
//                SelectPhotos.addAll(photos);
//            }
            pictureAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        //判断控件是否显示
        if (edit_vg_lyt.getVisibility()==View.VISIBLE){
            edit_vg_lyt.setVisibility(View.GONE);
            edit_comment.clearFocus();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void initTopbar(Activity activity, String titlename) {
        imgbtnRight = (ImageButton) activity.findViewById(R.id.imgbtn_right);
        tvTopBarText = (TextView) activity.findViewById(R.id.tv_title);
        tvTopBarText.setText(titlename);
        iv_experter_back = (RelativeLayout) activity.findViewById(R.id.layout_left);
        iv_experter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断控件是否显示
                if (edit_vg_lyt.getVisibility()==View.VISIBLE){
                    edit_vg_lyt.setVisibility(View.GONE);
                    edit_comment.clearFocus();
                }else {
                    finish();
                }
            }
        });
    }

    class RefreshReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.broadcast.REFRESH_ACTION".equals(action)){
                refesh();
            }
        }
    }

    private void registerReceivet(){
        receiver = new RefreshReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.broadcast.REFRESH_ACTION");
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
