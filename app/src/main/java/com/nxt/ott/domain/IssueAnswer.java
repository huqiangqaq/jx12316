package com.nxt.ott.domain;

import java.util.List;

/**
 * Created by huqiang on 2017/4/7 13:29.
 */

public class IssueAnswer {

    /**
     * list : [{"total":0,"list":null,"pageSize":20,"pageIndex":0,"pagerSize":0,"id":1,"issueId":43,"parentId":null,"issueDate":"2017-03-14 17:25:23.0","issueText":"","issueImg":"","issueVoice":"","issueUser":"","issueNickName":"李良泉","answerDate":"2017-03-21 17:24:49.0","answerText":"1","answerImg":"","answerVoice":"","answerUser":"18179005316","answerNickName":"钟志华","isPush":"1","isShow":"y","addAndReply":"reply","type":"","likeCount":"2","good":"1","centre":"0","bad":"1","isGood":"0","isCentre":"0","isBad":"0","user":"","listAnswer":[{"total":0,"list":null,"pageSize":20,"pageIndex":0,"pagerSize":0,"id":2,"issueId":43,"parentId":1,"issueDate":"2017-03-02 17:25:27.0","issueText":"","issueImg":"","issueVoice":"","issueUser":"","issueNickName":"李良泉","answerDate":"2017-03-15 17:24:54.0","answerText":"2","answerImg":"","answerVoice":"","answerUser":"18179005316","answerNickName":"钟志华","isPush":"1","isShow":"y","addAndReply":"reply","type":"","likeCount":"","good":"","centre":"","bad":"","isGood":"","isCentre":"","isBad":"","user":"","listAnswer":null},{"total":0,"list":null,"pageSize":20,"pageIndex":0,"pagerSize":0,"id":4,"issueId":43,"parentId":1,"issueDate":"","issueText":"","issueImg":"","issueVoice":"","issueUser":"","issueNickName":"","answerDate":"","answerText":"","answerImg":"","answerVoice":"","answerUser":"","answerNickName":"汪新茂","isPush":"0","isShow":"y","addAndReply":"","type":"","likeCount":"","good":"","centre":"","bad":"","isGood":"","isCentre":"","isBad":"","user":"","listAnswer":null}]}]
     * code : 1
     * msg : 获取成功！
     */

    private String code;
    private String msg;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * total : 0
         * list : null
         * pageSize : 20
         * pageIndex : 0
         * pagerSize : 0
         * id : 1
         * issueId : 43
         * parentId : null
         * issueDate : 2017-03-14 17:25:23.0
         * issueText :
         * issueImg :
         * issueVoice :
         * issueUser :
         * issueNickName : 李良泉
         * answerDate : 2017-03-21 17:24:49.0
         * answerText : 1
         * answerImg :
         * answerVoice :
         * answerUser : 18179005316
         * answerNickName : 钟志华
         * isPush : 1
         * isShow : y
         * addAndReply : reply
         * type :
         * likeCount : 2
         * good : 1
         * centre : 0
         * bad : 1
         * isGood : 0
         * isCentre : 0
         * isBad : 0
         * user :
         * listAnswer : [{"total":0,"list":null,"pageSize":20,"pageIndex":0,"pagerSize":0,"id":2,"issueId":43,"parentId":1,"issueDate":"2017-03-02 17:25:27.0","issueText":"","issueImg":"","issueVoice":"","issueUser":"","issueNickName":"李良泉","answerDate":"2017-03-15 17:24:54.0","answerText":"2","answerImg":"","answerVoice":"","answerUser":"18179005316","answerNickName":"钟志华","isPush":"1","isShow":"y","addAndReply":"reply","type":"","likeCount":"","good":"","centre":"","bad":"","isGood":"","isCentre":"","isBad":"","user":"","listAnswer":null},{"total":0,"list":null,"pageSize":20,"pageIndex":0,"pagerSize":0,"id":4,"issueId":43,"parentId":1,"issueDate":"","issueText":"","issueImg":"","issueVoice":"","issueUser":"","issueNickName":"","answerDate":"","answerText":"","answerImg":"","answerVoice":"","answerUser":"","answerNickName":"汪新茂","isPush":"0","isShow":"y","addAndReply":"","type":"","likeCount":"","good":"","centre":"","bad":"","isGood":"","isCentre":"","isBad":"","user":"","listAnswer":null}]
         */

        private int total;
        private Object list;
        private int pageSize;
        private int pageIndex;
        private int pagerSize;
        private int id;
        private int issueId;
        private Object parentId;
        private String issueDate;
        private String issueText;
        private String issueImg;
        private String issueVoice;
        private String issueUser;
        private String issueNickName;
        private String answerDate;
        private String answerText;
        private String answerImg;
        private String answerVoice;
        private String answerUser;
        private String answerNickName;
        private String isPush;
        private String isShow;
        private String addAndReply;
        private String type;
        private String likeCount;
        private String good;
        private String centre;
        private String bad;
        private String isLike;
        private String isGood;
        private String isCentre;
        private String isBad;
        private String user;
        private String issueUserImg;
        private String answerUserImg;
        private List<ListAnswerBean> listAnswer;

