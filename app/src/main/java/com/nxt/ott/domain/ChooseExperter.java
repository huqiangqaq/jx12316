package com.nxt.ott.domain;

import java.util.List;

/**
 * Created by huqiang on 2017/12/11 10:43.
 */

public class ChooseExperter {


    /**
     * result : ok
     * totalpage : 23
     * data : [{"id":"273","atype":"27","type":"气象专家","aname":"蔡哲","aphone":"13870643470","apic":"http://v426.nyt.bd.ej38.com/UpLoad/image/20170926/20170926134536_65118.jpg","ainfo":"江西省农业气象中心高级工程师，主要从事农业气象服务工作。","atime":"2017-09-14 16:37:55"},{"id":"272","atype":"27","type":"气象专家","aname":"姚俊萌","aphone":"15879086827","apic":"http://v426.nyt.bd.ej38.com/UpLoad/image/20170926/20170926134502_70174.jpg","ainfo":"江西省农业气象中心工程师，主要从事农业气象防灾减灾工作和气象对农业生产影响相关研究","atime":"2017-09-14 16:21:08"},{"id":"270","atype":"22","type":"通用","aname":"平台测试（不要提问）","aphone":"15679280526","apic":"http://wx.qlogo.cn/mmopen/aM085wB0rBGMvqNqffGdkDNKjk4ll5bgoP3jy17bDdnTGOrhoMcDhINbdua5SaQibnJkpUdXibm0ibibzMe2avrv8cEDLxNSAD9V/0","ainfo":"测试","atime":"2017-08-24 09:48:34"},{"id":"265","atype":"4","type":"水产技术","aname":"胡火根","aphone":"13970996311","apic":"http://v426.nyt.bd.ej38.com/UpLoad/image/20170711/20170711100532_91419.jpg","ainfo":"胡火根，男，江西省水产技术推广站研究员，主要从事水产技术推广服务工作。","atime":"2017-07-11 09:35:03"},{"id":"264","atype":"13","type":"畜牧技术","aname":"杨柳","aphone":"15907082202","apic":"http://v426.nyt.bd.ej38.com/UpLoad/image/20170710/20170710163040_55422.jpg","ainfo":"从事蜂业技术科研与推广近40年，发表蜂业技术论文20余篇，获得多项技术成果。尤其善长于蜜蜂养殖技术和蜜蜂病虫害防治技术。对蜜蜂经济生物学与运用有独道的研究。","atime":"2017-06-26 15:49:45"},{"id":"260","atype":"6","type":"种子管理","aname":"胡靖安","aphone":"13177885992","apic":"http://v426.nyt.bd.ej38.com/UpLoad/image/20170712/20170712151904_22933.jpg","ainfo":"宜丰县种子管理局，副局长，高级农艺师 1988.7 江西农业大学毕业 1988.8-2006.10 宜丰县种子公司 2006.11- 宜丰县种子管理（站）局","atime":"2017-06-07 10:30:48"},{"id":"259","atype":"24","type":"农村经济","aname":"郑发荣","aphone":"15970306578","apic":"http://v426.nyt.bd.ej38.com/UpLoad/image/20170523/20170523095224_18805.png","ainfo":"农业产业化、农业龙头企业申报、农业招商引资、农业示范园区建设等方面。","atime":"2017-05-18 16:27:01"},{"id":"258","atype":"14","type":"植保植检","aname":"赵险锋","aphone":"13177885178","apic":"http://v426.nyt.bd.ej38.com/UpLoad/image/20170518/20170518091423_27666.jpg","ainfo":"1998年至从事水稻病虫测报和植保技术推广工作","atime":"2017-05-18 06:28:30"},{"id":"257","atype":"14","type":"植保植检","aname":"丁清龙","aphone":"13177885637","apic":"http://v426.nyt.bd.ej38.com/UpLoad/image/20170522/20170522173556_99681.jpg","ainfo":"本人1985年毕业于江西农业大学植保系，从事植保检检工作30多年","atime":"2017-05-17 17:26:04"},{"id":"256","atype":"12","type":"粮油作物","aname":"曹九龙","aphone":"13970045798","apic":"http://v426.nyt.bd.ej38.com/UpLoad/image/20170516/20170516101205_73028.png","ainfo":"粮油作物栽培技术","atime":"2017-05-16 06:09:08"}]
     */

    private String result;
    private int totalpage;
    private List<DataBean> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 273
         * atype : 27
         * type : 气象专家
         * aname : 蔡哲
         * aphone : 13870643470
         * apic : http://v426.nyt.bd.ej38.com/UpLoad/image/20170926/20170926134536_65118.jpg
         * ainfo : 江西省农业气象中心高级工程师，主要从事农业气象服务工作。
         * atime : 2017-09-14 16:37:55
         */

        private String id;
        private String atype;
        private String type;
        private String aname;
        private String aphone;
        private String apic;
        private String ainfo;
        private String atime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAtype() {
            return atype;
        }

        public void setAtype(String atype) {
            this.atype = atype;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAname() {
            return aname;
        }

        public void setAname(String aname) {
            this.aname = aname;
        }

        public String getAphone() {
            return aphone;
        }

        public void setAphone(String aphone) {
            this.aphone = aphone;
        }

        public String getApic() {
            return apic;
        }

        public void setApic(String apic) {
            this.apic = apic;
        }

        public String getAinfo() {
            return ainfo;
        }

        public void setAinfo(String ainfo) {
            this.ainfo = ainfo;
        }

        public String getAtime() {
            return atime;
        }

        public void setAtime(String atime) {
            this.atime = atime;
        }
    }
}
