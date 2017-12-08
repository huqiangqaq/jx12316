package com.nxt.ott.domain;

import java.io.Serializable;

/**
 * @author koneloong koneloong@gmail.com
 *         Created on 2015/3/5 14:01.
 */
public class AppInfo implements Serializable {
    private int infoId;
    private String apkUrl;
    private String packageName;
    private String updateInfo;
    private String uploadDate;
    private Integer versionCode;
    private String versionName;
    private Boolean isRecommend;
    private String appName;
    private String logoUrl;

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "infoId=" + infoId +
                ", apkUrl='" + apkUrl + '\'' +
                ", packageName='" + packageName + '\'' +
                ", updateInfo='" + updateInfo + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", isRecommend=" + isRecommend +
                ", appName='" + appName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
