package com.nxt.ott.domain;

/**
 * Created by huqiang on 2017/12/8 14:44.
 */

public class Detail {


    /**
     * result : ok
     * zhresult : ok
     * title : 江西能种植油用牡丹吗？
     * info : 您好！曹老师，我是江西修水县的，请问我这里能种植油用牡丹吗？江西是否有种植的？谢谢！
     * zinfo : {"img":null,"name":"12316微信客服","type":"通用","info":"面向三农提供政策法规、生产技术、市场信息等咨询服务。"}
     * hinfo : 您好！江西地理条件优越，种植经验丰富，适合种植油用牡丹，在高安市、赣州等地都有种植。具体种植技术建议您咨询林业部门，在此也经网络查询到了宜春高安市赣平油用牡丹种植专业合作社，供您自行参考：胡声平 联系电话：18279536128
     * stype : 0
     * questionId : 0
     * asktype : 0
     * askId : 0
     */

    private String result;
    private String zhresult;
    private String title;
    private String info;
    private String img;
    private ZinfoBean zinfo;
    private String hinfo;
    private String stype;
    private String questionId;
    private String asktype;
    private String askId;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getZhresult() {
        return zhresult;
    }

    public void setZhresult(String zhresult) {
        this.zhresult = zhresult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ZinfoBean getZinfo() {
        return zinfo;
    }

    public void setZinfo(ZinfoBean zinfo) {
        this.zinfo = zinfo;
    }

    public String getHinfo() {
        return hinfo;
    }

    public void setHinfo(String hinfo) {
        this.hinfo = hinfo;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAsktype() {
        return asktype;
    }

    public void setAsktype(String asktype) {
        this.asktype = asktype;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static class ZinfoBean {
        /**
         * img : null
         * name : 12316微信客服
         * type : 通用
         * info : 面向三农提供政策法规、生产技术、市场信息等咨询服务。
         */

        private Object img;
        private String name;
        private String type;
        private String info;

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
