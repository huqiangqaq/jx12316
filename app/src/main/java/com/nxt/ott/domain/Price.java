package com.nxt.ott.domain;

import java.util.List;

/**
 * Created by huqiang on 2017/1/5 13:14.
 */

public class Price {

    /**
     * Rows : [{"Row":1,"id":849,"Agrivarity":"111","guige":"222","coUnit":"2","farmerPrice":222,"farmerHBAdd":566,"farmerTBAdd":55,"MarketPrice":333,"MarketHBAdd":55,"marketTBAdd":444,"isDelete":0,"Delete_time":"2017-01-06","CreateDate":"2017-01-06","reportMonth2":"2016年1月上","reportMonth":1,"reportYear":"2016","reportType":"1"},{"Row":2,"id":846,"Agrivarity":"玉米面","guige":"中等","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":0,"MarketHBAdd":0,"marketTBAdd":2.73,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":3,"id":845,"Agrivarity":"精粉","guige":"中等","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":0.21,"MarketHBAdd":0,"marketTBAdd":1.12,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":4,"id":844,"Agrivarity":"标准粉","guige":"中等","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":0,"MarketHBAdd":0,"marketTBAdd":4.9,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":5,"id":843,"Agrivarity":"中晚籼米","guige":"中等","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":0,"MarketHBAdd":0,"marketTBAdd":-0.5,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":6,"id":842,"Agrivarity":"早籼米","guige":"中等","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":0,"MarketHBAdd":0,"marketTBAdd":-1.42,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":7,"id":841,"Agrivarity":"大豆","guige":"中等","coUnit":"元/50公斤","farmerPrice":347.18,"farmerHBAdd":0,"farmerTBAdd":1,"MarketPrice":0,"MarketHBAdd":0,"marketTBAdd":2.87,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":8,"id":840,"Agrivarity":"玉米","guige":"中等","coUnit":"元/50公斤","farmerPrice":131,"farmerHBAdd":-4.84,"farmerTBAdd":-8.82,"MarketPrice":-0.66,"MarketHBAdd":0,"marketTBAdd":-4.21,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":9,"id":839,"Agrivarity":"晚籼稻","guige":"中等","coUnit":"元/50公斤","farmerPrice":130.26,"farmerHBAdd":0.2,"farmerTBAdd":-3.13,"MarketPrice":0.4,"MarketHBAdd":0,"marketTBAdd":-3.28,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":10,"id":838,"Agrivarity":"中籼稻","guige":"中等","coUnit":"元/50公斤","farmerPrice":129.74,"farmerHBAdd":-0.04,"farmerTBAdd":-2.99,"MarketPrice":0.2,"MarketHBAdd":0,"marketTBAdd":-3.29,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":11,"id":837,"Agrivarity":"早籼稻","guige":"中等","coUnit":"元/50公斤","farmerPrice":125.68,"farmerHBAdd":-0.42,"farmerTBAdd":-2.77,"MarketPrice":-0.28,"MarketHBAdd":0,"marketTBAdd":-2.8,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"0"},{"Row":12,"id":836,"Agrivarity":"带鱼","guige":"中等","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":-0.19,"MarketHBAdd":0,"marketTBAdd":0.36,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":13,"id":835,"Agrivarity":"草鱼","guige":"中等","coUnit":"元/公斤","farmerPrice":12.2,"farmerHBAdd":-0.77,"farmerTBAdd":5.7,"MarketPrice":-2.05,"MarketHBAdd":0,"marketTBAdd":4.9,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":14,"id":834,"Agrivarity":"鲢鱼","guige":"中等","coUnit":"元/公斤","farmerPrice":8.13,"farmerHBAdd":2.16,"farmerTBAdd":17.13,"MarketPrice":0.62,"MarketHBAdd":0,"marketTBAdd":10.37,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":15,"id":833,"Agrivarity":"鲤鱼","guige":"中等","coUnit":"元/公斤","farmerPrice":10.27,"farmerHBAdd":-0.7,"farmerTBAdd":2.72,"MarketPrice":0.23,"MarketHBAdd":0,"marketTBAdd":3.59,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":16,"id":832,"Agrivarity":"原料奶","guige":"中等","coUnit":"元/公斤","farmerPrice":5.7,"farmerHBAdd":0,"farmerTBAdd":0.88,"MarketPrice":0,"MarketHBAdd":0,"marketTBAdd":0,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":17,"id":831,"Agrivarity":"鸡蛋","guige":"中等  鲜蛋","coUnit":"元/公斤","farmerPrice":10.7,"farmerHBAdd":-0.51,"farmerTBAdd":-2.35,"MarketPrice":0.41,"MarketHBAdd":0,"marketTBAdd":-3.86,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":18,"id":830,"Agrivarity":"鸡肉","guige":"白条鸡","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":1.17,"MarketHBAdd":0,"marketTBAdd":2.99,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":19,"id":829,"Agrivarity":"羊肉","guige":"未切割细分","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":0.32,"MarketHBAdd":0,"marketTBAdd":-4.19,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":20,"id":828,"Agrivarity":"牛肉","guige":"未切割细分","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":0.65,"MarketHBAdd":0,"marketTBAdd":-2.25,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":21,"id":827,"Agrivarity":"猪肉","guige":"未切割细分","coUnit":"元/公斤","farmerPrice":0,"farmerHBAdd":0,"farmerTBAdd":0,"MarketPrice":-0.09,"MarketHBAdd":0,"marketTBAdd":9.53,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":22,"id":826,"Agrivarity":"仔猪","guige":"15公斤左右","coUnit":"元/公斤","farmerPrice":45.82,"farmerHBAdd":-0.43,"farmerTBAdd":26.91,"MarketPrice":-0.73,"MarketHBAdd":0,"marketTBAdd":30.43,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":23,"id":825,"Agrivarity":"生猪","guige":"100公斤左右","coUnit":"元/公斤","farmerPrice":18.24,"farmerHBAdd":-1.07,"farmerTBAdd":5.19,"MarketPrice":-1.21,"MarketHBAdd":0,"marketTBAdd":4.44,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"2"},{"Row":24,"id":824,"Agrivarity":"土豆","guige":"中等鲜菜","coUnit":"元/公斤","farmerPrice":3.39,"farmerHBAdd":-2.57,"farmerTBAdd":-6.94,"MarketPrice":0.61,"MarketHBAdd":0,"marketTBAdd":-7.46,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"1"},{"Row":25,"id":823,"Agrivarity":"菜椒","guige":"中等鲜菜","coUnit":"元/公斤","farmerPrice":5.03,"farmerHBAdd":6.7,"farmerTBAdd":6.7,"MarketPrice":3.1,"MarketHBAdd":0,"marketTBAdd":4.66,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"1"},{"Row":26,"id":822,"Agrivarity":"黄瓜","guige":"中等鲜菜","coUnit":"元/公斤","farmerPrice":3.45,"farmerHBAdd":0.54,"farmerTBAdd":-5.07,"MarketPrice":0.65,"MarketHBAdd":0,"marketTBAdd":-3.77,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"1"},{"Row":27,"id":821,"Agrivarity":"西红柿","guige":"中等鲜菜","coUnit":"元/公斤","farmerPrice":4.88,"farmerHBAdd":7.92,"farmerTBAdd":-0.11,"MarketPrice":6.09,"MarketHBAdd":0,"marketTBAdd":2.05,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"1"},{"Row":28,"id":820,"Agrivarity":"白菜","guige":"中等鲜菜","coUnit":"元/公斤","farmerPrice":3.58,"farmerHBAdd":-4.23,"farmerTBAdd":5.33,"MarketPrice":-2.49,"MarketHBAdd":0,"marketTBAdd":2.01,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"1"},{"Row":29,"id":819,"Agrivarity":"桑蚕茧","guige":"中准级","coUnit":"元/公斤","farmerPrice":35,"farmerHBAdd":0,"farmerTBAdd":2.94,"MarketPrice":0,"MarketHBAdd":0,"marketTBAdd":1.35,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"1"},{"Row":30,"id":818,"Agrivarity":"籽棉","guige":"36%衣分，10%回潮率","coUnit":"元/公斤","farmerPrice":5.95,"farmerHBAdd":0,"farmerTBAdd":-4.03,"MarketPrice":0,"MarketHBAdd":0,"marketTBAdd":-4.62,"isDelete":0,"Delete_time":"2017-01-03","CreateDate":"2017-01-03","reportMonth2":"2016年9月下旬","reportMonth":18,"reportYear":"2016","reportType":"1"}]
     * Total : 847
     */

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
         * id : 849
         * Agrivarity : 111
         * guige : 222
         * coUnit : 2
         * farmerPrice : 222
         * farmerHBAdd : 566
         * farmerTBAdd : 55
         * MarketPrice : 333
         * MarketHBAdd : 55
         * marketTBAdd : 444
         * isDelete : 0
         * Delete_time : 2017-01-06
         * CreateDate : 2017-01-06
         * reportMonth2 : 2016年1月上
         * reportMonth : 1
         * reportYear : 2016
         * reportType : 1
         */

