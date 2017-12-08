package com.nxt.ott.domain;

import java.io.Serializable;

public class Pesticide implements Serializable{
	//"Medication":"1008-1260克/公顷","Applicationmethod":"茎叶喷雾","other2":"null"

private String Registrationnumber;//证件号
private String Pesticidesname;//农药名称
private String Companyname;//公司名
private String Toxicity;//毒性
private String activeingredients;//有效成分
private String valid;//有效期
private String other1;//
private String other2;//
private String formulation;//剂型
private String Registrationname;//农作物
private String Preventname;//预防
private String Medication;//药量
private String Applicationmethod;//使用方式
public  String getRegistrationnumber() {
	return Registrationnumber;
}
public void setRegistrationnumber(String registrationnumber) {
	Registrationnumber = registrationnumber;
}
public String getPesticidesname() {
	return Pesticidesname;
}
public void setPesticidesname(String pesticidesname) {
	Pesticidesname = pesticidesname;
}
public String getCompanyname() {
	return Companyname;
}
public void setCompanyname(String companyname) {
	Companyname = companyname;
}
public String getToxicity() {
	return Toxicity;
}
public void setToxicity(String toxicity) {
	Toxicity = toxicity;
}
public String getActiveingredients() {
	return activeingredients;
}
public void setActiveingredients(String activeingredients) {
	this.activeingredients = activeingredients;
}
public String getValid() {
	return valid;
}
public void setValid(String valid) {
	this.valid = valid;
}
public String getOther1() {
	return other1;
}
public void setOther1(String other1) {
	this.other1 = other1;
}
public String getFormulation() {
	return formulation;
}
public void setFormulation(String formulation) {
	this.formulation = formulation;
}
public String getRegistrationname() {
	return Registrationname;
}
public void setRegistrationname(String registrationname) {
	Registrationname = registrationname;
}
public String getPreventname() {
	return Preventname;
}
public void setPreventname(String preventname) {
	Preventname = preventname;
}
public String getMedication() {
	return Medication;
}
public void setMedication(String medication) {
	Medication = medication;
}
public String getApplicationmethod() {
	return Applicationmethod;
}
public void setApplicationmethod(String applicationmethod) {
	Applicationmethod = applicationmethod;
}
public String getOther2() {
	return other2;
}
public void setOther2(String other2) {
	this.other2 = other2;
}

}
