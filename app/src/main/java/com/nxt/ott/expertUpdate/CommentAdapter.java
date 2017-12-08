package com.nxt.ott.expertUpdate;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.CommentPictureAdapter2;
import com.nxt.ott.domain.Comment;
import com.nxt.ott.util.MediaManager;

import java.util.ArrayList;
import java.util.List;

import static com.nxt.ott.R.id.lv_pic;

/**
 * Created by huqiang on 2017/3/22 13:01.
 */

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> datas = new ArrayList<>();
    private onContentClickListener onContentClickListener;
    private SparseArray<Boolean> map = new SparseArray<>();
    private CommentPictureAdapter2 pictureAdapter;

    public void setOnContentClickListener(CommentAdapter.onContentClickListener onContentClickListener) {
        this.onContentClickListener = onContentClickListener;
    }

    public CommentAdapter(Context mContext, List<Comment> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
            holder.issueUser = (TextView) convertView.findViewById(R.id.issueUser);
            holder.answerUser = (TextView) convertView.findViewById(R.id.answerUser);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.tv_message = (TextView) convertView.findViewById(R.id.tv_message);
            holder.iv_zan = (Button) convertView.findViewById(R.id.iv_zan);
            holder.iv_reply = (Button) convertView.findViewById(R.id.iv_reply);
            holder.lv_photos = (RecyclerView) convertView.findViewById(lv_pic);
            holder.id_recoder_anim = convertView.findViewById(R.id.id_recoder_anim);
            holder.id_recoder_time = (TextView) convertView.findViewById(R.id.id_recoder_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Comment comment = datas.get(position);
        map.put(position, comment.isReply());
        if (comment.getPicPaths().size()>0){
            holder.lv_photos.setVisibility(View.VISIBLE);
        }else {
            holder.lv_photos.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(comment.getVoicePath())){
            holder.id_recoder_anim.setVisibility(View.VISIBLE);
            holder.id_recoder_time.setVisibility(View.VISIBLE);
        }else {
            holder.id_recoder_anim.setVisibility(View.GONE);
            holder.id_recoder_time.setVisibility(View.GONE);
        }
        //图片
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.lv_photos.setLayoutManager(manager);
        pictureAdapter = new CommentPictureAdapter2(mContext, comment.getPicPaths());
        holder.lv_photos.setAdapter(pictureAdapter);
        //语音
        final ViewHolder finalHolder = holder;
        final View finalConvertView = convertView;
        holder.id_recoder_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 播放动画（帧动画）
                if (finalHolder.id_recoder_anim != null) {
                    finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                    finalHolder.id_recoder_anim = null;
                }
                finalHolder.id_recoder_anim = finalConvertView.findViewById(R.id.id_recoder_anim);
                finalHolder.id_recoder_anim.setBackgroundResource(R.drawable.play_anim);
                AnimationDrawable animation = (AnimationDrawable) finalHolder.id_recoder_anim.getBackground();
                animation.start();
                // 播放录音
                MediaManager.playSound(Constant.BASE_URL_EXPERTER+comment.getVoicePath(), new MediaPlayer.OnCompletionListener() {

                    public void onCompletion(MediaPlayer mp) {
                        finalHolder.id_recoder_anim.setBackgroundResource(R.mipmap.adj);
                    }
                });
            }
        });


        holder.id_recoder_time.setText(Math.round(comment.getVoiceSeconds()) + "\"");
        if (map.get(position)) {
            holder.tv_message.setVisibility(View.VISIBLE);
            holder.answerUser.setVisibility(View.VISIBLE);
            holder.issueUser.setText(comment.getIssueUser());
            holder.answerUser.setText(comment.getAnswerUser());
            holder.content.setText(comment.getAnswerText());
        } else {
            holder.tv_message.setVisibility(View.GONE);
            holder.issueUser.setText(comment.getIssueUser() + ":");
            holder.answerUser.setVisibility(View.GONE);
            holder.content.setText(comment.getAnswerText());
        }
        holder.iv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String issueUser = comment.getIssueUser();
                onContentClickListener.onContentClick(v, issueUser);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        private TextView issueUser, answerUser, content, tv_message, id_recoder_time;
        private Button iv_zan, iv_reply;
        private RecyclerView lv_photos;
        private View id_recoder_anim;
    }

    public void AddItem(String issueUser, String content, String answer, boolean isReply, List<String> picPaths, String voicePath, float voiceSeconds) {
        datas.add(new Comment(issueUser, content, answer, isReply, picPaths, voicePath, voiceSeconds));
        notifyDataSetChanged();
    }
    interface onContentClickListener {
        void onContentClick(View v, String issueUser);
    }
}
