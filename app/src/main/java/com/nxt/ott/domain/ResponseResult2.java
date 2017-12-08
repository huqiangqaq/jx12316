package com.nxt.ott.domain;

import java.io.Serializable;

/**
 * @author koneloong koneloong@gmail.com
 *         Created on 2015/2/11 8:11.
 */
public class ResponseResult2<T> implements Serializable {
    private static final long serialVersionUID = 6615965546258483054L;
    private int resultCode;
    private T resultInfo;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(T resultInfo) {
        this.resultInfo = resultInfo;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "resultCode=" + resultCode +
                ", resultInfo=" + resultInfo +
                '}';
    }
}
