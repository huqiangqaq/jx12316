package com.nxt.ott.domain;

import java.io.Serializable;

/**
 * Created by zhangyonglu on 2016/2/3 0003.
 */
public class DiseaseType implements Serializable{

private int id;
    private String sections;
    private String chineseName;
    private String englishName;
    private String symptomDataTableName;
    private String knowledgeDataTableName;
    private String diseaseDataTableName;
    private String testCaseDataTableName;
    private String testWeightDataTableName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getSymptomDataTableName() {
        return symptomDataTableName;
    }

    public void setSymptomDataTableName(String symptomDataTableName) {
        this.symptomDataTableName = symptomDataTableName;
    }

    public String getKnowledgeDataTableName() {
        return knowledgeDataTableName;
    }

    public void setKnowledgeDataTableName(String knowledgeDataTableName) {
        this.knowledgeDataTableName = knowledgeDataTableName;
    }

    public String getDiseaseDataTableName() {
        return diseaseDataTableName;
    }

    public void setDiseaseDataTableName(String diseaseDataTableName) {
        this.diseaseDataTableName = diseaseDataTableName;
    }

    public String getTestCaseDataTableName() {
        return testCaseDataTableName;
    }

    public void setTestCaseDataTableName(String testCaseDataTableName) {
        this.testCaseDataTableName = testCaseDataTableName;
    }

    public String getTestWeightDataTableName() {
        return testWeightDataTableName;
    }

    public void setTestWeightDataTableName(String testWeightDataTableName) {
        this.testWeightDataTableName = testWeightDataTableName;
    }
}
