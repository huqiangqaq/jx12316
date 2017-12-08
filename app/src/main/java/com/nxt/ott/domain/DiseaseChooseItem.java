package com.nxt.ott.domain;

import java.io.Serializable;

/**
 * Created by zhangyonglu on 2016/2/3 0003.
 */
public class DiseaseChooseItem implements Serializable{
   // "id":1,"symptomNum":1,"symptomName":"渐瘦","symptomGroup":"体况","sections":"猪常见传染病"},{"id":2
private int id;
private int symptomNum;
private String symptomName;
private String symptomGroup;
private String sections;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSymptomNum() {
        return symptomNum;
    }

    public void setSymptomNum(int symptomNum) {
        this.symptomNum = symptomNum;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getSymptomGroup() {
        return symptomGroup;
    }

    public void setSymptomGroup(String symptomGroup) {
        this.symptomGroup = symptomGroup;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }
}
