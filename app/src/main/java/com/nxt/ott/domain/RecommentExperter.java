package com.nxt.ott.domain;

import java.util.List;

/**
 * Created by huqiang on 2017/3/23 10:52.
 */

public class RecommentExperter {

    /**
     * Rows : [{"uid":"13607025761","zhiwuid":null,"officetel":"0791-86200911","tel":"13755796111","workUnit":"厅政策法规处","workEdate":null,"isDelete":0,"zixunfuwu":"农业法律","policyzhiwu":"","level":null,"schools":"南昌大学","age":"","jishuzhiwu":"","ID":913,"IsMien":null,"status":"","jiguan":"","dname":"江西省","portal":null,"idcard":"","professional":"法律","IsToday":null,"d_id":1,"email":"","workMony":null,"Policyinfo":"党员","beiyong2":null,"Delete_time":null,"beiyong1":"360100","postid":732,"birthday":"","sex":"男","sort":null,"btMony":null,"remarks":"","minzu":"","newsids":null,"education":"本科","rudangdate":null,"yewuzhuanchang":"政策法规","zhiwu":"政策法规处","title":"20160707091645A31801.jpg","name":"邱和生","workSdate":null,"jizhidate":null,"post":"政策法规处","canlogin":1,"theme":null,"usertype":"专家","EntryDate":"1992-08-01","pwd":"E10ADC3949BA59ABBE56E057F20F883E","jibieMony":null,"address":"江西省农业厅","Pcode":"","tokencode":null},{"uid":"13607025761","zhiwuid":1,"officetel":"","tel":"13677090851","workUnit":"厅政策法规处","workEdate":null,"isDelete":0,"zixunfuwu":"法律法规、行政执法监督等农业法律","policyzhiwu":"","level":null,"schools":"中国政法大学","age":"34","jishuzhiwu":"","ID":912,"IsMien":null,"status":"","jiguan":"","dname":"管理员","portal":null,"idcard":"362526198105212044","professional":"法律","IsToday":null,"d_id":1,"email":"","workMony":null,"Policyinfo":"","beiyong2":null,"Delete_time":null,"beiyong1":"360101","postid":1,"birthday":"","sex":"女","sort":null,"btMony":null,"remarks":"多次参与编写行政执法人员培训用书《行政执法监督章节》","minzu":"","newsids":null,"education":"在职研究生","rudangdate":null,"yewuzhuanchang":"政策法规","zhiwu":"默认职务","title":"","name":"董蕾","workSdate":null,"jizhidate":null,"post":"tess","canlogin":1,"theme":null,"usertype":"专家","EntryDate":"","pwd":"E10ADC3949BA59ABBE56E057F20F883E","jibieMony":null,"address":"江西省农业厅","Pcode":"","tokencode":null}]
     * code : 1
     * msg : 获取成功！
     * Total : 2
     */

    private String code;
    private String msg;
    private int Total;
    private String type;
    private List<RowsBean> Rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<RowsBean> getRows() {
        return Rows;
    }

    public void setRows(List<RowsBean> Rows) {
        this.Rows = Rows;
    }

    public static class RowsBean {
        /**
         * uid : 13607025761
         * zhiwuid : null
         * officetel : 0791-86200911
         * tel : 13755796111
         * workUnit : 厅政策法规处
         * workEdate : null
         * isDelete : 0
         * zixunfuwu : 农业法律
         * policyzhiwu :
         * level : null
         * schools : 南昌大学
         * age :
         * jishuzhiwu :
         * ID : 913
         * IsMien : null
         * status :
         * jiguan :
         * dname : 江西省
         * portal : null
         * idcard :
         * professional : 法律
         * IsToday : null
         * d_id : 1
         * email :
         * workMony : null
         * Policyinfo : 党员
         * beiyong2 : null
         * Delete_time : null
         * beiyong1 : 360100
         * postid : 732
         * birthday :
         * sex : 男
         * sort : null
         * btMony : null
         * remarks :
         * minzu :
         * newsids : null
         * education : 本科
         * rudangdate : null
         * yewuzhuanchang : 政策法规
         * zhiwu : 政策法规处
         * title : 20160707091645A31801.jpg
         * name : 邱和生
         * workSdate : null
         * jizhidate : null
         * post : 政策法规处
         * canlogin : 1
         * theme : null
         * usertype : 专家
         * EntryDate : 1992-08-01
         * pwd : E10ADC3949BA59ABBE56E057F20F883E
         * jibieMony : null
         * address : 江西省农业厅
         * Pcode :
         * tokencode : null
         */

        private String uid;
        private Object zhiwuid;
        private String officetel;
        private String tel;
        private String workUnit;
        private Object workEdate;
        private int isDelete;
        private String zixunfuwu;
        private String policyzhiwu;
        private Object level;
        private String schools;
        private String age;
        private String jishuzhiwu;
        private int ID;
        private Object IsMien;
        private String status;
        private String jiguan;
        private String dname;
        private Object portal;
        private String idcard;
        private String professional;
        private Object IsToday;
        private int d_id;
        private String email;
        private Object workMony;
        private String Policyinfo;
        private Object beiyong2;
        private Object Delete_time;
        private String beiyong1;
        private int postid;
        private String birthday;
        private String sex;
        private Object sort;
        private Object btMony;
        private String remarks;
        private String minzu;
        private Object newsids;
        private String education;
        private Object rudangdate;
        private String yewuzhuanchang;
        private String zhiwu;
        private String title;
        private String name;
        private Object workSdate;
        private Object jizhidate;
        private String post;
        private int canlogin;
        private Object theme;
        private String usertype;
        private String EntryDate;
        private String pwd;
        private Object jibieMony;
        private String address;
        private String Pcode;
        private Object tokencode;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Object getZhiwuid() {
            return zhiwuid;
        }

