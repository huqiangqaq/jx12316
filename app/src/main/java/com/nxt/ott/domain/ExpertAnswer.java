package com.nxt.ott.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huqiang on 2016/11/3 14:25.
 */

public class ExpertAnswer {

    /**
     * Rows : [{"Row":1,"id":1,"equestion":"hhhhhhhh","eanswer":"yy","addtime":"2016-11-09","hotcount":17,"CreateUser":null,"CreateDate":"2016-11-02","isdelete":0},{"Row":2,"id":2,"equestion":"111","eanswer":"22222","addtime":"2016-11-03","hotcount":4,"CreateUser":null,"CreateDate":"2016-11-02","isdelete":0}]
     * Total : 2
     */

    private String Total;
    /**
     * Row : 1
     * id : 1
     * equestion : hhhhhhhh
     * eanswer : yy
     * addtime : 2016-11-09
     * hotcount : 17
     * CreateUser : null
     * CreateDate : 2016-11-02
     * isdelete : 0
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

    public static class RowsBean implements Serializable {
        private int Row;
        private int id;
        private String equestion;
        private String eanswer;
        private String addtime;
        private int hotcount;
        private Object CreateUser;
        private String CreateDate;
        private int isdelete;

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

        public String getEquestion() {
            return equestion;
        }

        public void setEquestion(String equestion) {
            this.equestion = equestion;
        }

        public String getEanswer() {
            return eanswer;
        }

        public void setEanswer(String eanswer) {
            this.eanswer = eanswer;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getHotcount() {
            return hotcount;
        }

        public void setHotcount(int hotcount) {
            this.hotcount = hotcount;
        }

        public Object getCreateUser() {
            return CreateUser;
        }

        public void setCreateUser(Object CreateUser) {
            this.CreateUser = CreateUser;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public int getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(int isdelete) {
            this.isdelete = isdelete;
        }
    }
}
