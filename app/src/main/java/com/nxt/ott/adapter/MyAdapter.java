package com.nxt.ott.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.ott.Constant;
import com.nxt.ott.MyApplication;
import com.nxt.ott.R;
import com.nxt.ott.activity.PictureScan;
import com.nxt.ott.domain.ExperterInfo;
import com.nxt.ott.domain.IssueAnswer;
import com.nxt.ott.util.FileUtils;
import com.nxt.ott.util.JsonUtils;
import com.nxt.ott.util.MediaManager;
import com.nxt.ott.util.ToastUtils;
import com.nxt.ott.view.EditTextDialog;
import com.nxt.zyl.ui.widget.roundedimageview.CustomImageView;
import com.nxt.zyl.util.ZPreferenceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by huqiang on 2017/4/7 9:08.
 */

public class MyAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<IssueAnswer.ListBean> comments = new ArrayList<>();
    private SparseArray<Boolean> map = new SparseArray<>();
    private CommentPictureAdapter2 pictureAdapter;
    private onContentClickListener onContentClickListener;
    private onReplyClickListener onReplyClickListener;
    protected ProgressDialog loadingDialog;
    private String name,commentVoice,replyVoice;

    public MyAdapter.onReplyClickListener getOnReplyClickListener() {
        return onReplyClickListener;
    }

    public void setOnReplyClickListener(MyAdapter.onReplyClickListener onReplyClickListener) {
        this.onReplyClickListener = onReplyClickListener;
    }

    public void setOnContentClickListener(MyAdapter.onContentClickListener onContentClickListener) {
        this.onContentClickListener = onContentClickListener;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            notifyDataSetChanged();//更新数据
        }
    };
    public MyAdapter(Context mContext, List<IssueAnswer.ListBean> comments) {
        this.mContext = mContext;
        this.comments = comments;
    }
    @Override
    public int getGroupCount() {
        return comments.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return comments.get(groupPosition).getListAnswer().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return comments.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return comments.get(groupPosition).getListAnswer().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 回复的view
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CommentHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.comment,parent,false);
            holder = new CommentHolder();
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            holder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.iv_avator = (CustomImageView) convertView.findViewById(R.id.iv_avatar);
            holder.ry_pic = (RecyclerView) convertView.findViewById(R.id.ry_pic);
            holder.btn_reply = (Button) convertView.findViewById(R.id.btn_reply);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.tv_hp = (TextView) convertView.findViewById(R.id.tv_hp);
            holder.tv_zp = (TextView) convertView.findViewById(R.id.tv_zp);
            holder.tv_cp = (TextView) convertView.findViewById(R.id.tv_cp);
            holder.iv_zan = (ImageView) convertView.findViewById(R.id.iv_zan);
            holder.ll_voice = (LinearLayout) convertView.findViewById(R.id.ll_voice);
            holder.id_recoder_anim = convertView.findViewById(R.id.id_recoder_anim);
            holder.iv_update = (Button) convertView.findViewById(R.id.iv_update);
            convertView.setTag(holder);
        }else {
            holder = (CommentHolder) convertView.getTag();
        }
        final IssueAnswer.ListBean comment2 = comments.get(groupPosition);
        final String voice = comment2.getAnswerVoice();
        setTouchDelegate(holder.iv_zan,200);
        if (comment2.getAnswerUser().length()==11){
            holder.tv_user_name.setText("".equals(comment2.getAnswerNickName())?parsePhone(comment2.getAnswerUser()):comment2.getAnswerNickName());
        }else {
            holder.tv_user_name.setText(comment2.getAnswerNickName());
        }
        holder.tv_zan.setText(TextUtils.isEmpty(comment2.getLikeCount())?"0":comment2.getLikeCount()+"");
        holder.tv_time.setText(comment2.getAnswerDate());
        holder.content.setText(comment2.getAnswerText());
        holder.tv_type.setText("农户");
        Glide.with(mContext).load(Constant.BASE_URL_EXPERTER+comment2.getAnswerUserImg()).crossFade().placeholder(R.mipmap.header_update).into(holder.iv_avator);
        final List<String> imgs = new ArrayList<>();
        if (!comment2.getAnswerImg().contains(",")){
            imgs.add(comment2.getAnswerImg());
        }else {
            String[] imgStr = comment2.getAnswerImg().split(",");
            imgs.addAll(Arrays.asList(imgStr));
        }
        if (!"".equals(imgs.get(0))){
            holder.ry_pic.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.ry_pic.setLayoutManager(manager);
            CommentPictureAdapter2 adapter2 = new CommentPictureAdapter2(mContext,imgs);
            holder.ry_pic.setAdapter(adapter2);
            adapter2.setOnItemClickLitener(new CommentPictureAdapter2.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent1 = new Intent(mContext, PictureScan.class);
                    intent1.putExtra("imgurl",Constant.BASE_URL_EXPERTER+imgs.get(position));
                    mContext.startActivity(intent1);
                }
            });
        }else {
            holder.ry_pic.setVisibility(View.GONE);
        }
        if (TextUtils.equals(ZPreferenceUtils.getPrefString(Constant.USERNAME,""),comment2.getAnswerUser())){
            holder.iv_update.setVisibility(View.VISIBLE);
        }else {
            holder.iv_update.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(voice)){
            holder.ll_voice.setVisibility(View.VISIBLE);
        }else {
            holder.ll_voice.setVisibility(View.GONE);
        }
        holder.btn_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onReplyClickListener.onReplyClick(v,groupPosition, comment2.getAnswerUser(),comment2.getAnswerNickName());
            }
        });
        holder.iv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextDialog editTextDialog = EditTextDialog.NewInstance(comment2.getId()+"");
                if (mContext instanceof Activity){
                    editTextDialog.show((((Activity) mContext).getFragmentManager()));
                }
            }
        });
        final CommentHolder finalHolder = holder;
        final View finalConvertView = convertView;
        holder.id_recoder_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先把语音下载下来在播放
                if ("".equals(ZPreferenceUtils.getPrefString(voice,""))){
                    OkGo.get(Constant.BASE_URL_EXPERTER+voice)
                            .tag(this)
                            .execute(new FileCallback() {
                                @Override
                                public void onSuccess(File file, Call call, Response response) {
                                    commentVoice = file.getAbsolutePath();
                                    //第一次下载后面播放本地文件
                                    ZPreferenceUtils.setPrefString(voice,voice);
                                    //播放动画（帧动画）
                                    if (finalHolder.id_recoder_anim != null) {
                                        finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                                        finalHolder.id_recoder_anim = null;
                                    }
                                    finalHolder.id_recoder_anim = finalConvertView.findViewById(R.id.id_recoder_anim);
                                    finalHolder.id_recoder_anim.setBackgroundResource(R.drawable.play_anim);
                                    AnimationDrawable animation1 = (AnimationDrawable) finalHolder.id_recoder_anim.getBackground();
                                    animation1.start();
                                    // 播放录音
                                    MediaManager.playSound(file.getAbsolutePath(),new MediaPlayer.OnCompletionListener() {

                                        public void onCompletion(MediaPlayer mp) {
                                            finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                                        }
                                    });
                                }
                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                }
                            });
                }else {
                    //判断文件是否被删除
                    if (!FileUtils.isFileExists(commentVoice)){
                       ToastUtils.showShort(mContext,"语音已过期或被清理");
                        return;
                    }
                    //播放动画（帧动画）
                    if (finalHolder.id_recoder_anim != null) {
                        finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                        finalHolder.id_recoder_anim = null;
                    }
                    finalHolder.id_recoder_anim = finalConvertView.findViewById(R.id.id_recoder_anim);
                    finalHolder.id_recoder_anim.setBackgroundResource(R.drawable.play_anim);
                    AnimationDrawable animation1 = (AnimationDrawable) finalHolder.id_recoder_anim.getBackground();
                    animation1.start();
                    // 播放录音
                    MediaManager.playSound(commentVoice,new MediaPlayer.OnCompletionListener() {

                        public void onCompletion(MediaPlayer mp) {
                            finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                        }
                    });
                }

            }
        });
        holder.tv_hp.setText(String.format("好评(%s)",comment2.getGood()));
        holder.tv_zp.setText(String.format("中评(%s)",comment2.getCentre()));
        holder.tv_cp.setText(String.format("差评(%s)",comment2.getBad()));
        List<ExperterInfo.RowsBean> infos;