        public void setZhiwuid(Object zhiwuid) {
            this.zhiwuid = zhiwuid;
        }

        public String getOfficetel() {
            return officetel;
        }

        public void setOfficetel(String officetel) {
            this.officetel = officetel;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getWorkUnit() {
            return workUnit;
        }

        public void setWorkUnit(String workUnit) {
            this.workUnit = workUnit;
        }

        public Object getWorkEdate() {
            return workEdate;
        }

        public void setWorkEdate(Object workEdate) {
            this.workEdate = workEdate;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getZixunfuwu() {
            return zixunfuwu;
        }

        public void setZixunfuwu(String zixunfuwu) {
            this.zixunfuwu = zixunfuwu;
        }

        public String getPolicyzhiwu() {
            return policyzhiwu;
        }

        public void setPolicyzhiwu(String policyzhiwu) {
            this.policyzhiwu = policyzhiwu;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public String getSchools() {
            return schools;
        }

        public void setSchools(String schools) {
            this.schools = schools;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getJishuzhiwu() {
            return jishuzhiwu;
        }

        public void setJishuzhiwu(String jishuzhiwu) {
            this.jishuzhiwu = jishuzhiwu;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public Object getIsMien() {
            return IsMien;
        }

        public void setIsMien(Object IsMien) {
            this.IsMien = IsMien;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getJiguan() {
            return jiguan;
        }

        public void setJiguan(String jiguan) {
            this.jiguan = jiguan;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public Object getPortal() {
            return portal;
        }

        public void setPortal(Object portal) {
            this.portal = portal;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getProfessional() {
            return professional;
        }

        public void setProfessional(String professional) {
            this.professional = professional;
        }

        public Object getIsToday() {
            return IsToday;
        }

        public void setIsToday(Object IsToday) {
            this.IsToday = IsToday;
        }

        public int getD_id() {
            return d_id;
        }

        public void setD_id(int d_id) {
            this.d_id = d_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getWorkMony() {
            return workMony;
        }

        public void setWorkMony(Object workMony) {
            this.workMony = workMony;
        }

        public String getPolicyinfo() {
            return Policyinfo;
        }

        public void setPolicyinfo(String Policyinfo) {
            this.Policyinfo = Policyinfo;
        }

        public Object getBeiyong2() {
            return beiyong2;
        }

        public void setBeiyong2(Object beiyong2) {
            this.beiyong2 = beiyong2;
        }

        public Object getDelete_time() {
            return Delete_time;
        }

        public void setDelete_time(Object Delete_time) {
            this.Delete_time = Delete_time;
        }

        public String getBeiyong1() {
            return beiyong1;
        }

        public void setBeiyong1(String beiyong1) {
            this.beiyong1 = beiyong1;
        }

        public int getPostid() {
            return postid;
        }

        public void setPostid(int postid) {
            this.postid = postid;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public Object getBtMony() {
            return btMony;
        }

        public void setBtMony(Object btMony) {
            this.btMony = btMony;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getMinzu() {
            return minzu;
        }

        public void setMinzu(String minzu) {
            this.minzu = minzu;
        }

        public Object getNewsids() {
            return newsids;
        }

        public void setNewsids(Object newsids) {
            this.newsids = newsids;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public Object getRudangdate() {
            return rudangdate;
        }

        public void setRudangdate(Object rudangdate) {
            this.rudangdate = rudangdate;
        }

        public String getYewuzhuanchang() {
            return yewuzhuanchang;
        }

        public void setYewuzhuanchang(String yewuzhuanchang) {
            this.yewuzhuanchang = yewuzhuanchang;
        }

        public String getZhiwu() {
            return zhiwu;
        }

        public void setZhiwu(String zhiwu) {
            this.zhiwu = zhiwu;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getWorkSdate() {
            return workSdate;
        }

        public void setWorkSdate(Object workSdate) {
            this.workSdate = workSdate;
        }

        public Object getJizhidate() {
            return jizhidate;
        }

        public void setJizhidate(Object jizhidate) {
            this.jizhidate = jizhidate;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public int getCanlogin() {
            return canlogin;
        }

        public void setCanlogin(int canlogin) {
            this.canlogin = canlogin;
        }

        public Object getTheme() {
            return theme;
        }

        public void setTheme(Object theme) {
            this.theme = theme;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getEntryDate() {
            return EntryDate;
        }

        public void setEntryDate(String EntryDate) {
            this.EntryDate = EntryDate;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public Object getJibieMony() {
            return jibieMony;
        }

        public void setJibieMony(Object jibieMony) {
            this.jibieMony = jibieMony;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPcode() {
            return Pcode;
        }

        public void setPcode(String Pcode) {
            this.Pcode = Pcode;
        }

        public Object getTokencode() {
            return tokencode;
        }

        public void setTokencode(Object tokencode) {
            this.tokencode = tokencode;
        }
    }
}
