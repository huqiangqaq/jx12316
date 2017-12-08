package com.nxt.ott.domain;

import java.io.Serializable;

/**
 * Updated by iwon on 2016/6/19 20:22.
 */
public class LessonDetail implements Serializable{
//    "id": "992d0961dc0149639ce570b9d00dc917",
//            "isNewRecord": false,
//            "remarks": "通过学习视频可以科学管理规模化散养鸡",
//            "created": "2016-05-07 13:16:00",
//            "updateDate": "2016-05-07 13:16:00",
//            "classifyid": "6ece0d60d2384aedae0a44f1dcddfd21",
//            "videoimage": "|/atc-console/userfiles/1/files/video/video/2016/05/%E8%A7%84%E6%A8%A1%E5%8C%96%E6%95%A3%E5%85%BB%E9%B8%A1%E7%9A%84%E7%A7%91%E5%AD%A6%E7%AE%A1%E7%90%86.png",
//            "name": "规模化散养鸡的科学管理",
//            "url": "|/atc-console/userfiles/1/files/video/video/2016/05/%E8%A7%84%E6%A8%A1%E5%8C%96%E6%95%A3%E5%85%BB%E9%B8%A1%E7%9A%84%E7%A7%91%E5%AD%A6%E7%AE%A1%E7%90%86.flv",
//            "videosize": null,

    private String id;
    private String remarks;
    private String videoimage;
    private String name;
    private String url;
    private String videosize;
    private String videotime;
    private String title;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    private String created;
    private String updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getVideoimage() {
        return videoimage;
    }

    public void setVideoimage(String videoimage) {
        this.videoimage = videoimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideosize() {
        return videosize;
    }

    public void setVideosize(String videosize) {
        this.videosize = videosize;
    }

    public String getVideotime() {
        return videotime;
    }

    public void setVideotime(String videotime) {
        this.videotime = videotime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//            "videotime": null,
//            "title": "规模化散养鸡的科学管理"


    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