//        infos = new ListDataSave(mContext,"experter").getDataListExperter("experter");
        infos = MyApplication.getInstance().getInfo();
        if (infos!=null&&infos.size()>0){
            for (int i=0;i<infos.size();i++){
                if (infos.get(i).getUid().equals(comment2.getAnswerUser())){
                    name = infos.get(i).getName();
                    String type = infos.get(i).getUsertype();
                    String avator = infos.get(i).getTitle();
//                    holder.tv_user_name.setText(name);
                    holder.tv_type.setText(type);
//                    holder.iv_avator.setTag(R.id.glide_tag,avator);
////                if (!"".equals(holder.iv_avator.getTag(R.id.glide_tag)) && holder.iv_avator.getTag(R.id.glide_tag).equals(avator)){
//                    Glide.with(mContext).load(String.format(Constant.IMAGE_URL, avator)).crossFade().into(holder.iv_avator);
//                }
                }
            }
        }
        //好评
        holder.tv_hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(comment2.getIsCentre())||"1".equals(comment2.getIsGood())||"1".equals(comment2.getIsBad())){
                    ToastUtils.showShort(mContext,"您已经给过评价了!");
                    return;
                }
                showPinLunDialog(R.string.hp,groupPosition,"good","good","好评成功!","好评失败");
            }
        });
        //中评
        holder.tv_zp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(comment2.getIsCentre())||"1".equals(comment2.getIsGood())||"1".equals(comment2.getIsBad())){
                    ToastUtils.showShort(mContext,"您已经给过评价了!");
                    return;
                }
                showPinLunDialog(R.string.zp,groupPosition,"centre","centre","中评成功!","中评失败");
            }
        });
        //差评
        holder.tv_cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(comment2.getIsCentre())||!"1".equals(comment2.getIsGood())||!"1".equals(comment2.getIsBad())){
                    ToastUtils.showShort(mContext,"您已经给过评价了!");
                    return;
                }
                showPinLunDialog(R.string.cp,groupPosition,"bad","bad","差评成功!","差评失败");
            }
        });
        //点赞
        holder.iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(comment2.getIsLike())){
                    OkGo.post(Constant.CANCEL_BINDING_LIKE)
                            .params("expertAccount",comment2.getAnswerUser())
                            .params("userAccount",ZPreferenceUtils.getPrefString(Constant.USERNAME,""))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    if ("1".equals(JsonUtils.getServerResult(s))){
                                        ToastUtils.showShort(mContext,"取消点赞成功！");
                                        mContext.sendBroadcast(new Intent("com.android.broadcast.REFRESH_ACTION"));
                                    }else {
                                        ToastUtils.showShort(mContext,"取消点赞失败:"+JsonUtils.getServerMsg(s));
                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    ToastUtils.showShort(mContext,"取消点赞失败,服务没有响应!");
                                }
                            });
                }else {
                    OkGo.post(Constant.ADD_BINDING_LIKE)
                            .params("expertAccount",comment2.getAnswerUser())
                            .params("userAccount", ZPreferenceUtils.getPrefString(Constant.USERNAME,""))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    if ("1".equals(JsonUtils.getServerResult(s))){
                                        ToastUtils.showShort(mContext,"点赞成功！");
                                        mContext.sendBroadcast(new Intent("com.android.broadcast.REFRESH_ACTION"));
                                    }else {
                                        ToastUtils.showShort(mContext,"点赞失败:"+JsonUtils.getServerMsg(s));
                                    }
                                }
                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    ToastUtils.showShort(mContext,"点赞失败,服务没有响应!");
                                }
                            });
                }

            }
        });
        return convertView;
    }

    /**
     * 评论的对话框
     * @param message
     * @param position
     * @param key
     * @param value
     * @param hint
     * @param hintErr
     */
    private void showPinLunDialog(int message, int position, final String key, final String value, final String hint, final String hintErr) {
        final IssueAnswer.ListBean comment2 = comments.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showLoadingDialog("加载中...");
                        OkGo.post(Constant.USER_COMMENT)
                                .params("binding",comment2.getAnswerUser())
                                .params("answerId",comment2.getId())
                                .params("user",ZPreferenceUtils.getPrefString(Constant.USERNAME,""))
                                .params(key,value)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        if ("1".equals(JsonUtils.getServerResult(s))){
                                            dismissLoadingDialog();
                                            ToastUtils.showShort(mContext,hint);
                                            mContext.sendBroadcast(new Intent("com.android.broadcast.REFRESH_ACTION"));
                                        }else {
                                            ToastUtils.showShort(mContext,hintErr+":"+JsonUtils.getServerMsg(s));
                                        }
                                    }
                                    @Override
                                    public void onError(Call call, Response response, Exception e) {
                                        super.onError(call, response, e);
                                        ToastUtils.showShort(mContext,"失败,服务器错误");
                                    }
                                });
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 追加的view
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ReplyHolder holder;
        if (convertView==null){
            holder = new ReplyHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.reply,parent,false);
            holder.issueUser = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.answerUser = (TextView) convertView.findViewById(R.id.tv_user_name2);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.iv_avator = (CustomImageView) convertView.findViewById(R.id.iv_avatar);
            holder.lv_photos = (RecyclerView) convertView.findViewById(R.id.ry_pic);
            holder.id_recoder_anim = convertView.findViewById(R.id.id_recoder_anim);
            holder.id_recoder_time = (TextView) convertView.findViewById(R.id.id_recoder_time);
            holder.ll_voice = (LinearLayout) convertView.findViewById(R.id.ll_voice);
            convertView.setTag(holder);
        }else {
            holder = (ReplyHolder) convertView.getTag();
        }

        final IssueAnswer.ListBean.ListAnswerBean comment = comments.get(groupPosition).getListAnswer().get(childPosition);
        final String voice = comment.getIssueVoice();
        Glide.with(mContext).load(Constant.BASE_URL_EXPERTER+comment.getIssueUserImg()).placeholder(R.mipmap.header_update).crossFade().into(holder.iv_avator);
        if (!"".equals(comment.getIssueImg())){
            holder.lv_photos.setVisibility(View.VISIBLE);
        }else {
            holder.lv_photos.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(comment.getIssueVoice())){
            holder.ll_voice.setVisibility(View.VISIBLE);
        }else {
            holder.ll_voice.setVisibility(View.GONE);
        }
        //图片
        final List<String> imgs = new ArrayList<>();
        if (!comment.getIssueImg().contains(",")){
            imgs.add(comment.getIssueImg());
        }else {
            String[] imgStr = comment.getIssueImg().split(",");
            imgs.addAll(Arrays.asList(imgStr));
        }
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.lv_photos.setLayoutManager(manager);
        pictureAdapter = new CommentPictureAdapter2(mContext,imgs);
        holder.lv_photos.setAdapter(pictureAdapter);
        pictureAdapter.setOnItemClickLitener(new CommentPictureAdapter2.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent1 = new Intent(mContext, PictureScan.class);
                intent1.putExtra("imgurl",Constant.BASE_URL_EXPERTER+imgs.get(position));
                mContext.startActivity(intent1);
            }
        });
        //语音
        final MyAdapter.ReplyHolder finalHolder = holder;
        final View finalConvertView1 = convertView;
        holder.id_recoder_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先把语音下载下来在播放
                if ("".equals(ZPreferenceUtils.getPrefString(voice,""))){
                    OkGo.get(Constant.BASE_URL_EXPERTER+voice)
                            .tag(this)
                            .execute(new FileCallback() {
                                @Override
                                public void onSuccess(File file, Call call, Response response) {
                                    replyVoice = file.getAbsolutePath();
                                    //第一次下载后面播放本地文件
                                    ZPreferenceUtils.setPrefString(voice,voice);
                                    //播放动画（帧动画）
                                    if (finalHolder.id_recoder_anim != null) {
                                        finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                                        finalHolder.id_recoder_anim = null;
                                    }
                                    finalHolder.id_recoder_anim = finalConvertView1.findViewById(R.id.id_recoder_anim);
                                    finalHolder.id_recoder_anim.setBackgroundResource(R.drawable.play_anim);
                                    AnimationDrawable animation1 = (AnimationDrawable) finalHolder.id_recoder_anim.getBackground();
                                    animation1.start();
                                    // 播放录音
                                    MediaManager.playSound(file.getAbsolutePath(),new MediaPlayer.OnCompletionListener() {

                                        public void onCompletion(MediaPlayer mp) {
                                            finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                                        }
                                    });
                                }
                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                }
                            });
                }else {
                    if (!FileUtils.isFileExists(replyVoice)){
                        ToastUtils.showShort(mContext,"语音已过期或被清理");
                        return;
                    }
                    //播放动画（帧动画）
                    if (finalHolder.id_recoder_anim != null) {
                        finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                        finalHolder.id_recoder_anim = null;
                    }
                    finalHolder.id_recoder_anim = finalConvertView1.findViewById(R.id.id_recoder_anim);
                    finalHolder.id_recoder_anim.setBackgroundResource(R.drawable.play_anim);
                    AnimationDrawable animation1 = (AnimationDrawable) finalHolder.id_recoder_anim.getBackground();
                    animation1.start();
                    // 播放录音
                    MediaManager.playSound(replyVoice,new MediaPlayer.OnCompletionListener() {

                        public void onCompletion(MediaPlayer mp) {
                            finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                        }
                    });
                }

            }
        });