        private int Row;
        private int id;
        private String Agrivarity;
        private String guige;
        private String coUnit;
        private double farmerPrice;
        private double farmerHBAdd;
        private double farmerTBAdd;
        private double MarketPrice;
        private double MarketHBAdd;
        private double marketTBAdd;
        private double isDelete;
        private String Delete_time;
        private String CreateDate;
        private String reportMonth2;
        private int reportMonth;
        private String reportYear;
        private String reportType;

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

        public String getAgrivarity() {
            return Agrivarity;
        }

        public void setAgrivarity(String Agrivarity) {
            this.Agrivarity = Agrivarity;
        }

        public String getGuige() {
            return guige;
        }

        public void setGuige(String guige) {
            this.guige = guige;
        }

        public String getCoUnit() {
            return coUnit;
        }

        public void setCoUnit(String coUnit) {
            this.coUnit = coUnit;
        }

        public double getFarmerPrice() {
            return farmerPrice;
        }

        public void setFarmerPrice(double farmerPrice) {
            this.farmerPrice = farmerPrice;
        }

        public double getFarmerHBAdd() {
            return farmerHBAdd;
        }

        public void setFarmerHBAdd(double farmerHBAdd) {
            this.farmerHBAdd = farmerHBAdd;
        }

        public double getFarmerTBAdd() {
            return farmerTBAdd;
        }

