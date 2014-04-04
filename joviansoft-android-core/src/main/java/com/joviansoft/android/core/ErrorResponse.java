package com.joviansoft.android.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 返回的错误对象
 * Created by bigbao on 14-3-20.
 */
public class ErrorResponse extends JovianResponse {
    @JsonProperty("error_code")
    private int errorCode;
    @JsonProperty("error_msg")
    private String errorMsg;
    @JsonProperty("sub_msg")
    private String subMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }
}