        public String getIssueUserImg() {
            return issueUserImg;
        }

        public void setIssueUserImg(String issueUserImg) {
            this.issueUserImg = issueUserImg;
        }

        public String getAnswerUserImg() {
            return answerUserImg;
        }

        public void setAnswerUserImg(String answerUserImg) {
            this.answerUserImg = answerUserImg;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public Object getList() {
            return list;
        }

        public void setList(Object list) {
            this.list = list;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPagerSize() {
            return pagerSize;
        }

        public void setPagerSize(int pagerSize) {
            this.pagerSize = pagerSize;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIssueId() {
            return issueId;
        }

        public void setIssueId(int issueId) {
            this.issueId = issueId;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
        }

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        public String getIssueText() {
            return issueText;
        }

        public void setIssueText(String issueText) {
            this.issueText = issueText;
        }

        public String getIssueImg() {
            return issueImg;
        }

        public void setIssueImg(String issueImg) {
            this.issueImg = issueImg;
        }

        public String getIssueVoice() {
            return issueVoice;
        }

        public void setIssueVoice(String issueVoice) {
            this.issueVoice = issueVoice;
        }

        public String getIssueUser() {
            return issueUser;
        }

        public void setIssueUser(String issueUser) {
            this.issueUser = issueUser;
        }

        public String getIssueNickName() {
            return issueNickName;
        }

        public void setIssueNickName(String issueNickName) {
            this.issueNickName = issueNickName;
        }

        public String getAnswerDate() {
            return answerDate;
        }

        public void setAnswerDate(String answerDate) {
            this.answerDate = answerDate;
        }

        public String getAnswerText() {
            return answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }

        public String getAnswerImg() {
            return answerImg;
        }

        public void setAnswerImg(String answerImg) {
            this.answerImg = answerImg;
        }

        public String getAnswerVoice() {
            return answerVoice;
        }

        public void setAnswerVoice(String answerVoice) {
            this.answerVoice = answerVoice;
        }

        public String getAnswerUser() {
            return answerUser;
        }

        public void setAnswerUser(String answerUser) {
            this.answerUser = answerUser;
        }

        public String getAnswerNickName() {
            return answerNickName;
        }

        public void setAnswerNickName(String answerNickName) {
            this.answerNickName = answerNickName;
        }

        public String getIsPush() {
            return isPush;
        }

        public void setIsPush(String isPush) {
            this.isPush = isPush;
        }

        public String getIsShow() {
            return isShow;
        }

        public void setIsShow(String isShow) {
            this.isShow = isShow;
        }

        public String getAddAndReply() {
            return addAndReply;
        }

        public void setAddAndReply(String addAndReply) {
            this.addAndReply = addAndReply;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(String likeCount) {
            this.likeCount = likeCount;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getCentre() {
            return centre;
        }

        public void setCentre(String centre) {
            this.centre = centre;
        }

        public String getBad() {
            return bad;
        }

        public void setBad(String bad) {
            this.bad = bad;
        }

        public String getIsGood() {
            return isGood;
        }

        public void setIsGood(String isGood) {
            this.isGood = isGood;
        }

        public String getIsCentre() {
            return isCentre;
        }

        public void setIsCentre(String isCentre) {
            this.isCentre = isCentre;
        }

        public String getIsBad() {
            return isBad;
        }

        public void setIsBad(String isBad) {
            this.isBad = isBad;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }

        public List<ListAnswerBean> getListAnswer() {
            return listAnswer;
        }

        public void setListAnswer(List<ListAnswerBean> listAnswer) {
            this.listAnswer = listAnswer;
        }

        public static class ListAnswerBean {
            /**
             * total : 0
             * list : null
             * pageSize : 20
             * pageIndex : 0
             * pagerSize : 0
             * id : 2
             * issueId : 43
             * parentId : 1
             * issueDate : 2017-03-02 17:25:27.0
             * issueText :
             * issueImg :
             * issueVoice :
             * issueUser :
             * issueNickName : 李良泉
             * answerDate : 2017-03-15 17:24:54.0
             * answerText : 2
             * answerImg :
             * answerVoice :
             * answerUser : 18179005316
             * answerNickName : 钟志华
             * isPush : 1
             * isShow : y
             * addAndReply : reply
             * type :
             * likeCount :
             * good :
             * centre :
             * bad :
             * isGood :
             * isCentre :
             * isBad :
             * user :
             * listAnswer : null
             */

            private int total;
            private Object list;
            private int pageSize;
            private int pageIndex;
            private int pagerSize;
            private int id;
            private int issueId;
            private int parentId;
            private String issueDate;
            private String issueText;
            private String issueImg;
            private String issueVoice;
            private String issueUser;
            private String issueNickName;
            private String answerDate;
            private String answerText;
            private String answerImg;
            private String answerVoice;
            private String answerUser;
            private String answerNickName;
            private String isPush;
            private String isShow;
            private String addAndReply;
            private String type;
            private String likeCount;
            private String good;
            private String centre;
            private String bad;
            private String isGood;
            private String isCentre;
            private String isBad;
            private String user;
            private String issueUserImg;
            private String answerUserImg;
            private Object listAnswer;

            public String getIssueUserImg() {
                return issueUserImg;
            }

            public void setIssueUserImg(String issueUserImg) {
                this.issueUserImg = issueUserImg;
            }

            public String getAnswerUserImg() {
                return answerUserImg;
            }

            public void setAnswerUserImg(String answerUserImg) {
                this.answerUserImg = answerUserImg;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public Object getList() {
                return list;
            }

            public void setList(Object list) {
                this.list = list;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPageIndex() {
                return pageIndex;
            }

            public void setPageIndex(int pageIndex) {
                this.pageIndex = pageIndex;
            }

            public int getPagerSize() {
                return pagerSize;
            }

            public void setPagerSize(int pagerSize) {
                this.pagerSize = pagerSize;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIssueId() {
                return issueId;
            }

            public void setIssueId(int issueId) {
                this.issueId = issueId;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getIssueDate() {
                return issueDate;
            }

            public void setIssueDate(String issueDate) {
                this.issueDate = issueDate;
            }

            public String getIssueText() {
                return issueText;
            }

            public void setIssueText(String issueText) {
                this.issueText = issueText;
            }

            public String getIssueImg() {
                return issueImg;
            }

            public void setIssueImg(String issueImg) {
                this.issueImg = issueImg;
            }

            public String getIssueVoice() {
                return issueVoice;
            }

            public void setIssueVoice(String issueVoice) {
                this.issueVoice = issueVoice;
            }

            public String getIssueUser() {
                return issueUser;
            }

            public void setIssueUser(String issueUser) {
                this.issueUser = issueUser;
            }

            public String getIssueNickName() {
                return issueNickName;
            }

            public void setIssueNickName(String issueNickName) {
                this.issueNickName = issueNickName;
            }

            public String getAnswerDate() {
                return answerDate;
            }

            public void setAnswerDate(String answerDate) {
                this.answerDate = answerDate;
            }

            public String getAnswerText() {
                return answerText;
            }

            public void setAnswerText(String answerText) {
                this.answerText = answerText;
            }

            public String getAnswerImg() {
                return answerImg;
            }

            public void setAnswerImg(String answerImg) {
                this.answerImg = answerImg;
            }

            public String getAnswerVoice() {
                return answerVoice;
            }

            public void setAnswerVoice(String answerVoice) {
                this.answerVoice = answerVoice;
            }

            public String getAnswerUser() {
                return answerUser;
            }

            public void setAnswerUser(String answerUser) {
                this.answerUser = answerUser;
            }

            public String getAnswerNickName() {
                return answerNickName;
            }

            public void setAnswerNickName(String answerNickName) {
                this.answerNickName = answerNickName;
            }

            public String getIsPush() {
                return isPush;
            }

            public void setIsPush(String isPush) {
                this.isPush = isPush;
            }

            public String getIsShow() {
                return isShow;
            }

            public void setIsShow(String isShow) {
                this.isShow = isShow;
            }

            public String getAddAndReply() {
                return addAndReply;
            }

            public void setAddAndReply(String addAndReply) {
                this.addAndReply = addAndReply;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(String likeCount) {
                this.likeCount = likeCount;
            }

            public String getGood() {
                return good;
            }

            public void setGood(String good) {
                this.good = good;
            }

            public String getCentre() {
                return centre;
            }

            public void setCentre(String centre) {
                this.centre = centre;
            }

            public String getBad() {
                return bad;
            }

            public void setBad(String bad) {
                this.bad = bad;
            }

            public String getIsGood() {
                return isGood;
            }

            public void setIsGood(String isGood) {
                this.isGood = isGood;
            }

            public String getIsCentre() {
                return isCentre;
            }

            public void setIsCentre(String isCentre) {
                this.isCentre = isCentre;
            }

            public String getIsBad() {
                return isBad;
            }

            public void setIsBad(String isBad) {
                this.isBad = isBad;
            }

            public String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }

            public Object getListAnswer() {
                return listAnswer;
            }

            public void setListAnswer(Object listAnswer) {
                this.listAnswer = listAnswer;
            }
        }
    }
}
