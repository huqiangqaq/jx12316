package com.nxt.ott.domain;

/**
 * Created by huqiang on 2017/3/21 12:58.
 */

public class HotExperter {

    private String avator;
    private String name;
    private String tel;
    private String type;
    private String zixunfuwu;
    private String remarks;
    private String uid;
    private String jishuzhiwu;

    public HotExperter(String avator, String name, String tel, String type, String zixunfuwu, String remarks,String uid,String jishuzhiwu) {
        this.avator = avator;
        this.name = name;
        this.tel = tel;
        this.type = type;
        this.zixunfuwu = zixunfuwu;
        this.remarks = remarks;
        this.uid = uid;
        this.jishuzhiwu = jishuzhiwu;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZixunfuwu() {
        return zixunfuwu;
    }

    public void setZixunfuwu(String zixunfuwu) {
        this.zixunfuwu = zixunfuwu;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getJishuzhiwu() {
        return jishuzhiwu;
    }

    public void setJishuzhiwu(String jishuzhiwu) {
        this.jishuzhiwu = jishuzhiwu;
    }
}
