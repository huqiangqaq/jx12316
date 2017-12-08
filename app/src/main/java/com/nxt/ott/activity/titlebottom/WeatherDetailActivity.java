package com.nxt.ott.activity.titlebottom;

import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nxt.ott.Constant;
import com.nxt.ott.R;
import com.nxt.ott.adapter.WeekWeatherAdapter;
import com.nxt.ott.base.BaseLoadingTitleActivity;
import com.nxt.ott.domain.Weather;
import com.nxt.ott.util.CalendarUtil;
import com.nxt.zyl.util.TimeUtil;
import com.nxt.zyl.util.ZPreferenceUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Created by zhangyonglu on 2016/3/2 0002.
 */
public class WeatherDetailActivity extends BaseLoadingTitleActivity{
    private String weatherdata;
    private List<Weather> weatherList;
    private TextView weathertemtext,weatherareatext,weatherdetailtext;
    private GridView weathergrid;
    private Weather weather;
    private String nowtem,nowweather,date;
    private String data,area;
    @Override
    protected void initView() {
        application.addActivity(this);
        initTopbar(this,getString(R.string.weather_detail));
        weathertemtext= (TextView) findViewById(R.id.tv_nowweather_temp);
        weatherareatext= (TextView) findViewById(R.id.tv_area);
        weatherdetailtext= (TextView) findViewById(R.id.tv_nowweather_detail);
        weathergrid= (GridView) findViewById(R.id.gridview_week_weather);
        data = ZPreferenceUtils.getPrefString(Constant.WEATHER_DATA,"");
        area = ZPreferenceUtils.getPrefString(Constant.WEATHER_AREA,"");
        weatherList=new Gson().fromJson(data,new TypeToken<List<Weather>>(){}.getType());
        weather=weatherList.get(0);
        nowweather=weather.getDate().split(" ")[2];
        nowtem=nowweather.split("：")[1].replace(")","");
        date=weather.getDate().split(" ")[0];
        weathertemtext.setText(nowtem);
        weatherareatext.setText(area);
        weatherdetailtext.setText(weather.getWind()+"\t"+weather.getWeather()+"\n"+ new CalendarUtil(Calendar.getInstance()).getlunar()+ "\n"+date+"\t"+ TimeUtil.gethour()+":00"+"更新");
        for(int i=0;i<weatherList.size();i++){
            if(i==0) weatherList.get(i).setDate(date);
        }
        weathergrid.setAdapter(new WeekWeatherAdapter(this,weatherList));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_weather_detail;
    }



}
