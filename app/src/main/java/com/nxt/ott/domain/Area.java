package com.nxt.ott.domain;

import java.io.Serializable;

/**
 * Created by zhangyonglu on 2016/3/7 0007.
 */
public class Area implements Serializable{
    private String name;
    private String header;
    private String pinyin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
