package com.nxt.ott.domain;

import java.io.Serializable;

/**
 * Created by xpeng on 2016/10/13.
 */

public class PestDetail implements Serializable{

    /**
     * ImgUrl2 : http://jx.12316.zq.yn15.com/upload/20161014/201610140204006835.png
     * ImgSmall : null
     * contentImg :
     * DispestMapid : 603
     * MapNo : 04003
     * Title : 疮痂病（黑星病）
     * Content :                                         病虫害的发生与流行病菌以菌丝体在枝稍的发病部位越冬，翌年4-5月份产生分生孢子、随风雨传播。侵染果实的潜育期较长、约为40-70天。病菌的发生和流行与春季、初夏的温湿度关系密切，最适温度为20-28℃、相对湿度80%。果园低洼郁闭、多雨潮湿发病较重；果实一般6月份开始发病、7-8月份为发病盛期。早熟品种发病较劲、中晚熟品发病较重尤其是黄肉桃油桃品种。  防治措施：落花后15天至6月份。没间隔15天喷一次苯醚甲环唑或阿醚达，混配甲基硫菌灵和代锰
     * ImgUrl : ~/upload/20161014/201610140204006835.png
     * Dtypeid : 79
     * Dtypename : 桃树病虫害
     * ModifyDate : 2016-10-14
     * ModifyUserId : 0
     * ModifyUserName : null
     * CreateUserName : null
     * CreateUserId : 0
     * CreateDate : 2016-10-14
     * Isenable : true
     * Isdelete : false
     */

    private String ImgUrl2;
    private String ImgSmall;
    private String contentImg;
    private int DispestMapid;
    private String MapNo;
    private String Title;
    private String Content;
    private String ImgUrl;
    private int Dtypeid;
    private String Dtypename;
    private String ModifyDate;
    private int ModifyUserId;
    private String ModifyUserName;
    private String CreateUserName;
    private int CreateUserId;
    private String CreateDate;
    private boolean Isenable;
    private boolean Isdelete;

    public String getImgUrl2() {
        return ImgUrl2;
    }

    public void setImgUrl2(String ImgUrl2) {
        this.ImgUrl2 = ImgUrl2;
    }

    public Object getImgSmall() {
        return ImgSmall;
    }

    public void setImgSmall(String ImgSmall) {
        this.ImgSmall = ImgSmall;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public int getDispestMapid() {
        return DispestMapid;
    }

    public void setDispestMapid(int DispestMapid) {
        this.DispestMapid = DispestMapid;
    }

    public String getMapNo() {
        return MapNo;
    }

    public void setMapNo(String MapNo) {
        this.MapNo = MapNo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String ImgUrl) {
        this.ImgUrl = ImgUrl;
    }

    public int getDtypeid() {
        return Dtypeid;
    }

    public void setDtypeid(int Dtypeid) {
        this.Dtypeid = Dtypeid;
    }

    public String getDtypename() {
        return Dtypename;
    }

    public void setDtypename(String Dtypename) {
        this.Dtypename = Dtypename;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(String ModifyDate) {
        this.ModifyDate = ModifyDate;
    }

    public int getModifyUserId() {
        return ModifyUserId;
    }

    public void setModifyUserId(int ModifyUserId) {
        this.ModifyUserId = ModifyUserId;
    }

    public Object getModifyUserName() {
        return ModifyUserName;
    }

    public void setModifyUserName(String ModifyUserName) {
        this.ModifyUserName = ModifyUserName;
    }

    public Object getCreateUserName() {
        return CreateUserName;
    }

    public void setCreateUserName(String CreateUserName) {
        this.CreateUserName = CreateUserName;
    }

    public int getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(int CreateUserId) {
        this.CreateUserId = CreateUserId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public boolean isIsenable() {
        return Isenable;
    }

    public void setIsenable(boolean Isenable) {
        this.Isenable = Isenable;
    }

    public boolean isIsdelete() {
        return Isdelete;
    }

    public void setIsdelete(boolean Isdelete) {
        this.Isdelete = Isdelete;
    }
}
