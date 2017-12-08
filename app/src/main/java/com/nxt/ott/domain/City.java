package com.nxt.ott.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/27.
 */
public class City implements Serializable {

    private int id;
    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
