package com.nxt.ott.domain;

import java.util.List;

/**
 * Created by huqiang on 2017/3/21 14:06.
 */

public class Answer {

    /**
     * result : ok
     * totalpage : 48
     * data : [{"htime":"2017-12-06 11:22:17","id":"819","pid":"126","tbiaoqian":"2","tname":"河虾养殖","biaoqian":"水产类","htype":"专家已回复","hinfo":"您好！很高兴回答你的问题，山脚下泉水养河虾，首先要知道水质是否适合，水质要符合淡水鱼养殖水质条件，在夏季水温不宜过低，否则影响河虾生长。其次要考虑河虾苗的供应情况，在九江没有专业生产河虾苗的地方，只有沿鄱阳湖地区捕捞少量河虾苗，并且运输不方便，成活率往往有影响。综合上述原因，建议你在离湖区较远的山区不宜用山泉水养河虾。谢谢。"},{"htime":"2017-12-06 10:30:53","id":"818","pid":"20","tbiaoqian":"17","tname":"粮食取消保护价会实行吗？","biaoqian":"粮油类","htype":"专家已回复","hinfo":"您好，感谢您对12316的支持！你咨询的政策目前还没出台，一切以出台政策为准，建议您关注江西农业信息网和江西农业公众微信号，获取第一时间的政策信息。"},{"htime":"2017-12-06 11:43:45","id":"816","pid":"39","tbiaoqian":"17","tname":"病虫","biaoqian":"粮油类","htype":"专家已回复","hinfo":"您好！感谢您对12316的信任和支持！2017年我省水稻病虫属中等发生年份，主要以两迁害虫、螟虫、纹枯病、部分地区的稻瘟病为主。根据11月份螟虫冬前基数剥查结果，二化螟冬前基数仍然很高。预计2018年水稻病虫害呈偏重发生态势，重点关注螟虫、稻飞虱、稻纵卷叶螟、稻瘟病和纹枯病，直播稻区要切实加强抗性杂草的防控，坚持做好杂草防治的一封二杀三补技术。具体的水稻病虫草害防治措施请您及时关注当地植保部门的病虫情报。"},{"htime":"2017-12-06 10:31:14","id":"815","pid":"20","tbiaoqian":"17","tname":"粮食","biaoqian":"粮油类","htype":"专家已回复","hinfo":"您好，感谢您对12316的支持！你咨询的政策目前还没出台，一切以出台政策为准，建议您关注江西农业信息网和江西农业公众微信号，获取第一时间的政策信息。"},{"htime":"2017-12-06 11:25:36","id":"814","pid":"272","tbiaoqian":"23","tname":"天气","biaoqian":"其他","htype":"专家已回复","hinfo":"预计，2017/2018年冬季全省平均气温略偏高，降水量偏少。季内、月内冷暖变化幅度较大，部分地区可能出现雨雪冰冻天气，但全省出现大范围、持续性低温雨雪冰冻灾害的可能性较小。 气温：预计，2017/2018年冬季全省平均气温略偏高，为8～9℃，其中赣北偏高为8～9℃，赣中略偏高为7.5～8.5℃，赣南正常到略偏高为9.0～10.0℃。季内气温12月、1月、2月均略偏高。（仅供参考）"},{"htime":"2017-12-06 09:42:12","id":"811","pid":"253","tbiaoqian":"16","tname":"生猪呕吐","biaoqian":"畜牧类","htype":"专家已回复","hinfo":"生猪呕吐一般有三类病因:1、传染性胃肠炎或流行性腹泻，呕吐的同时伴有水样泻。治疗采取饮水加口服补液盐、杨树花素、粘杆菌素等，拌料加中药类止泻药、木炭，注意控制采食量，加强防寒保暖，在流行季节前做好1-2次疫苗免疫，控制发病场的猪、车、人员的流入。2、采食了变质、腐败、霉变、不易消化的饲料或食物，饲料粉碎细度过细造成胃溃疡等。防治方法:喂服新鲜、优质、易消化的饲料，调整饲料粉碎细度。3、仔猪伪狂犬病也会出现呕吐，防治方法是:做好母猪和仔猪的伪狂犬疫苗免疫。"},{"htime":"","id":"809","pid":"270","tbiaoqian":"18","tname":"茭白种植技术","biaoqian":"经济作物类","htype":"","hinfo":""},{"htime":"2017-12-04 09:58:04","id":"808","pid":"258","tbiaoqian":"18","tname":"除草剂","biaoqian":"经济作物类","htype":"专家已回复","hinfo":"您好，首先感谢您对江西12316的关注和支持！从您发的图片，我认定杂草为小酸浆，属茄科，为一年生杂草。"},{"htime":"2017-12-04 16:57:03","id":"807","pid":"256","tbiaoqian":"17","tname":"杀茵剂","biaoqian":"粮油类","htype":"专家已回复","hinfo":"属广谱杀菌剂，建议使用菌核净或腐霉利防治菌核病效果更好。"},{"htime":"2017-12-04 09:29:28","id":"805","pid":"258","tbiaoqian":"18","tname":"有什么特效除草剂杀死这几种草吗","biaoqian":"经济作物类","htype":"专家已回复","hinfo":"这几种杂草对我们常用的草甘膦产生了抗药性。改用草胺膦兑水喷雾，配药时加有机硅或其他增效剂，效果更明显。"}]
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
         * htime : 2017-12-06 11:22:17
         * id : 819
         * pid : 126
         * tbiaoqian : 2
         * tname : 河虾养殖
         * biaoqian : 水产类
         * htype : 专家已回复
         * hinfo : 您好！很高兴回答你的问题，山脚下泉水养河虾，首先要知道水质是否适合，水质要符合淡水鱼养殖水质条件，在夏季水温不宜过低，否则影响河虾生长。其次要考虑河虾苗的供应情况，在九江没有专业生产河虾苗的地方，只有沿鄱阳湖地区捕捞少量河虾苗，并且运输不方便，成活率往往有影响。综合上述原因，建议你在离湖区较远的山区不宜用山泉水养河虾。谢谢。
         */

        private String htime;
        private String id;
        private String pid;
        private String tbiaoqian;
        private String tname;
        private String biaoqian;
        private String htype;
        private String hinfo;

        public String getHtime() {
            return htime;
        }

        public void setHtime(String htime) {
            this.htime = htime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getTbiaoqian() {
            return tbiaoqian;
        }

        public void setTbiaoqian(String tbiaoqian) {
            this.tbiaoqian = tbiaoqian;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getBiaoqian() {
            return biaoqian;
        }

        public void setBiaoqian(String biaoqian) {
            this.biaoqian = biaoqian;
        }

        public String getHtype() {
            return htype;
        }

        public void setHtype(String htype) {
            this.htype = htype;
        }

        public String getHinfo() {
            return hinfo;
        }

        public void setHinfo(String hinfo) {
            this.hinfo = hinfo;
        }
    }
}