//        holder.id_recoder_time.setText(Math.round(comment.getVoiceSeconds()) + "\"");
        if (comment.getIssueUser().length()==11){
            holder.issueUser.setText("".equals(comment.getIssueNickName())?parsePhone(comment.getIssueUser()):comment.getIssueNickName());
        }else {
            holder.issueUser.setText(comment.getIssueNickName());
        }
        if (comment.getAnswerUser().length()==11){
            holder.answerUser.setText("".equals(comment.getAnswerNickName())?parsePhone(comment.getAnswerUser())+":":comment.getAnswerNickName()+":");
        }else {
            holder.answerUser.setText(comment.getAnswerNickName()+":");
        }
        holder.content.setText(comment.getIssueText());
        holder.answerUser.setText("".equals(comment.getAnswerNickName())?parsePhone(comment.getAnswerUser())+":":comment.getAnswerNickName()+":");
        final View finalConvertView = convertView;
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContentClickListener.onContentClick(finalConvertView,groupPosition,childPosition,comment.getIssueUser(),comment.getIssueNickName());
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class CommentHolder{
        private TextView tv_user_name,tv_type,tv_zan,tv_time,content,tv_hp,tv_zp,tv_cp;
        private ImageView iv_avator,iv_zan;
        private RecyclerView ry_pic;
        private Button btn_reply,iv_update;
        private LinearLayout ll_voice;
        private View id_recoder_anim;
    }

    private class ReplyHolder{
        private TextView issueUser, answerUser, content, id_recoder_time,tv_time;
        private ImageView iv_avator;
        private RecyclerView lv_photos;
        private View id_recoder_anim;
        private LinearLayout ll_voice;
    }

    public interface onContentClickListener {
        void onContentClick(View v,int GroupPosition,int ChildPosition, String issueUser,String issueNiceName);
    }
    public interface onReplyClickListener{
        void onReplyClick(View v,int groupPosition,String issueUser,String issueNickName);
    }

    public void refresh(ExpandableListView expandableListView, int groupPosition){
        handler.sendMessage(new Message());
        //必须重新伸缩之后才能更新数据
        expandableListView.collapseGroup(groupPosition);
        expandableListView.expandGroup(groupPosition);
    }
    public void refresh(){
        handler.sendMessage(new Message());
    }
    /**
     * 显示进度对话框
     *
     * @param message {@link String} 消息文本
     */
    protected void showLoadingDialog(String message) {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(mContext);
        }
        loadingDialog.setMessage(message);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    /**
     * 取消进度对话框
     */
    private void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }
    /**
     * 提升点击范围
     *
     * @param view
     * @param expandTouchWidth
     */
    private void setTouchDelegate(final View view, final int expandTouchWidth) {
        final View parentView = (View) view.getParent();
        parentView.post(new Runnable() {
            @Override
            public void run() {
                final Rect rect = new Rect();
                view.getHitRect(rect);
                rect.top -= expandTouchWidth;
                rect.bottom += expandTouchWidth;
                rect.left -= expandTouchWidth;
                rect.right += expandTouchWidth;
                TouchDelegate touchDelegate = new TouchDelegate(rect, view);
                parentView.setTouchDelegate(touchDelegate);
            }
        });
    }

    private String parsePhone(String tel){
        return tel.substring(0,tel.length()-(tel.substring(3)).length())+"****"+tel.substring(7);
    }
}
