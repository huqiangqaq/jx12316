package com.nxt.ott.domain;

import java.io.Serializable;

/**
 * Created by xpeng on 2016/10/10.
 */

public class PestType implements Serializable {

    /**
     * id : 74
     * text : 病害1
     */

    private int id;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
