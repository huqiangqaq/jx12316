package com.nxt.ott.domain;

import java.util.List;

/**
 * Created by huqiang on 2017/3/22 12:50.
 */

public class Comment {

    private String issueUser;
    private String answerText;
    private String answerUser;
    private boolean isReply;
    private List<String> picPaths;
    private String voicePath;
    private float voiceSeconds;

    public Comment(String issueUser, String answerText, String answerUser,boolean isReply,List<String> picPaths,String voicePath,float voiceSeconds) {
        this.issueUser = issueUser;
        this.answerText = answerText;
        this.answerUser = answerUser;
        this.isReply = isReply;
        this.picPaths = picPaths;
        this.voicePath = voicePath;
        this.voiceSeconds = voiceSeconds;
    }

    public String getIssueUser() {
        return issueUser;
    }

    public void setIssueUser(String issueUser) {
        this.issueUser = issueUser;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    public List<String> getPicPaths() {
        return picPaths;
    }

    public void setPicPaths(List<String> picPaths) {
        this.picPaths = picPaths;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public float getVoiceSeconds() {
        return voiceSeconds;
    }

    public void setVoiceSeconds(float voiceSeconds) {
        this.voiceSeconds = voiceSeconds;
    }
}