        public void setFarmerTBAdd(double farmerTBAdd) {
            this.farmerTBAdd = farmerTBAdd;
        }

        public double getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(double MarketPrice) {
            this.MarketPrice = MarketPrice;
        }

        public double getMarketHBAdd() {
            return MarketHBAdd;
        }

        public void setMarketHBAdd(double MarketHBAdd) {
            this.MarketHBAdd = MarketHBAdd;
        }

        public double getMarketTBAdd() {
            return marketTBAdd;
        }

        public void setMarketTBAdd(double marketTBAdd) {
            this.marketTBAdd = marketTBAdd;
        }

        public double getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(double isDelete) {
            this.isDelete = isDelete;
        }

        public String getDelete_time() {
            return Delete_time;
        }

        public void setDelete_time(String Delete_time) {
            this.Delete_time = Delete_time;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getReportMonth2() {
            return reportMonth2;
        }

        public void setReportMonth2(String reportMonth2) {
            this.reportMonth2 = reportMonth2;
        }

        public int getReportMonth() {
            return reportMonth;
        }

        public void setReportMonth(int reportMonth) {
            this.reportMonth = reportMonth;
        }

        public String getReportYear() {
            return reportYear;
        }

        public void setReportYear(String reportYear) {
            this.reportYear = reportYear;
        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }
    }
}
