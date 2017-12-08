package com.nxt.ott.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xpeng on 2016/10/10.
 */

public class Pest implements Serializable{

    /**
     * Rows : [{"Row":1,"DispestMapid":7,"MapNo":"333344","Title":"333344","Content":"&lt;p&gt;333333&lt;br/&gt;&lt;/p&gt;","ImgUrl":"~/NJB/upload/20160922/201609220824256049.jpg","Dtypeid":75,"Dtypename":"??????31","ModifyDate":"2016-10-10","ModifyUserId":0,"ModifyUserName":null,"CreateUserName":null,"CreateUserId":0,"CreateDate":"2016-10-10","Isenable":true,"Isdelete":false,"ImgUrl2":"http://jx.12316.zq.yn15.com/NJB/upload/20160922/201609220824256049.jpg"},{"Row":2,"DispestMapid":6,"MapNo":"333344","Title":"333344","Content":"&lt;p&gt;333333&lt;br /&gt;&lt;/p&gt;","ImgUrl":"~/NJB/upload/20160922/201609220824256049.jpg","Dtypeid":75,"Dtypename":"虫害1","ModifyDate":"2016-10-10","ModifyUserId":0,"ModifyUserName":null,"CreateUserName":null,"CreateUserId":0,"CreateDate":"2016-10-10","Isenable":true,"Isdelete":false,"ImgUrl2":"http://jx.12316.zq.yn15.com/NJB/upload/20160922/201609220824256049.jpg"},{"Row":3,"DispestMapid":5,"MapNo":"333344","Title":"333344","Content":"&lt;p&gt;333333&lt;br /&gt;&lt;/p&gt;","ImgUrl":"~/NJB/upload/20160922/201609220824256049.jpg","Dtypeid":75,"Dtypename":"虫害1","ModifyDate":"2016-10-10","ModifyUserId":0,"ModifyUserName":null,"CreateUserName":null,"CreateUserId":0,"CreateDate":"2016-10-10","Isenable":true,"Isdelete":false,"ImgUrl2":"http://jx.12316.zq.yn15.com/NJB/upload/20160922/201609220824256049.jpg"},{"Row":4,"DispestMapid":4,"MapNo":"33334455","Title":"3333644","Content":"&lt;p&gt;333333&lt;br /&gt;&lt;/p&gt;","ImgUrl":"~/NJB/upload/20160922/201609220824256049.jpg","Dtypeid":75,"Dtypename":"虫害1","ModifyDate":"2016-10-10","ModifyUserId":0,"ModifyUserName":null,"CreateUserName":null,"CreateUserId":0,"CreateDate":"2016-10-10","Isenable":true,"Isdelete":false,"ImgUrl2":"http://jx.12316.zq.yn15.com/NJB/upload/20160922/201609220824256049.jpg"},{"Row":5,"DispestMapid":3,"MapNo":"11111111","Title":"11111111111","Content":"&lt;p&gt;dfasdasdsadfasfdsafsdafds&lt;br /&gt;&lt;/p&gt;","ImgUrl":"~/NJB/upload/20161010/201610100211247577.png","Dtypeid":74,"Dtypename":"病害1","ModifyDate":"2016-10-10","ModifyUserId":0,"ModifyUserName":null,"CreateUserName":null,"CreateUserId":0,"CreateDate":"2016-10-10","Isenable":true,"Isdelete":false,"ImgUrl2":"http://jx.12316.zq.yn15.com/NJB/upload/20161010/201610100211247577.png"},{"Row":6,"DispestMapid":2,"MapNo":"333344","Title":"333344","Content":"&lt;p&gt;333333&lt;br/&gt;&lt;/p&gt;","ImgUrl":"~/NJB/upload/20160922/201609220824256049.jpg","Dtypeid":75,"Dtypename":"??????31","ModifyDate":"2016-10-10","ModifyUserId":0,"ModifyUserName":null,"CreateUserName":null,"CreateUserId":0,"CreateDate":"2016-09-22","Isenable":true,"Isdelete":false,"ImgUrl2":"http://jx.12316.zq.yn15.com/NJB/upload/20160922/201609220824256049.jpg"},{"Row":7,"DispestMapid":1,"MapNo":"eeee","Title":"bbbbbb","Content":"&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;h1 label=&quot;Title center&quot; name=&quot;tc&quot; style=&quot;border-bottom-color:#cccccc;border-bottom-width:2px;border-bottom-style:solid;padding:0px 4px 0px 0px;text-align:center;margin:0px 0px 20px;&quot; class=&quot;ue_t&quot;&gt;[此处键入文章标题]&lt;/h1&gt;&lt;p&gt;&lt;img src=&quot;http://img.baidu.com/hi/youa/y_0034.gif&quot; style=&quot;width:300px;height:200px;float:left;&quot; border=&quot;0&quot; height=&quot;200&quot; hspace=&quot;0&quot; vspace=&quot;0&quot; width=&quot;300&quot; /&gt;图文混排方法&lt;/p&gt;&lt;p&gt;1. 图片居左，文字围绕图片排版&lt;/p&gt;&lt;p&gt;方法：在文字前面插入图片，设置居左对齐，然后即可在右边输入多行文本&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;2. 图片居右，文字围绕图片排版&lt;/p&gt;&lt;p&gt;方法：在文字前面插入图片，设置居右对齐，然后即可在左边输入多行文本&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;3. 图片居中环绕排版&lt;/p&gt;&lt;p&gt;方法：亲，这个真心没有办法。。。&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;http://img.baidu.com/hi/youa/y_0040.gif&quot; style=&quot;width:300px;height:300px;float:right;&quot; border=&quot;0&quot; height=&quot;300&quot; hspace=&quot;0&quot; vspace=&quot;0&quot; width=&quot;300&quot; /&gt;&lt;/p&gt;&lt;p&gt;还有没有什么其他的环绕方式呢？这里是居右环绕&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;欢迎大家多多尝试，为UEditor提供更多高质量模板！&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;占位&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;占位&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;占位&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;占位&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;占位&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;","ImgUrl":"~/NJB/upload/20160922/201609221010383692.jpg","Dtypeid":75,"Dtypename":"虫害1","ModifyDate":"2016-09-22","ModifyUserId":0,"ModifyUserName":null,"CreateUserName":null,"CreateUserId":0,"CreateDate":"2016-09-21","Isenable":true,"Isdelete":false,"ImgUrl2":"http://jx.12316.zq.yn15.com/NJB/upload/20160922/201609221010383692.jpg"}]
     * Total : 7
     */

