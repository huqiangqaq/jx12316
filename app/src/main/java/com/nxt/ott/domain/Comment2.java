package com.nxt.ott.domain;

import java.util.List;

/**
 * Created by huqiang on 2017/4/5 13:17.
 */

public class Comment2 {
    private String userName;
    private String time;
    private int zhaNum;
    private String content;
    private List<String> imgs;
    private List<Comment> replys;
    private String voicePath;

    public Comment2(String userName, String time, int zhaNum, String content, List<String> imgs, String voicePath,List<Comment> replys) {
        this.userName = userName;
        this.time = time;
        this.zhaNum = zhaNum;
        this.content = content;
        this.imgs = imgs;
        this.voicePath = voicePath;
        this.replys = replys;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getZhaNum() {
        return zhaNum;
    }

    public void setZhaNum(int zhaNum) {
        this.zhaNum = zhaNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public List<Comment> getReplys() {
        return replys;
    }

    public void setReplys(List<Comment> replys) {
        this.replys = replys;
    }
}
