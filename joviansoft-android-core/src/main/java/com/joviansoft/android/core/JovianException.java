package com.joviansoft.android.core;

/**
 * Created by bigbao on 14-3-12.
 */
public class JovianException extends Exception {
    private int errorCode;
    private String errorMsg;
    private String subMsg;

    public JovianException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public JovianException(int errorCode, String errorMsg, String subMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.subMsg = subMsg;
    }

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

    @Override
    public String toString() {
        String str = "错误码:" + errorCode + "  " + "错误信息:" + errorMsg;
        if (subMsg != null && !subMsg.equals("")) {
            str += " 错误详细描述:" + subMsg;
        }
        return str;
    }
}
