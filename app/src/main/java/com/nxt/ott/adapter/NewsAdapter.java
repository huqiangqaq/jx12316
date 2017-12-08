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
import com.nxt.ott.domain.News;
import com.nxt.zyl.ui.adapter.ZBaseAdapter;

import java.util.List;


/**
 * @author koneloong koneloong@gmail.com
 *         Created on 2015/7/29 14:50.
 */
public class NewsAdapter extends ZBaseAdapter<News> {

    public NewsAdapter(Context context, List<News> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
//        if (convertView == null) {
        convertView = mLayoutInflater.inflate(R.layout.layout_news_item, parent, false);
        holder = new Holder();
        holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
        holder.tvSource = (TextView) convertView.findViewById(R.id.tv_source);
//        holder.imageView = (ImageView) convertView.findViewById(R.id.imgview);
        convertView.setTag(holder);
//        } else {
//            holder = (Holder) convertView.getTag();
//        }

        News article = dataList.get(position);
//        if (!TextUtils.isEmpty(article.getImagePath()) && (article.getImagePath().trim().length() > 0)) {
//            holder.imageView.setVisibility(View.VISIBLE);
//            ImageLoader.getInstance().displayImage(article.getImagePath(),
//                    new ImageViewAware(holder.imageView, false));
//        } else {
//            holder.imageView.setVisibility(View.GONE);
//        }
        if (!TextUtils.isEmpty(article.getTitle()))
            holder.tvTitle.setText(article.getTitle());
        if (!TextUtils.isEmpty(article.getSourceName().trim()))
            holder.tvSource.setText(article.getSourceName());
        if (!TextUtils.isEmpty(article.getPublishDateStr().trim()))
            holder.tvTime.setText(article.getPublishDateStr());
        return convertView;
    }

    class Holder {
        TextView tvTitle, tvSource, tvTime;
//        ImageView imageView;
    }
}
