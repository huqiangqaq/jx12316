package com.nxt.ott.domain;

/**
 * Created by huqiang on 2017/3/27 14:57.
 */

public class Recoder {
    private float time;
    private String filePath;

    public Recoder(float time, String filePath) {
        this.time = time;
        this.filePath = filePath;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