    private String Total;
    /**
     * Row : 1
     * DispestMapid : 7
     * MapNo : 333344
     * Title : 333344
     * Content : &lt;p&gt;333333&lt;br/&gt;&lt;/p&gt;
     * ImgUrl : ~/NJB/upload/20160922/201609220824256049.jpg
     * Dtypeid : 75
     * Dtypename : ??????31
     * ModifyDate : 2016-10-10
     * ModifyUserId : 0
     * ModifyUserName : null
     * CreateUserName : null
     * CreateUserId : 0
     * CreateDate : 2016-10-10
     * Isenable : true
     * Isdelete : false
     * ImgUrl2 : http://jx.12316.zq.yn15.com/NJB/upload/20160922/201609220824256049.jpg
     */

    private List<RowsBean> Rows;

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }

    public List<RowsBean> getRows() {
        return Rows;
    }

    public void setRows(List<RowsBean> Rows) {
        this.Rows = Rows;
    }

    public static class RowsBean {
        private int Row;
        private int DispestMapid;
        private String MapNo;
        private String Title;
        private String Content;
        private String ImgUrl;
        private int Dtypeid;
        private String Dtypename;
        private String ModifyDate;
        private int ModifyUserId;
        private Object ModifyUserName;
        private Object CreateUserName;
        private int CreateUserId;
        private String CreateDate;
        private boolean Isenable;
        private boolean Isdelete;
        private String ImgUrl2;

        public int getRow() {
            return Row;
        }

        public void setRow(int Row) {
            this.Row = Row;
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

        public void setModifyUserName(Object ModifyUserName) {
            this.ModifyUserName = ModifyUserName;
        }

        public Object getCreateUserName() {
            return CreateUserName;
        }

        public void setCreateUserName(Object CreateUserName) {
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

        public String getImgUrl2() {
            return ImgUrl2;
        }

        public void setImgUrl2(String ImgUrl2) {
            this.ImgUrl2 = ImgUrl2;
        }
    }
}
