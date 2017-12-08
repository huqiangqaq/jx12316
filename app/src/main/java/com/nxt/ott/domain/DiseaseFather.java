package com.nxt.ott.domain;

import java.util.List;

/**
 * Created by zhangyonglu on 2016/2/1 0001.
 */
public class DiseaseFather {
    private String fathername;
    private List<String> content;

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
