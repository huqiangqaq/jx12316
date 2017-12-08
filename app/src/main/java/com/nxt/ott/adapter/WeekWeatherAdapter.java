package com.nxt.ott.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nxt.ott.R;
import com.nxt.ott.domain.Weather;
import com.nxt.ott.util.WeatherUtil;
import com.nxt.zyl.util.TimeUtil;

import java.util.List;

/**
 * Created by 张永露 on 2015/8/17 0017.
 */
public class WeekWeatherAdapter extends BaseAdapter {
    private Context context;
    private int layoutid;
    private List<Weather> weatherlist;
    int[] imgids;
    Drawable drawable;
    int type;

    public WeekWeatherAdapter(Context context, List<Weather> weatherlist) {
        this.context = context;
        this.weatherlist = weatherlist;
    }

    @Override
    public int getCount() {
        return weatherlist.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.week_weather_list, null);
            holder.weatherview = (TextView) convertView.findViewById(R.id.tv_week_weather);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Weather weather = weatherlist.get(position);
        drawable = context.getResources().getDrawable(WeatherUtil.getImageresource(weatherlist.get(position).getWeather(),"biz_plugin_weather_"));
        drawable.setBounds(0, 0, 80, 80);
        holder.weatherview.setText(TimeUtil.getdatalist().get(position) + "\n\n" + weather.getWeather() + "\n\n" + weather.getTemperature());
        holder.weatherview.setCompoundDrawables(null, null, null, drawable);
        return convertView;
    }

    class Holder {
        TextView weatherview;
    }
}
