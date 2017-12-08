package com.nxt.ott.domain;

/**
 * Created by huqiang on 2017/4/18 9:54.
 */

public class ExpertInfo {

    /**
     * code : 1
     * msg : 获取成功！
     * expert : {"account":"","img":"","nickname":""}
     */

    private String code;
    private String msg;
    private ExpertBean expert;

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

    public ExpertBean getExpert() {
        return expert;
    }

    public void setExpert(ExpertBean expert) {
        this.expert = expert;
    }

    public static class ExpertBean {
        /**
         * account :
         * img :
         * nickname :
         */

        private String account;
        private String img;
        private String nickname;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
