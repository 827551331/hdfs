package com.hd.hdfs.dto;

import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @param <T>
 * @author wang_yw
 */
public class ResponseDataDTO<T> implements Serializable {

    public ResponseDataDTO(String rtnCode, String rtnMsg) {
        this.rtnCode = rtnCode;
        this.rtnMsg = rtnMsg;
    }

    public ResponseDataDTO(String rtnCode, T rtnData) {
        this.rtnCode = rtnCode;
        this.rtnMsg = rtnMsg;
        this.rtnData = rtnData;
    }

    public ResponseDataDTO(String rtnCode, String rtnMsg, T rtnData) {
        this.rtnCode = rtnCode;
        this.rtnMsg = rtnMsg;
        this.rtnData = rtnData;
    }

    /**
     * 返回码
     */
    private String rtnCode;

    /**
     * 返回信息
     */
    private String rtnMsg;

    /**
     * 返回信息
     */
    private T rtnData;

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public T getRtnData() {
        return rtnData;
    }

    public void setRtnData(T rtnData) {
        this.rtnData = rtnData;
    }
}
