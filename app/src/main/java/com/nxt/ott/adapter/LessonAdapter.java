package com.nxt.ott.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nxt.ott.R;
import com.nxt.ott.domain.LessonDetail;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;

import java.util.List;

/**
 * Updated by iwon on 2016/6/19 20:21.
 */
public class LessonAdapter extends ZBaseAdapter<LessonDetail> {
    public LessonAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_lesson_list, null, false);
            viewHolder.videoimg = (ImageView) convertView.findViewById(R.id.img_video);
            viewHolder.videonametext = (TextView) convertView.findViewById(R.id.tv_video_name);
            viewHolder.videotime = (TextView) convertView.findViewById(R.id.tv_video_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LessonDetail lesson = dataList.get(position);
        String imgurl = lesson.getVideoimage();
        if (!TextUtils.isEmpty(imgurl)) {
            imgurl = imgurl.replace("|", "http://182.116.57.246:27210");
            viewHolder.videoimg.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(imgurl,
                    new ImageViewAware(viewHolder.videoimg, false));
        } else {
            viewHolder.videoimg.setImageResource(R.mipmap.app_empty_icn);
        }
        if (!TextUtils.isEmpty(lesson.getName())) {
            viewHolder.videonametext.setText(lesson.getTitle());
        }

        if (TextUtils.isEmpty(lesson.getVideotime())) {
            viewHolder.videotime.setText(lesson.getCreated().split("\\.")[0]);
        }else{
            viewHolder.videotime.setText(lesson.getCreated().split("\\.")[0]);
        }
        //  System.out.println("video url-------->"+ videourl.replace("|", "http://182.116.57.246:27210"));
        return convertView;
    }

    class ViewHolder {
        ImageView videoimg;
        TextView videonametext;
        TextView videotime;
    }
}
