package com.nxt.ott.domain;

import java.util.List;

/**
 * Created by huqiang on 2017/2/23 16:52.
 */

public class HomeImage {

    private String Total;
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
        /**
         * Row : 1
         * id : 12
         * ImgeUrl : ~/upload/20170223/201702230314257587.png
         * ImageMark : &lt;p&gt;&lt;img src=&quot;../../../ueditor/net/upload/2017-02-23/19007eb9-77f4-4cb9-9d0f-796562d285b0.jpg&quot; title=&quot;cap_one.jpg&quot; /&gt;&lt;/p&gt;
         * ImageOrder : 1000
         * isDelete : 0
         * Create_id : 0
         * Create_name : null
         * Create_date : 2017-02-23
         * ImgeHead : 12316APP正式上线
         * imghttpurl : http://jx.12316.zq.yn15.com/upload/20170223/201702230314257587.png
         */

        private int Row;
        private int id;
        private String ImgeUrl;
        private String ImageMark;
        private int ImageOrder;
        private int isDelete;
        private int Create_id;
        private Object Create_name;
        private String Create_date;
        private String ImgeHead;
        private String imghttpurl;

        public int getRow() {
            return Row;
        }

        public void setRow(int Row) {
            this.Row = Row;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgeUrl() {
            return ImgeUrl;
        }

        public void setImgeUrl(String ImgeUrl) {
            this.ImgeUrl = ImgeUrl;
        }

        public String getImageMark() {
            return ImageMark;
        }

        public void setImageMark(String ImageMark) {
            this.ImageMark = ImageMark;
        }

        public int getImageOrder() {
            return ImageOrder;
        }

        public void setImageOrder(int ImageOrder) {
            this.ImageOrder = ImageOrder;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getCreate_id() {
            return Create_id;
        }

        public void setCreate_id(int Create_id) {
            this.Create_id = Create_id;
        }

        public Object getCreate_name() {
            return Create_name;
        }

        public void setCreate_name(Object Create_name) {
            this.Create_name = Create_name;
        }

        public String getCreate_date() {
            return Create_date;
        }

        public void setCreate_date(String Create_date) {
            this.Create_date = Create_date;
        }

        public String getImgeHead() {
            return ImgeHead;
        }

        public void setImgeHead(String ImgeHead) {
            this.ImgeHead = ImgeHead;
        }

        public String getImghttpurl() {
            return imghttpurl;
        }

        public void setImghttpurl(String imghttpurl) {
            this.imghttpurl = imghttpurl;
        }
    }
}
