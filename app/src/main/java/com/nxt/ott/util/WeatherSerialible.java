package com.nxt.ott.util;

import com.nxt.ott.domain.Weather;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangyonglu on 2016/3/2 0002.
 */
public class WeatherSerialible implements Serializable{
    private List<Weather> weatherList;

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}
