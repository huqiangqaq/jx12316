package com.nxt.ott.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huqiang on 2017/12/11 9:51.
 */

public class BaseExperter extends BaseType {

    @SerializedName("atype")
    private String atype;

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }
}
